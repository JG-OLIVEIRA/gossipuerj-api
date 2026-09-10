package dev.jorge.projects.gossipuerj.repository;

import dev.jorge.projects.gossipuerj.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, String> {
    int countByPostId(String postId);
    int countByPostIdAndCommentId(String postId, String commentId);
    Optional<Like> findByAuthorIdAndPostIdAndCommentIdIsNull(String authorId, String postId);
    Optional<Like> findByAuthorIdAndPostIdAndCommentId(String authorId, String postId, String commentId);
    boolean existsByAuthorIdAndPostIdAndCommentIdIsNull(String authorId, String postId);
    boolean existsByAuthorIdAndPostIdAndCommentId(String authorId, String postId, String commentId);
}
