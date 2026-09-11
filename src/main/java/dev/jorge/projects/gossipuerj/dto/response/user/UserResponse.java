package dev.jorge.projects.gossipuerj.dto.response.user;

import dev.jorge.projects.gossipuerj.model.User;

import java.time.LocalDateTime;

public record UserResponse(
        String username,
        String courseName,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static UserResponse fromUser(User user) {
        return new UserResponse(
                user.getUsername(),
                user.getCourse().getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}