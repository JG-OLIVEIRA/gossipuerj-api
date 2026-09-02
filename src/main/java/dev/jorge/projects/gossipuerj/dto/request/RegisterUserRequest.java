package dev.jorge.projects.gossipuerj.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterUserRequest(@NotBlank String username, @NotBlank @Email String email, @NotBlank String password) { }
