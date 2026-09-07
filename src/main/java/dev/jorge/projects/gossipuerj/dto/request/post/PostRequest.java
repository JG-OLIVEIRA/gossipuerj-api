package dev.jorge.projects.gossipuerj.dto.request.post;

import dev.jorge.projects.gossipuerj.enums.post.Category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostRequest(
        @NotBlank String title,
        @NotBlank String content,
        @NotNull Category category
){}
