package dev.jorge.projects.gossipuerj.controller;

import dev.jorge.projects.gossipuerj.config.JWTUserData;
import dev.jorge.projects.gossipuerj.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/api/v1/posts/{postId}/likes")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Map<String, Object>> togglePostLike(@PathVariable String postId, @AuthenticationPrincipal JWTUserData userData) {
        boolean liked = likeService.togglePostLike(postId, userData.userId());
        int totalLikes = likeService.getTotalPostLikes(postId);
        return ResponseEntity.ok(Map.of("liked", liked, "totalLikes", totalLikes));
    }

    @PostMapping("/api/v1/posts/{postId}/comments/{commentId}/likes")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Map<String, Object>> toggleCommentLike(@PathVariable String postId, @PathVariable String commentId, @AuthenticationPrincipal JWTUserData userData) {
        boolean liked = likeService.toggleCommentLike(postId, commentId, userData.userId());
        int totalLikes = likeService.getTotalCommentLikes(postId, commentId);
        return ResponseEntity.ok(Map.of("liked", liked, "totalLikes", totalLikes));
    }

    @GetMapping("/api/v1/posts/{postId}/likes")
    @ResponseStatus(HttpStatus.OK)
    public int getTotalPostLikes(@PathVariable String postId) {
        return likeService.getTotalPostLikes(postId);
    }

    @GetMapping("/api/v1/posts/{postId}/comments/{commentId}/likes")
    @ResponseStatus(HttpStatus.OK)
    public int getTotalCommentLikes(@PathVariable String postId, @PathVariable String commentId) {
        return likeService.getTotalCommentLikes(postId, commentId);
    }

}
