package com.Tezpur.University.Management.System.Backend.controller;

import com.Tezpur.University.Management.System.Backend.dto.AssignCourseRequest;
import com.Tezpur.University.Management.System.Backend.model.Course;
import com.Tezpur.University.Management.System.Backend.model.Faculty;
import com.Tezpur.University.Management.System.Backend.service.CourseService;
import com.Tezpur.University.Management.System.Backend.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    @Autowired
    private FacultyService facultyService;
    @Autowired
    private CourseService courseService;

    @PostMapping("/assign/courses")
    public ResponseEntity<Faculty> assignCoursesToFaculty(@RequestBody AssignCourseRequest assignCourseRequest) {
        Faculty faculty = this.facultyService.assignCourses(assignCourseRequest.getFacultyId(), assignCourseRequest.getCourseIds());
        return new ResponseEntity<>(faculty, HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<List<Faculty>> getFaculty() {
        List<Faculty> faculty = this.facultyService.getFaculty();
        return new ResponseEntity<>(faculty, HttpStatus.OK);
    }

    @GetMapping("/get/courses/{id}")
    public ResponseEntity<List<Course>> getCoursesOfFaculty(@PathVariable Long id) {
        List<Course> courses = this.courseService.getCoursesByFacultyId(id);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

}
