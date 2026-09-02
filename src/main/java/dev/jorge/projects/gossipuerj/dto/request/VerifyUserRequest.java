package dev.jorge.projects.gossipuerj.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record VerifyUserRequest(@NotBlank @Email String email, @NotBlank String verificationCode) { }
