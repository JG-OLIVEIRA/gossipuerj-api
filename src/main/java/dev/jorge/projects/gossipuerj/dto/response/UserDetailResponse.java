package dev.jorge.projects.gossipuerj.dto.response;

import dev.jorge.projects.gossipuerj.enums.Gender;
import dev.jorge.projects.gossipuerj.enums.Orientation;
import dev.jorge.projects.gossipuerj.model.User;

import java.time.LocalDateTime;

public record UserDetailResponse(
        String username,
        String email,
        Gender gender,
        Orientation orientation,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static UserDetailResponse fromUser(User user) {
        return new UserDetailResponse(
                user.getUsername(),
                user.getEmail(),
                user.getGender(),
                user.getOrientation(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}