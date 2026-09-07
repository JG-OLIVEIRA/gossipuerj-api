package dev.jorge.projects.gossipuerj.dto.request.user;

import dev.jorge.projects.gossipuerj.enums.user.Gender;
import dev.jorge.projects.gossipuerj.enums.user.Orientation;

import jakarta.validation.constraints.*;

public record RegisterUserRequest(
        @NotBlank String username,
        @NotBlank String email,
        @NotBlank String password,
        @NotNull Gender gender,
        @NotNull Orientation orientation
) { }
