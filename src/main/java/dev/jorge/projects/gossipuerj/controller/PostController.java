package dev.jorge.projects.gossipuerj.controller;

import dev.jorge.projects.gossipuerj.config.JWTUserData;
import dev.jorge.projects.gossipuerj.dto.request.post.PostRequest;
import dev.jorge.projects.gossipuerj.dto.response.common.PageResponse;
import dev.jorge.projects.gossipuerj.dto.response.post.PostResponse;
import dev.jorge.projects.gossipuerj.model.Post;
import dev.jorge.projects.gossipuerj.service.PostService;
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

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/api/v1/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PostResponse> create(
            @AuthenticationPrincipal JWTUserData userData,
            @RequestBody @Valid PostRequest request
    ){
        Post created = postService.create(request, userData.userId());
        return ResponseEntity
                .created(URI.create("/api/v1/posts/%s".formatted(created.getId())))
                .body(PostResponse.from(created));
    }

    @GetMapping("/api/v1/posts/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponse getOne(@PathVariable String postId){
        Post post = postService.findById(postId);
        return PostResponse.from(post);
    }

    @GetMapping("/api/v1/posts")
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<PostResponse> getAll(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Post> posts = postService.findAll(pageable);
        return PageResponse.from(posts.map(PostResponse::from));
    }

    @GetMapping("/api/v1/posts/me")
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<PostResponse> getAllByUserId(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal JWTUserData userData
    ){
        Page<Post> posts = postService.findByAuthorId(userData.userId(), pageable);
        return PageResponse.from(posts.map(PostResponse::from));
    }

    @DeleteMapping("/api/v1/posts/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable String postId,
            @AuthenticationPrincipal JWTUserData userData
    ){
        postService.delete(postId, userData.userId());
    }

}
