package com.Tezpur.University.Management.System.Backend.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class QuizSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference("quiz-quizsubmissions")
    private Quiz quiz;

    @ManyToOne
    @JsonBackReference("quizsubmissions-enrollment")
    private Enrollment enrollment;

    private Integer marks;

    private Date submissionDate;
}
