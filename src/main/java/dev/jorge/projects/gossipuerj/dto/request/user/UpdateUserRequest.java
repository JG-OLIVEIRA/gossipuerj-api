package dev.jorge.projects.gossipuerj.dto.request.user;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest (
        @NotBlank String username,
        @NotBlank String courseName,
        @NotBlank String email
){ }
