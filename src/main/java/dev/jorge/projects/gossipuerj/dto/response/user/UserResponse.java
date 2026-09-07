package dev.jorge.projects.gossipuerj.dto.response.user;

import dev.jorge.projects.gossipuerj.enums.user.Gender;
import dev.jorge.projects.gossipuerj.enums.user.Orientation;
import dev.jorge.projects.gossipuerj.model.User;

import java.time.LocalDateTime;

public record UserResponse(
        String username,
        String email,
        Gender gender,
        Orientation orientation,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static UserResponse fromUser(User user) {
        return new UserResponse(
                user.getUsername(),
                user.getEmail(),
                user.getGender(),
                user.getOrientation(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}