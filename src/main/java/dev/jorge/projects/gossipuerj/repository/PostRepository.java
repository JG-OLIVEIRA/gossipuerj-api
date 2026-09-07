package dev.jorge.projects.gossipuerj.repository;

import dev.jorge.projects.gossipuerj.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
    List<Post> findByAuthorId(String authorId);
}
