package com.peakprosys.institute.service.impl;

import com.peakprosys.institute.dto.request.CourseRequest;
import com.peakprosys.institute.dto.response.CourseResponse;
import com.peakprosys.institute.entity.Course;
import com.peakprosys.institute.exception.CourseNotFoundException;
import com.peakprosys.institute.repository.CourseRepository;
import com.peakprosys.institute.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public CourseResponse createCourse(CourseRequest request) {
        String courseCode = request.courseCode().trim().toUpperCase();

        if (courseRepository.existsByCourseCode(courseCode)) {
            throw new IllegalArgumentException("Course code already exists");
        }

        Course course = new Course(
            request.courseName().trim(),
            courseCode,
            request.description(),
            request.durationInWeeks(),
            request.courseFee(),
            request.active() == null ? true : request.active()
        );

        return toResponse(courseRepository.save(course));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll()
            .stream()
            .map(this::toResponse)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CourseResponse getCourseById(Long id) {
        return toResponse(findCourse(id));
    }

    @Override
    public CourseResponse updateCourse(Long id, CourseRequest request) {
        Course course = findCourse(id);
        String courseCode = request.courseCode().trim().toUpperCase();

        if (courseRepository.existsByCourseCodeAndIdNot(courseCode, id)) {
            throw new IllegalArgumentException("Course code already exists");
        }

        course.setCourseName(request.courseName().trim());
        course.setCourseCode(courseCode);
        course.setDescription(request.description());
        course.setDurationInWeeks(request.durationInWeeks());
        course.setCourseFee(request.courseFee());

        if (request.active() != null) {
            course.setActive(request.active());
        }

        return toResponse(courseRepository.save(course));
    }

    @Override
    public void deleteCourse(Long id) {
        Course course = findCourse(id);
        courseRepository.delete(course);
    }

    private Course findCourse(Long id) {
        return courseRepository.findById(id)
            .orElseThrow(() ->
                new CourseNotFoundException("Course not found with ID: " + id)
            );
    }

    private CourseResponse toResponse(Course course) {
        return new CourseResponse(
            course.getId(),
            course.getCourseName(),
            course.getCourseCode(),
            course.getDescription(),
            course.getDurationInWeeks(),
            course.getCourseFee(),
            course.getActive()
        );
    }
}