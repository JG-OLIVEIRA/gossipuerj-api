package dev.jorge.projects.gossipuerj.service;

import dev.jorge.projects.gossipuerj.exception.course.CourseNotFoundException;
import dev.jorge.projects.gossipuerj.model.Course;
import dev.jorge.projects.gossipuerj.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    @Transactional(readOnly = true)
    public Course create(String name) {
        if (findByName(name).isPresent()) {
            return findByName(name).get();
        }
        Course newCourse = new Course();
        newCourse.setName(name);
        return courseRepository.save(newCourse);
    }

    @Transactional(readOnly = true)
    public Optional<Course> findByName(String name) {
        return courseRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public Course findById(String courseId) {
        return courseRepository.findById(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));
    }

    @Transactional(readOnly = true)
    public Page<Course> findAll(Pageable pageable){
        return courseRepository.findAll(pageable);
    }

}
