package dev.jorge.projects.gossipuerj.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import dev.jorge.projects.gossipuerj.config.JWTUserData;
import dev.jorge.projects.gossipuerj.dto.request.LoginRequest;
import dev.jorge.projects.gossipuerj.dto.request.RegisterUserRequest;
import dev.jorge.projects.gossipuerj.dto.request.VerifyUserRequest;
import dev.jorge.projects.gossipuerj.enums.Gender;
import dev.jorge.projects.gossipuerj.enums.Orientation;
import dev.jorge.projects.gossipuerj.exception.*;
import dev.jorge.projects.gossipuerj.model.User;
import dev.jorge.projects.gossipuerj.enums.Role;
import dev.jorge.projects.gossipuerj.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${security.jwt.secret-key}")
    private String tokenSecretKey;

    @Value("${security.jwt.expiration-time}")
    private long tokenExpirationTime;

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public User signUp(RegisterUserRequest request) {
        User newUser = new User();

        String email = request.email();
        String username = request.username();

        if (!email.endsWith("@graduacao.uerj.br")) {
            throw new UserEmailDomainIsNotValidException(email);
        }

        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException(email);
        }
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException(username);
        }

        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(hashPassword(request.password()));
        newUser.setRoles(Set.of(Role.ROLE_USER));
        newUser.setGender(Gender.valueOf(request.gender()));
        newUser.setOrientation(Orientation.valueOf(request.orientation()));
        newUser.setVerificationCode(generateVerificationCode());
        newUser.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(15));

        User savedUser = userRepository.save(newUser);
        sendVerificationEmail(savedUser);
        return savedUser;
    }

    public User signIn(LoginRequest request) {
        User user = findByEmail(request.email());

        if (!user.isEnabled()) {
            throw new UserNotVerifiedException(request.email());
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        return user;
    }

    public void verifyUser(VerifyUserRequest request) {
        User user = findByEmail(request.email());

        if (user.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())) {
            throw new UserVerificationCodeExpiredException(request.verificationCode());
        }

        if (user.getVerificationCode().equals(request.verificationCode())) {
            user.setEnabled(true);
            user.setVerificationCode(null);
            user.setVerificationCodeExpiresAt(null);
            userRepository.save(user);
        } else {
            throw new UserVerificationCodeIsNotValidException(request.verificationCode());
        }
    }

    public User findById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    private User findByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }

    private String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public void resendVerificationCode(String email) {
        User user = findByEmail(email);
        if (user.isEnabled()) {
            throw new UserNotVerifiedException(email);
        }
        user.setVerificationCode(generateVerificationCode());
        user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(15));
        sendVerificationEmail(user);
        userRepository.save(user);
    }

    private void sendVerificationEmail(User user) {
        String subject = "Verificação de conta - Gossip UERJ";
        String verificationCode = "Código de verificação: " + user.getVerificationCode();
        String text =  "Olá " + user.getUsername() + ",\n\n" +
                "Obrigado por se registrar no Gossip UERJ! Para ativar sua conta, por favor, use o seguinte código de verificação:\n\n" +
                verificationCode + "\n\n" +
                "Este código expira em 15 minutos.\n\n" +
                "Atenciosamente,\n" +
                "Equipe Gossip UERJ";

        try {
            emailService.sendVerificationEmail(user.getEmail(), subject, text);
        } catch (MailException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    public String generateSessionToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(tokenSecretKey);

        return JWT.create()
                .withClaim("userId", user.getId())
                .withClaim("roles", user.getRoles().stream().map(Enum::name).toList())
                .withSubject(user.getEmail())
                .withExpiresAt(Instant.now().plus(Duration.ofHours(tokenExpirationTime)))
                .withIssuedAt(Instant.now())
                .sign(algorithm);
    }

    public Optional<JWTUserData> validateSessionToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(tokenSecretKey);

            DecodedJWT decoded = JWT.require(algorithm).build().verify(token);

            if (decoded.getClaim("userId").isMissing()) {
                return Optional.empty();
            }

            List<String> roles = decoded.getClaim("roles").asList(String.class);

            return Optional.of(JWTUserData.builder()
                    .userId(String.valueOf(UUID.fromString(decoded.getClaim("userId").asString())))
                    .email(decoded.getSubject())
                    .roles(roles != null ? roles : Collections.emptyList())
                    .build());

        } catch (JWTVerificationException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

}