package com.Tezpur.University.Management.System.Backend.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference("student-enrollments")
    private Student student;

    @ManyToOne
    @JsonBackReference("course-enrollments")
    private Course course;

    @OneToMany(mappedBy = "enrollment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("quizsubmissions-enrollment")
    private List<QuizSubmission> quizSubmissions;

    @OneToMany(mappedBy = "enrollment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("enrollment-assignment")
    private List<AssignmentSubmission> assignmentSubmissions;

    private Integer semester;

    private String grade;

}
