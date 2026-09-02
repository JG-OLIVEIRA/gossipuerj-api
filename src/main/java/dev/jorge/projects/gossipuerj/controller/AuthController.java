package dev.jorge.projects.gossipuerj.controller;

import dev.jorge.projects.gossipuerj.dto.request.LoginRequest;
import dev.jorge.projects.gossipuerj.dto.request.VerifyUserRequest;
import dev.jorge.projects.gossipuerj.dto.response.LoginResponse;

import dev.jorge.projects.gossipuerj.dto.request.RegisterUserRequest;
import dev.jorge.projects.gossipuerj.dto.response.RegisterUserResponse;
import dev.jorge.projects.gossipuerj.model.User;
import dev.jorge.projects.gossipuerj.service.AuthService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/api/v1/auth/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterUserResponse register(@RequestBody @Valid RegisterUserRequest request){
        User user = authService.signUp(request);
        return new RegisterUserResponse(user.getUsername(), user.getEmail());
    }

    @PostMapping("/api/v1/auth/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {
        User user = authService.signIn(request);
        return new LoginResponse(authService.generateSessionToken(user));
    }

    @PostMapping("/verify")
    @ResponseStatus(HttpStatus.OK)
    public void verifyUser(@RequestBody @Valid VerifyUserRequest request) {
        authService.verifyUser(request);
    }

    @PostMapping("/resend")
    @ResponseStatus(HttpStatus.OK)
    public void resendVerificationCode(@RequestParam String email) {
        authService.resendVerificationCode(email);
    }

}
