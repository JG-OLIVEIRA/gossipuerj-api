package dev.jorge.projects.gossipuerj.controller;

import dev.jorge.projects.gossipuerj.dto.response.common.PageResponse;
import dev.jorge.projects.gossipuerj.dto.response.course.CourseResponse;
import dev.jorge.projects.gossipuerj.model.Course;
import dev.jorge.projects.gossipuerj.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/api/v1/courses")
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<CourseResponse> getAll(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ){
        Page<Course> courses = courseService.findAll(pageable);
        return PageResponse.from(courses.map(CourseResponse::from));
    }

    @GetMapping("/api/v1/courses/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    public CourseResponse getOne(@PathVariable String courseId){
        return CourseResponse.from(courseService.findById(courseId));
    }

}
