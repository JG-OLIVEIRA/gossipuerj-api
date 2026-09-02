package dev.jorge.projects.gossipuerj.config;

import lombok.Builder;

import java.util.List;

@Builder
public record JWTUserData(String userId, String email, List<String> roles) { }