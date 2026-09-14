package dev.jorge.projects.gossipuerj.dto.request.match;
import dev.jorge.projects.gossipuerj.enums.match.Status;
import jakarta.validation.constraints.NotNull;

public record MatchRequest(
        @NotNull Status status
) { }
