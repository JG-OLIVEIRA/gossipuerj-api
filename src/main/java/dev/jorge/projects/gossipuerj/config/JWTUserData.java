package dev.jorge.projects.gossipuerj.config;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record JWTUserData(UUID userId, String email, List<String> roles) { }