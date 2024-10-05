package com.Tezpur.University.Management.System.Backend.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonManagedReference("quiz-questions")
    private List<Question> questions;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonManagedReference("quiz-quizsubmissions")
    private List<QuizSubmission> quizSubmissions;

    private Date dueDate;

}
