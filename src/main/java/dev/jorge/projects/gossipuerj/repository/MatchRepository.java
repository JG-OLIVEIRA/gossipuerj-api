package dev.jorge.projects.gossipuerj.repository;

import dev.jorge.projects.gossipuerj.model.Match;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatchRepository extends JpaRepository<Match, String> {
    Optional<Match> findByLikerIdAndLikedIdAndId(String likerId, String likedId, String id);
    Page<Match> findAllByLikerId(String likerId, Pageable pageable);
    Page<Match> findAllByLikedId(String likedId, Pageable pageable);
}
