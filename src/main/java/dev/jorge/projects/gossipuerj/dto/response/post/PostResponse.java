package dev.jorge.projects.gossipuerj.dto.response.post;

import dev.jorge.projects.gossipuerj.enums.post.Category;
import dev.jorge.projects.gossipuerj.model.Post;

import java.time.LocalDateTime;

public record PostResponse(
        String id,
        String title,
        String content,
        Category category,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PostResponse fromPost(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCategory(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
