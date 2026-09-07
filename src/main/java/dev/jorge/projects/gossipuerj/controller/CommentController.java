package dev.jorge.projects.gossipuerj.controller;

import dev.jorge.projects.gossipuerj.config.JWTUserData;
import dev.jorge.projects.gossipuerj.dto.request.comment.CommentRequest;
import dev.jorge.projects.gossipuerj.dto.response.comment.CommentResponse;
import dev.jorge.projects.gossipuerj.model.Comment;
import dev.jorge.projects.gossipuerj.service.CommentService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/v1/posts/{postId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CommentResponse> createComment(@PathVariable String postId, @AuthenticationPrincipal JWTUserData userData, @RequestBody @Valid CommentRequest request){
        Comment created = commentService.createComment(postId, null, userData.userId(), request);
        return ResponseEntity
                .created(URI.create("/api/v1/posts/%s/comments/%s".formatted(postId, created.getId())))
                .body(CommentResponse.from(created));
    }

    @PostMapping("/api/v1/posts/{postId}/comments/{commentId}/replies")
    @ResponseStatus(HttpStatus.CREATED)
    public void replyComment(@PathVariable String postId, @PathVariable String commentId, @AuthenticationPrincipal JWTUserData userData, @RequestBody @Valid CommentRequest request){
        commentService.createComment(postId, commentId, userData.userId(), request);
    }

    @GetMapping("/api/v1/posts/{postId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponse> getPostComments(@PathVariable String postId){
        return commentService.findPostComments(postId);
    }

    @GetMapping("/api/v1/posts/{postId}/comments/{commentId}/replies")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponse> getReplies(@PathVariable String postId, @PathVariable String commentId){
        return commentService.findReplies(postId, commentId);
    }

}
