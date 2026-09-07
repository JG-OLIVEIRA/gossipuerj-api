package dev.jorge.projects.gossipuerj.repository;

import dev.jorge.projects.gossipuerj.model.Comment;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, String> {
    @EntityGraph(attributePaths = {"replies"})
    List<Comment> findByPostIdAndParentIsNullOrderByCreatedAtAsc(String postId);

    @EntityGraph(attributePaths = {"replies"})
    Comment findByPostIdAndId(String postId, String commentId);
}
