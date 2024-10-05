package com.Tezpur.University.Management.System.Backend.controller;

import com.Tezpur.University.Management.System.Backend.dto.*;
import com.Tezpur.University.Management.System.Backend.model.*;
import com.Tezpur.University.Management.System.Backend.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        Course addedCourse = courseService.addCourse(course);
        return ResponseEntity.ok(addedCourse);
    }

    @GetMapping("/no-faculty/courses")
    public ResponseEntity<List<Course>> getUnassignedFacultyCourses() {
        List<Course> courses = courseService.getUnassignedFacultyCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/get/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long courseId) {
        Course course = courseService.getCourse(courseId);
        return ResponseEntity.ok(course);
    }

    @GetMapping("/all/courses")
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/student/list")
    public ResponseEntity<List<Student>> getAllStudent() {
        List<Student> students = courseService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @PostMapping("/student/credentials/{id}")
    public ResponseEntity<Student> assignStudentsCredentials(@RequestBody UserDto userDto,@PathVariable Long id) {
        Student student = courseService.assignStudentsCredentials(userDto,id);
        return ResponseEntity.ok(student);
    }

    @PostMapping("/add/topic/{courseId}")
    public ResponseEntity<List<Topic>> addTopicInCourses(@PathVariable Long courseId, @RequestBody List<Topic> topics) {
        List<Topic> topic1 = courseService.addTopicByCourseId(courseId,topics);
        return ResponseEntity.ok(topic1);
    }

    @PostMapping("/add/subtopic/{topicId}/{courseId}")
    public ResponseEntity<Subtopic> addSubTopicInCourses(@PathVariable Long topicId, @PathVariable Long courseId,@RequestBody Subtopic subtopic) {
        Subtopic subtopic1 = courseService.addSubTopicByCourseId(topicId,courseId,subtopic);
        return ResponseEntity.ok(subtopic1);
    }

    @PostMapping("/add/quiz/{subTopicId}/{topicId}/{courseId}")
    public ResponseEntity<Subtopic> addQuiz(@PathVariable Long subTopicId, @PathVariable Long topicId, @PathVariable Long courseId,@RequestBody Quiz quiz) {
        Subtopic subtopic1 = courseService.addQuiz(subTopicId,topicId,courseId,quiz);
        return ResponseEntity.ok(subtopic1);
    }

    @PostMapping("/enroll/course")
    public ResponseEntity<Student> enrollCoursesToStudent(@RequestBody EnrollCourseRequest enrollCourseRequest) {
        Student student = this.courseService.enrollCourses(enrollCourseRequest);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping("/get/enrolled/{id}")
    public ResponseEntity<List<Course>> getCurrentCoursesOfStudent(@PathVariable Long id) {
        List<Course> courses = this.courseService.getCurrentCoursesOfStudent(id);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/get/past/courses/{studentId}")
    public ResponseEntity<List<PastCoursesResponse>> getPastCoursesOfStudent(@PathVariable Long studentId) {
        List<PastCoursesResponse> pastEnrollments = this.courseService.getPastEnrollmentsOfStudent(studentId);
        return new ResponseEntity<>(pastEnrollments, HttpStatus.OK);
    }

    @GetMapping("/not-enrolled/{id}")
    public ResponseEntity<List<Course>> getCoursesNotEnrolledByStudent(@PathVariable Long id) {
        List<Course> courses = this.courseService.getCoursesNotEnrolledByStudent(id);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @PostMapping("/quiz/submit")
    public ResponseEntity<QuizSubmission> quizSubmit(@RequestBody QuizSubmissionRequest quizSubmissionRequest) {
        QuizSubmission quizSubmission = this.courseService.quizSubmission(quizSubmissionRequest);
        return new ResponseEntity<>(quizSubmission, HttpStatus.OK);
    }

    @PostMapping("/student/quiz/marks")
    public ResponseEntity<List<QuizMarksResponse>> getStudentQuizMarks(@RequestBody QuizMarksRequest quizMarksRequest) {
        return new ResponseEntity<>(this.courseService.getQuizMarks(quizMarksRequest), HttpStatus.OK);
    }

    @GetMapping("/assign/grade/{id}/{grade}")
    public ResponseEntity<Enrollment> assignGrade(@PathVariable Long id, @PathVariable String grade) {
        return new ResponseEntity<>(this.courseService.assignGrade(id,grade), HttpStatus.OK);
    }

    @GetMapping("/assign/marks/{id}/{marks}")
    public ResponseEntity<AssignmentSubmission> assignGrade(@PathVariable Long id, @PathVariable Integer marks) {
        return new ResponseEntity<>(this.courseService.assignMarks(id,marks), HttpStatus.OK);
    }

    @GetMapping("/get/enrolled/students/{courseId}")
    public ResponseEntity<List<EnrolledStudent>> getEnrolledStudentsList(@PathVariable Long courseId) {
        List<EnrolledStudent> enrolledStudentsList = courseService.getEnrolledStudentsList(courseId);
        return ResponseEntity.ok(enrolledStudentsList);
    }

    @PostMapping("/student/{id}")
    public ResponseEntity<Student> updateStudentDetails(@PathVariable Long id, @RequestBody Student student) {
        return new ResponseEntity<>(this.courseService.updateStudentDetails(id,student), HttpStatus.OK);
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<Student> getStudentDetails(@PathVariable Long id) {
        return new ResponseEntity<>(this.courseService.getStudentDetails(id), HttpStatus.OK);
    }

    @PostMapping("/unenroll/{studentId}/{courseId}")
    public ResponseEntity<Enrollment> unEnrollStudentFromCourse(@PathVariable Long studentId,@PathVariable Long courseId) {
        return new ResponseEntity<>(this.courseService.unEnrollStudentFromCourse(studentId,courseId), HttpStatus.OK);
    }
}
