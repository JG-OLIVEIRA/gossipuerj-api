package dev.jorge.projects.gossipuerj.dto.request.course;

import jakarta.validation.constraints.NotBlank;

public record CourseRequest(
    @NotBlank String name
) {}
