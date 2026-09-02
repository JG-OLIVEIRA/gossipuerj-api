package dev.jorge.projects.gossipuerj.dto.response;

import dev.jorge.projects.gossipuerj.model.User;

public record RegisterUserResponse(String username, String email) {
    public static RegisterUserResponse fromUser(User user) {
        return new RegisterUserResponse(user.getUsername(), user.getEmail());
    }
}
