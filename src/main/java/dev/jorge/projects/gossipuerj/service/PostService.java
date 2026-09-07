package dev.jorge.projects.gossipuerj.service;

import dev.jorge.projects.gossipuerj.dto.request.post.PostRequest;
import dev.jorge.projects.gossipuerj.exception.post.PostNotFoundException;
import dev.jorge.projects.gossipuerj.model.Post;
import dev.jorge.projects.gossipuerj.repository.PostRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final AuthService authService;

    public Post createPost(PostRequest request, String userId) {
        Post newPost = new Post();
        newPost.setTitle(request.title());
        newPost.setContent(request.content());
        newPost.setCategory(request.category());
        newPost.setAuthor(authService.findById(userId));
        return postRepository.save(newPost);
    }

    public Post findById(String postId) {
        return postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
    }

    public List<Post> findByAuthorId(String authorId) {
        return postRepository.findByAuthorId(authorId);
    }

    public List<Post> findAll(){
        return postRepository.findAll();
    }

    public void delete(String postId, String userId) {
        Post post = findById(postId);
        if (!post.getAuthor().getId().equals(userId)) {
            throw new PostNotFoundException(postId);
        }
        postRepository.delete(post);
    }
}
