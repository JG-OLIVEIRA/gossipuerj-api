package dev.jorge.projects.gossipuerj.dto.response.comment;

import dev.jorge.projects.gossipuerj.model.Comment;

import java.time.LocalDateTime;

public record CommentResponse(
        String id,
        String content,
        LocalDateTime createdAt
){
    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt()
        );
    }
}
