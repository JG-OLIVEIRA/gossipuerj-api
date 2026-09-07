package dev.jorge.projects.gossipuerj.controller;

import dev.jorge.projects.gossipuerj.config.JWTUserData;
import dev.jorge.projects.gossipuerj.dto.request.post.PostRequest;
import dev.jorge.projects.gossipuerj.dto.response.post.PostResponse;
import dev.jorge.projects.gossipuerj.model.Post;
import dev.jorge.projects.gossipuerj.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PostResponse> create(@RequestBody @Valid PostRequest request, @AuthenticationPrincipal JWTUserData userData){
        Post created = postService.createPost(request, userData.userId());
        return ResponseEntity
                .created(URI.create("/api/v1/posts/%s".formatted(created.getId())))
                .body(PostResponse.fromPost(created));
    }

    @GetMapping("{postId}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponse getById(@PathVariable String postId){
        Post post = postService.findById(postId);
        return PostResponse.fromPost(post);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponse> getAll(){
        List<Post> posts = postService.findAll();
        return posts.stream().map(PostResponse::fromPost).toList();
    }

    @GetMapping("me")
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponse> getAllByUserId(@AuthenticationPrincipal JWTUserData userData){
        List<Post> posts = postService.findByAuthorId(userData.userId());
        return posts.stream().map(PostResponse::fromPost).toList();
    }

    @DeleteMapping("{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal JWTUserData userData, @PathVariable String postId){
        postService.delete(postId, userData.userId());
    }

}
