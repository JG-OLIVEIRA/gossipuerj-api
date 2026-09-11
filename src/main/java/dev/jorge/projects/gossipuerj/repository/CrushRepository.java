package dev.jorge.projects.gossipuerj.repository;

import dev.jorge.projects.gossipuerj.model.Crush;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CrushRepository extends JpaRepository<Crush, String> {
    Optional<Crush> findByUserId(String userId);
}
