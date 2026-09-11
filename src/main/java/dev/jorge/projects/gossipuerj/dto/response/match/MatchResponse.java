package dev.jorge.projects.gossipuerj.dto.response.match;

import dev.jorge.projects.gossipuerj.enums.match.Status;
import dev.jorge.projects.gossipuerj.model.Crush;
import dev.jorge.projects.gossipuerj.model.Match;

import java.time.LocalDateTime;

public record MatchResponse (
        String id,
        Crush crush,
        Crush likedCrush,
        Status status,
        LocalDateTime unmatchedAt,
        LocalDateTime createdAt
) {
    public static MatchResponse from(Match match){
        return new MatchResponse(
                match.getId(),
                match.getLiker(),
                match.getLiked(),
                match.getStatus(),
                match.getUnmatchedAt(),
                match.getCreatedAt()
        );
    }
}
