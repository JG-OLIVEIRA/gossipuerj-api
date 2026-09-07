package dev.jorge.projects.gossipuerj.controller;

import dev.jorge.projects.gossipuerj.config.JWTUserData;
import dev.jorge.projects.gossipuerj.dto.request.user.LoginRequest;
import dev.jorge.projects.gossipuerj.dto.request.user.VerifyUserRequest;
import dev.jorge.projects.gossipuerj.dto.response.user.LoginResponse;

import dev.jorge.projects.gossipuerj.dto.request.user.RegisterUserRequest;
import dev.jorge.projects.gossipuerj.dto.response.user.UserResponse;
import dev.jorge.projects.gossipuerj.model.User;
import dev.jorge.projects.gossipuerj.service.AuthService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid RegisterUserRequest request){
        authService.signUp(request);
    }

    @PostMapping("login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {
        User user = authService.signIn(request);
        return new LoginResponse(authService.generateSessionToken(user));
    }

    @PostMapping("verify")
    @ResponseStatus(HttpStatus.OK)
    public void verify(@RequestBody @Valid VerifyUserRequest request) {
        authService.verifyUser(request);
    }

    @PostMapping("resend")
    @ResponseStatus(HttpStatus.OK)
    public void resend(@RequestParam String email) {
        authService.resendVerificationCode(email);
    }

    @GetMapping("me")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse me(@AuthenticationPrincipal JWTUserData userData) {
        User user = authService.findById(userData.userId());
        return UserResponse.fromUser(user);
    }

}
