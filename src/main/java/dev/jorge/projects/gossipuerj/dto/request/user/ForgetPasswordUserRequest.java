package dev.jorge.projects.gossipuerj.dto.request.user;

import jakarta.validation.constraints.NotBlank;

public record ForgetPasswordUserRequest(@NotBlank String email){}
