package dev.jorge.projects.gossipuerj.service;

import dev.jorge.projects.gossipuerj.dto.request.post.PostRequest;
import dev.jorge.projects.gossipuerj.exception.post.PostNotFoundException;
import dev.jorge.projects.gossipuerj.model.Post;
import dev.jorge.projects.gossipuerj.model.User;
import dev.jorge.projects.gossipuerj.repository.PostRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final AuthService authService;

    public Post create(PostRequest request, String userId) {
        Post newPost = new Post();
        User user = authService.findById(userId);
        newPost.setTitle(request.title());
        newPost.setCourse(user.getCourse());
        newPost.setContent(request.content());
        newPost.setCategory(request.category());
        newPost.setAuthor(user);
        return postRepository.save(newPost);
    }

    public Post findById(String postId) {
        return postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
    }

    public Page<Post> findByAuthorId(String authorId, Pageable pageable) {
        return postRepository.findByAuthorId(authorId, pageable);
    }

    public Page<Post> findAll(Pageable pageable){
        return postRepository.findAll(pageable);
    }

    public void delete(String postId, String userId) {
        Post post = findById(postId);
        if (!post.getAuthor().getId().equals(userId)) {
            throw new PostNotFoundException(postId);
        }
        postRepository.delete(post);
    }
}
