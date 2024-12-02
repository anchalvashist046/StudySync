package com.Tezpur.University.Management.System.Backend.service_testing;

import com.Tezpur.University.Management.System.Backend.model.Course;
import com.Tezpur.University.Management.System.Backend.model.Enrollment;
import com.Tezpur.University.Management.System.Backend.model.Student;
import com.Tezpur.University.Management.System.Backend.repository.CourseRepository;
import com.Tezpur.University.Management.System.Backend.repository.EnrollmentRepository;
import com.Tezpur.University.Management.System.Backend.repository.StudentRepository;
import com.Tezpur.University.Management.System.Backend.service.EnrollmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EnrollmentServiceTest {

    @InjectMocks
    private EnrollmentService enrollmentService;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testApplyForCourse_Success() {
        Long studentId = 1L;
        Long courseId = 1L;

        Student student = new Student();
        student.setId(studentId);

        Course course = new Course();
        course.setId(courseId);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);

        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(enrollment);

        Enrollment result = enrollmentService.applyForCourse(studentId, courseId);

        assertNotNull(result);
        assertEquals(student, result.getStudent());
        assertEquals(course, result.getCourse());
        verify(enrollmentRepository, times(1)).save(any(Enrollment.class));
    }

    @Test
    void testApplyForCourse_InvalidStudentOrCourseId() {
        Long studentId = 1L;
        Long courseId = 1L;

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> enrollmentService.applyForCourse(studentId, courseId));
    }

    @Test
    void testAddMarks_Success() {
        Long enrollmentId = 1L;
        int marks = 85;

        Enrollment enrollment = new Enrollment();
        enrollment.setId(enrollmentId);

        when(enrollmentRepository.findById(enrollmentId)).thenReturn(Optional.of(enrollment));

        enrollmentService.addMarks(enrollmentId, marks);

        verify(enrollmentRepository, times(1)).save(enrollment);
    }

    @Test
    void testAddMarks_InvalidEnrollmentId() {
        Long enrollmentId = 1L;
        int marks = 85;

        when(enrollmentRepository.findById(enrollmentId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> enrollmentService.addMarks(enrollmentId, marks));
    }

    @Test
    void testGetEnrollmentsByStudent_Success() {
        Long studentId = 1L;

        List<Enrollment> enrollments = List.of(new Enrollment());
        when(enrollmentRepository.findByStudentId(studentId)).thenReturn(enrollments);

        List<Enrollment> result = enrollmentService.getEnrollmentsByStudent(studentId);

        assertNotNull(result);
        assertEquals(enrollments.size(), result.size());
        verify(enrollmentRepository, times(1)).findByStudentId(studentId);
    }

    @Test
    void testGetEnrollmentsByCourse_Success() {
        Long courseId = 1L;

        List<Enrollment> enrollments = List.of(new Enrollment());
        when(enrollmentRepository.findByCourseId(courseId)).thenReturn(enrollments);

        List<Enrollment> result = enrollmentService.getEnrollmentsByCourse(courseId);

        assertNotNull(result);
        assertEquals(enrollments.size(), result.size());
        verify(enrollmentRepository, times(1)).findByCourseId(courseId);
    }
}
