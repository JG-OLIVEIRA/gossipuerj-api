package dev.jorge.projects.gossipuerj.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import dev.jorge.projects.gossipuerj.config.JWTUserData;
import dev.jorge.projects.gossipuerj.dto.request.LoginRequest;
import dev.jorge.projects.gossipuerj.dto.request.RegisterUserRequest;
import dev.jorge.projects.gossipuerj.dto.request.VerifyUserRequest;
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
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpirationTime;

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public User signUp(RegisterUserRequest request) {
        User newUser = new User();

        String email = request.email();
        String username = request.username();

        if (!email.endsWith("graduacao.uerj.br")) {
            throw new UserEmailDomainIsNotValidException(email);
        }

        if (userRepository.existsByEmailAndUsername(email, username)) {
            throw new UserAlreadyExistsException(username);
        }

        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(hashPassword(request.password()));
        newUser.setRoles(Set.of(Role.ROLE_USER));
        newUser.setVerificationCode(generateVerificationCode());
        newUser.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(15));
        sendVerificationEmail(newUser);
        return userRepository.save(newUser);
    }

    public User signIn(LoginRequest request) {
        User user = findByEmail(request.email());

        if (!user.isEnabled()) {
            throw new RuntimeException("Account not verified. Please verify your account.");
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
            throw new RuntimeException("Account is already verified");
        }
        user.setVerificationCode(generateVerificationCode());
        user.setVerificationCodeExpiresAt(LocalDateTime.now().plusHours(1));
        sendVerificationEmail(user);
        userRepository.save(user);
    }

    private void sendVerificationEmail(User user) {
        String subject = "Account Verification";
        String verificationCode = "VERIFICATION CODE " + user.getVerificationCode();
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Welcome to our app!</h2>"
                + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h3 style=\"color: #333;\">Verification Code:</h3>"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + verificationCode + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";

        try {
            emailService.sendVerificationEmail(user.getEmail(), subject, htmlMessage);
        } catch (MailException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    public String generateSessionToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        return JWT.create()
                .withClaim("userId", user.getId().toString())
                .withClaim("roles", user.getRoles().stream().map(Enum::name).toList())
                .withSubject(user.getEmail())
                .withExpiresAt(Instant.now().plus(Duration.ofHours(jwtExpirationTime)))
                .withIssuedAt(Instant.now())
                .sign(algorithm);
    }

    public Optional<JWTUserData> validateSessionToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            DecodedJWT decoded = JWT.require(algorithm).build().verify(token);

            if (decoded.getClaim("userId").isMissing()) {
                return Optional.empty();
            }

            List<String> roles = decoded.getClaim("roles").asList(String.class);

            return Optional.of(JWTUserData.builder()
                    .userId(UUID.fromString(decoded.getClaim("userId").asString()))
                    .email(decoded.getSubject())
                    .roles(roles != null ? roles : Collections.emptyList())
                    .build());

        } catch (JWTVerificationException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

}