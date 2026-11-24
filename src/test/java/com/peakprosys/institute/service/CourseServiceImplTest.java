
package com.peakprosys.institute.service;

import com.peakprosys.institute.dto.request.CourseRequest;
import com.peakprosys.institute.dto.response.CourseResponse;
import com.peakprosys.institute.entity.Course;
import com.peakprosys.institute.repository.CourseRepository;
import com.peakprosys.institute.service.impl.CourseServiceImpl;
import com.peakprosys.institute.exception.CourseNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    @Test
    void shouldCreateCourseSuccessfully() {

        // Arrange: Prepare the request
        CourseRequest request = new CourseRequest(
                "  Java Full Stack  ",
                " java-fs-01 ",
                "Professional Java course",
                24,
                new BigDecimal("25000.00"),
                null
        );

        when(courseRepository.existsByCourseCode("JAVA-FS-01"))
                .thenReturn(false);

        when(courseRepository.save(any(Course.class)))
                .thenAnswer(invocation -> {
                    Course course = invocation.getArgument(0);
                    course.setId(1L);
                    return course;
                });

        // Act: Execute the service method
        CourseResponse response = courseService.createCourse(request);

        // Assert: Verify the result
        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Java Full Stack", response.courseName());
        assertEquals("JAVA-FS-01", response.courseCode());
        assertEquals(24, response.durationInWeeks());
        assertEquals(new BigDecimal("25000.00"), response.courseFee());
        assertTrue(response.active());

        verify(courseRepository).existsByCourseCode("JAVA-FS-01");
        verify(courseRepository).save(any(Course.class));
    }
    @Test
    void shouldThrowExceptionWhenCourseCodeAlreadyExists() {

        CourseRequest request = new CourseRequest(
                "Java Full Stack",
                "JAVA-FS-01",
                "Professional Java course",
                24,
                new BigDecimal("25000.00"),
                true
        );

        when(courseRepository.existsByCourseCode("JAVA-FS-01"))
                .thenReturn(true);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> courseService.createCourse(request)
        );

        assertEquals("Course code already exists", exception.getMessage());

        verify(courseRepository).existsByCourseCode("JAVA-FS-01");
        verify(courseRepository, never()).save(any(Course.class));
    }

    @Test
    void shouldReturnCourseWhenCourseExists() {

        Course course = new Course(
                "Java Full Stack",
                "JAVA-FS-01",
                "Professional Java course",
                24,
                new BigDecimal("25000.00"),
                true
        );

        course.setId(1L);

        when(courseRepository.findById(1L))
                .thenReturn(java.util.Optional.of(course));

        CourseResponse response = courseService.getCourseById(1L);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Java Full Stack", response.courseName());
        assertEquals("JAVA-FS-01", response.courseCode());
        assertEquals(24, response.durationInWeeks());

        verify(courseRepository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenCourseDoesNotExist() {

        when(courseRepository.findById(999L))
                .thenReturn(java.util.Optional.empty());

        CourseNotFoundException exception = assertThrows(
                CourseNotFoundException.class,
                () -> courseService.getCourseById(999L)
        );

        assertEquals("Course not found with ID: 999", exception.getMessage());

        verify(courseRepository).findById(999L);
    }

    @Test
    void shouldUpdateCourseSuccessfully() {

        Course existingCourse = new Course(
                "Java Full Stack",
                "JAVA-FS-01",
                "Basic Java course",
                20,
                new BigDecimal("20000.00"),
                true
        );

        existingCourse.setId(1L);

        CourseRequest request = new CourseRequest(
                "Advanced Java Full Stack",
                "JAVA-FS-01",
                "Advanced professional Java course",
                28,
                new BigDecimal("50000.00"),
                false
        );

        when(courseRepository.findById(1L))
                .thenReturn(java.util.Optional.of(existingCourse));

        when(courseRepository.existsByCourseCodeAndIdNot("JAVA-FS-01", 1L))
                .thenReturn(false);

        when(courseRepository.save(any(Course.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CourseResponse response = courseService.updateCourse(1L, request);

        assertEquals("Advanced Java Full Stack", response.courseName());
        assertEquals(28, response.durationInWeeks());
        assertEquals(new BigDecimal("50000.00"), response.courseFee());
        assertFalse(response.active());

        verify(courseRepository).findById(1L);
        verify(courseRepository).existsByCourseCodeAndIdNot("JAVA-FS-01", 1L);
        verify(courseRepository).save(existingCourse);
    }

    @Test
    void shouldDeleteCourseSuccessfully() {

        Course existingCourse = new Course(
                "Java Full Stack",
                "JAVA-FS-01",
                "Professional Java course",
                24,
                new BigDecimal("25000.00"),
                true
        );

        existingCourse.setId(1L);

        when(courseRepository.findById(1L))
                .thenReturn(java.util.Optional.of(existingCourse));

        courseService.deleteCourse(1L);

        verify(courseRepository).findById(1L);
        verify(courseRepository).delete(existingCourse);
    }
    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingCourse() {

        CourseRequest request = new CourseRequest(
                "Advanced Java",
                "JAVA-ADV-01",
                "Advanced Java course",
                30,
                new BigDecimal("35000.00"),
                true
        );

        when(courseRepository.findById(999L))
                .thenReturn(java.util.Optional.empty());

        CourseNotFoundException exception = assertThrows(
                CourseNotFoundException.class,
                () -> courseService.updateCourse(999L, request)
        );

        assertEquals("Course not found with ID: 999", exception.getMessage());

        verify(courseRepository, never())
                .save(any(Course.class));
    }
}
