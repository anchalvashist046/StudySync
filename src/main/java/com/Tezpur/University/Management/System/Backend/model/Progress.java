package com.Tezpur.University.Management.System.Backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.List;

@Entity
@Getter
@Setter
public class Progress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User student;

    @ManyToOne
    private Course course;

    @OneToMany
    private List<AssignmentSubmission> assignmentSubmissions;

    @OneToMany
    private List<QuizSubmission> quizSubmissions;
}

