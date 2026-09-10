package dev.jorge.projects.gossipuerj.repository;

import dev.jorge.projects.gossipuerj.model.Comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, String> {
    Page<Comment> findByPostIdAndParentIdIsNull(String postId, Pageable pageable);
    Page<Comment> findByPostIdAndParentId(String postId, String commentId, Pageable pageable);
    Comment findByPostIdAndId(String postId, String commentId);
}
