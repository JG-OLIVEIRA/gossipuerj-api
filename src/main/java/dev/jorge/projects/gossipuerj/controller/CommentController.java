package dev.jorge.projects.gossipuerj.controller;

import dev.jorge.projects.gossipuerj.config.JWTUserData;
import dev.jorge.projects.gossipuerj.dto.request.comment.CommentRequest;
import dev.jorge.projects.gossipuerj.dto.response.comment.CommentResponse;
import dev.jorge.projects.gossipuerj.dto.response.common.PageResponse;
import dev.jorge.projects.gossipuerj.model.Comment;
import dev.jorge.projects.gossipuerj.service.CommentService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/v1/posts/{postId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CommentResponse> create(
            @PathVariable String postId,
            @AuthenticationPrincipal JWTUserData userData,
            @RequestBody @Valid CommentRequest request
    ){
        Comment created = commentService.createComment(postId, null, userData.userId(), request);
        return ResponseEntity
                .created(URI.create("/api/v1/posts/%s/comments/%s".formatted(postId, created.getId())))
                .body(CommentResponse.from(created));
    }

    @GetMapping("/api/v1/posts/{postId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<CommentResponse> getAll(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable String postId
    ){
        Page<Comment> comments = commentService.findByPostId(postId, pageable);
        return PageResponse.from(comments.map(CommentResponse::from));
    }

    @GetMapping("/api/v1/posts/{postId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponse getOne(
            @PathVariable String postId,
            @PathVariable String commentId
    ){
        Comment comment = commentService.findByPostIdAndId(postId, commentId);
        return CommentResponse.from(comment);
    }

    @PostMapping("/api/v1/posts/{postId}/comments/{commentId}/replies")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CommentResponse> reply(
            @PathVariable String postId,
            @PathVariable String commentId,
            @AuthenticationPrincipal JWTUserData userData,
            @RequestBody @Valid CommentRequest request
    ){
        Comment created = commentService.createComment(postId, commentId, userData.userId(), request);
        return ResponseEntity
                .created(URI.create("/api/v1/posts/%s/comments/%s".formatted(postId, created.getId())))
                .body(CommentResponse.from(created));
    }

    @GetMapping("/api/v1/posts/{postId}/comments/{commentId}/replies")
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<CommentResponse> getAllReplies(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable String postId,
            @PathVariable String commentId
    ){
        Page<Comment> comments = commentService.findByPostIdAndParentId(postId, commentId, pageable);
        return PageResponse.from(comments.map(CommentResponse::from));
    }

}
