package dev.jorge.projects.gossipuerj.dto.request.user;

import jakarta.validation.constraints.*;

public record RegisterUserRequest(
        @NotBlank String username,
        @NotBlank String courseName,
        @NotBlank String email,
        @NotBlank String password
) { }
