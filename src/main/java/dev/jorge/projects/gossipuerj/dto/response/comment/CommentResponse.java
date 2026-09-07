package dev.jorge.projects.gossipuerj.dto.response.comment;

import dev.jorge.projects.gossipuerj.model.Comment;

import java.time.LocalDateTime;
import java.util.List;

public record CommentResponse(
        String id,
        String content,
        LocalDateTime createdAt,
        List<CommentResponse> replies
){
    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getReplies() != null
                        ? comment.getReplies().stream()
                                .map(CommentResponse::from)
                                .toList()
                        : List.of()
        );
    }
}
