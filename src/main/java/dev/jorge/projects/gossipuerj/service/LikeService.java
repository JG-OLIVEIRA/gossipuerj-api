package dev.jorge.projects.gossipuerj.service;

import dev.jorge.projects.gossipuerj.model.Like;
import dev.jorge.projects.gossipuerj.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final PostService postService;
    private final AuthService authService;
    private final CommentService commentService;
    private final LikeRepository likeRepository;

    @Transactional
    public boolean togglePostLike(String postId, String userId) {
        Optional<Like> existing = likeRepository.findByAuthorIdAndPostIdAndCommentIdIsNull(userId, postId);
        if (existing.isPresent()) {
            likeRepository.delete(existing.get());
            return false;
        } else {
            Like newLike = new Like();
            newLike.setPost(postService.findById(postId));
            newLike.setAuthor(authService.findById(userId));
            likeRepository.save(newLike);
            return true;
        }
    }

    @Transactional
    public boolean toggleCommentLike(String postId, String commentId, String userId) {
        Optional<Like> existing = likeRepository.findByAuthorIdAndPostIdAndCommentId(userId, postId, commentId);
        if (existing.isPresent()) {
            likeRepository.delete(existing.get());
            return false;
        } else {
            Like newLike = new Like();
            newLike.setPost(postService.findById(postId));
            newLike.setComment(commentService.findById(commentId));
            newLike.setAuthor(authService.findById(userId));
            likeRepository.save(newLike);
            return true;
        }
    }

    public int getTotalPostLikes(String postId) {
        return likeRepository.countLikesByPostId(postId);
    }

    public int getTotalCommentLikes(String postId, String commentId) {
        return likeRepository.countLikesByPostIdAndCommentId(postId, commentId);
    }

    public boolean hasUserLikedPost(String postId, String userId) {
        return likeRepository.existsByAuthorIdAndPostIdAndCommentIdIsNull(userId, postId);
    }

    public boolean hasUserLikedComment(String postId, String commentId, String userId) {
        return likeRepository.existsByAuthorIdAndPostIdAndCommentId(userId, postId, commentId);
    }
}
