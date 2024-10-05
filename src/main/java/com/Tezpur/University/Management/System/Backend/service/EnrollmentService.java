package com.Tezpur.University.Management.System.Backend.service;

import com.Tezpur.University.Management.System.Backend.model.Course;
import com.Tezpur.University.Management.System.Backend.model.Enrollment;
import com.Tezpur.University.Management.System.Backend.model.Student;
import com.Tezpur.University.Management.System.Backend.repository.CourseRepository;
import com.Tezpur.University.Management.System.Backend.repository.EnrollmentRepository;
import com.Tezpur.University.Management.System.Backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    public Enrollment applyForCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        Course course = courseRepository.findById(courseId).orElse(null);

        if (student == null || course == null) {
            throw new IllegalArgumentException("Invalid student or course ID");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);

        return enrollmentRepository.save(enrollment);
    }

//    public Enrollment approveEnrollment(Long enrollmentId) {
//        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElse(null);
//
//        if (enrollment == null) {
//            throw new IllegalArgumentException("Invalid enrollment ID");
//        }
//
//        enrollment.setApproved(true);
//        return enrollmentRepository.save(enrollment);
//    }

    public void addMarks(Long enrollmentId, Integer marks) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElse(null);

        if (enrollment == null) {
            throw new IllegalArgumentException("Invalid enrollment ID");
        }

      //  enrollment.setMarks(marks);
        enrollmentRepository.save(enrollment);
    }

    public List<Enrollment> getEnrollmentsByStudent(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    public List<Enrollment> getEnrollmentsByCourse(Long courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }
}
