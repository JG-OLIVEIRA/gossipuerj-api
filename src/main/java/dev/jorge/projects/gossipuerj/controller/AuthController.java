package dev.jorge.projects.gossipuerj.controller;

import dev.jorge.projects.gossipuerj.config.JWTUserData;
import dev.jorge.projects.gossipuerj.dto.request.LoginRequest;
import dev.jorge.projects.gossipuerj.dto.request.VerifyUserRequest;
import dev.jorge.projects.gossipuerj.dto.response.LoginResponse;

import dev.jorge.projects.gossipuerj.dto.request.RegisterUserRequest;
import dev.jorge.projects.gossipuerj.dto.response.RegisterUserResponse;
import dev.jorge.projects.gossipuerj.dto.response.UserDetailResponse;
import dev.jorge.projects.gossipuerj.model.User;
import dev.jorge.projects.gossipuerj.service.AuthService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/api/v1/auth/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterUserResponse register(@RequestBody @Valid RegisterUserRequest request){
        User user = authService.signUp(request);
        return RegisterUserResponse.fromUser(user);
    }

    @PostMapping("/api/v1/auth/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {
        User user = authService.signIn(request);
        return new LoginResponse(authService.generateSessionToken(user));
    }

    @PostMapping("/api/v1/auth/verify")
    @ResponseStatus(HttpStatus.OK)
    public void verify(@RequestBody @Valid VerifyUserRequest request) {
        authService.verifyUser(request);
    }

    @PostMapping("/api/v1/auth/resend")
    @ResponseStatus(HttpStatus.OK)
    public void resend(@RequestParam String email) {
        authService.resendVerificationCode(email);
    }

    @GetMapping("/api/v1/auth/me")
    @ResponseStatus(HttpStatus.OK)
    public UserDetailResponse me(@AuthenticationPrincipal JWTUserData userData) {
        User user = authService.findById(userData.userId());
        return UserDetailResponse.fromUser(user);
    }

}
