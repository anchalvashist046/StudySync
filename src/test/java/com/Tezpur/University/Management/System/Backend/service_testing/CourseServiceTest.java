package com.Tezpur.University.Management.System.Backend.service_testing;

import com.Tezpur.University.Management.System.Backend.model.Course;
import com.Tezpur.University.Management.System.Backend.repository.CourseRepository;
import com.Tezpur.University.Management.System.Backend.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private CourseRepository courseRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCourse() {
        Course course = new Course();
        course.setId(1L);
        course.setCourseName("Test Course");

        when(courseRepository.save(course)).thenReturn(course);

        Course result = courseService.addCourse(course);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Course", result.getCourseName());
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void testGetAllCourses() {
        Course course1 = new Course();
        course1.setId(1L);
        Course course2 = new Course();
        course2.setId(2L);

        when(courseRepository.findAll()).thenReturn(Arrays.asList(course1, course2));

        List<Course> result = courseService.getAllCourses();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void testGetCourseById() {
        Course course = new Course();
        course.setId(1L);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        Course result = courseService.getCourse(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(courseRepository, times(1)).findById(1L);
    }

    @Test
    void testGetCoursesByFacultyId() {
        Course course = new Course();
        course.setId(1L);

        when(courseRepository.findByFacultyId(10L)).thenReturn(Arrays.asList(course));

        List<Course> result = courseService.getCoursesByFacultyId(10L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(courseRepository, times(1)).findByFacultyId(10L);
    }
}
