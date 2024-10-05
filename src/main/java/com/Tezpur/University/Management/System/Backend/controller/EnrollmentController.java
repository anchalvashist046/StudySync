package com.Tezpur.University.Management.System.Backend.controller;

import com.Tezpur.University.Management.System.Backend.model.Enrollment;
import com.Tezpur.University.Management.System.Backend.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    // Endpoint for students to apply for a course
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/apply")
    public ResponseEntity<Enrollment> applyForCourse(@RequestParam Long studentId, @RequestParam Long courseId) {
        Enrollment enrollment = enrollmentService.applyForCourse(studentId, courseId);
        return ResponseEntity.ok(enrollment);
    }

    @GetMapping("/test")
    public String test(Authentication authentication) {
        System.out.println(authentication.getAuthorities()); // Log the authorities
        return "Authorities: " + authentication.getAuthorities();
    }

    // Endpoint for faculty to approve a course request
//    @PostMapping("/{id}/approve")
//    public ResponseEntity<Enrollment> approveEnrollment(@PathVariable Long id) {
//        Enrollment enrollment = enrollmentService.approveEnrollment(id);
//        return ResponseEntity.ok(enrollment);
//    }

    // Endpoint to add marks to an approved enrollment
    @PostMapping("/{id}/marks")
    public ResponseEntity<Void> addMarks(@PathVariable Long id, @RequestParam Integer marks) {
        enrollmentService.addMarks(id, marks);
        return ResponseEntity.ok().build();
    }

    // Endpoint to get all enrollments for a specific student
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Enrollment>> getEnrollmentsByStudent(@PathVariable Long studentId) {
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudent(studentId);
        return ResponseEntity.ok(enrollments);
    }

    // Endpoint to get all enrollments for a specific course
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Enrollment>> getEnrollmentsByCourse(@PathVariable Long courseId) {
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByCourse(courseId);
        return ResponseEntity.ok(enrollments);
    }
}


