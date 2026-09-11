package dev.jorge.projects.gossipuerj.dto.request.user;

import jakarta.validation.constraints.NotBlank;

public record ResetPasswordUserRequest (
        @NotBlank String email,
        @NotBlank String password,
        @NotBlank String verificationCode
){}
