package dev.jorge.projects.gossipuerj.dto.response.match;

import dev.jorge.projects.gossipuerj.dto.response.crush.CrushMatchResponse;
import dev.jorge.projects.gossipuerj.enums.match.Status;
import dev.jorge.projects.gossipuerj.model.Match;

import java.time.LocalDateTime;

public record MatchResponse (
        String id,
        CrushMatchResponse crush,
        CrushMatchResponse likedCrush,
        Status status,
        LocalDateTime unmatchedAt,
        LocalDateTime createdAt
) {
    public static MatchResponse from(Match match){
        return new MatchResponse(
                match.getId(),
                CrushMatchResponse.from(match.getLiker()),
                match.getStatus() != Status.PENDING ? CrushMatchResponse.from(match.getLiked()) : null,
                match.getStatus(),
                match.getUnmatchedAt(),
                match.getCreatedAt()
        );
    }
}
