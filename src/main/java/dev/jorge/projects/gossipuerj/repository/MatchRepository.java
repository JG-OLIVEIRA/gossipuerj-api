package dev.jorge.projects.gossipuerj.repository;

import dev.jorge.projects.gossipuerj.enums.match.Status;
import dev.jorge.projects.gossipuerj.model.Match;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MatchRepository extends JpaRepository<Match, String> {
    @Query("""
        SELECT m
        FROM Match m
        WHERE (m.status = :status) AND (m.liker.id = :likerId AND m.liked.id = :likedId) OR (m.liker.id = :likedId AND m.liked.id = :likerId)
    """)
    Optional<Match> findMatchBetweenCrushes(
            @Param("likerId") String likerId,
            @Param("likedId") String likedId,
            @Param("status") Status status
    );

    Optional<Match> findByLikerIdAndLikedIdAndId(String likerId, String likedId, String id);
    Page<Match> findAllByLikerId(String likerId, Pageable pageable);
    Page<Match> findAllByLikedId(String likedId, Pageable pageable);

    @Query("""
        SELECT m
        FROM Match m
        WHERE (m.status = :status) AND (m.liker.id = :crushId) OR (m.liked.id = :crushId)
    """)
    Page<Match> findAllByCrushId(
            @Param("crushId") String crushId,
            @Param("status") Status status
    , Pageable pageable
    );
}
