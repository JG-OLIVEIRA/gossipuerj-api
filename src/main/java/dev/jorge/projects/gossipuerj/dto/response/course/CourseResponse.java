package dev.jorge.projects.gossipuerj.dto.response.course;

import dev.jorge.projects.gossipuerj.model.Course;

public record CourseResponse(String id, String name) {
    public static CourseResponse from(Course course) {
        return new CourseResponse(course.getId(), course.getName());
    }
}
