package dev.jorge.projects.gossipuerj.repository;

import dev.jorge.projects.gossipuerj.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, String> {
    Optional<Course> findByName(String name);
}
