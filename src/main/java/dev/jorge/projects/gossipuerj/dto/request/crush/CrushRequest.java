package dev.jorge.projects.gossipuerj.dto.request.crush;

import dev.jorge.projects.gossipuerj.enums.crush.Gender;
import dev.jorge.projects.gossipuerj.enums.crush.Orientation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrushRequest (
        @NotBlank String photoUrl,
        @NotBlank String description,
        @NotNull Gender gender,
        @NotNull Orientation orientation
){ }
