package dev.jorge.projects.gossipuerj.controller;

import dev.jorge.projects.gossipuerj.config.JWTUserData;
import dev.jorge.projects.gossipuerj.dto.request.user.*;
import dev.jorge.projects.gossipuerj.dto.response.user.LoginResponse;

import dev.jorge.projects.gossipuerj.dto.response.user.UserResponse;
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
    public void register(@RequestBody @Valid RegisterUserRequest request){
        authService.signUp(request);
    }

    @PutMapping("/api/v1/auth/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(
            @AuthenticationPrincipal JWTUserData userData,
            @RequestBody @Valid UpdateUserRequest request
    ){
        authService.update(request, userData.userId());
    }

    @PostMapping("/api/v1/auth/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {
        User user = authService.signIn(request);
        return new LoginResponse(authService.generateSessionToken(user));
    }

    @PostMapping("/api/v1/auth/forget-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void forgetPassword(@RequestBody @Valid ForgetPasswordUserRequest request) {
        authService.forgetPassword(request.email());
    }

    @PostMapping("/api/v1/auth/reset-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resetPassword(@RequestBody @Valid ResetPasswordUserRequest request) {
        authService.resetPassword(request);
    }

    @PostMapping("/api/v1/auth/verify")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void verify(@RequestBody @Valid VerifyUserRequest request) {
        authService.verifyUser(request);
    }

    @PostMapping("/api/v1/auth/resend")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resend(@RequestParam String email) {
        authService.resendVerificationCode(email);
    }

    @GetMapping("/api/v1/auth/me")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse me(@AuthenticationPrincipal JWTUserData userData) {
        User user = authService.findById(userData.userId());
        return UserResponse.fromUser(user);
    }

    @DeleteMapping("/api/v1/auth")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal JWTUserData userData) {
        authService.delete(userData.userId());
    }

}
