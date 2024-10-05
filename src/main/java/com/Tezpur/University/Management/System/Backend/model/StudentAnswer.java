package com.Tezpur.University.Management.System.Backend.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class StudentAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Quiz quiz;

    @ManyToOne
    private Question question;

    private int selectedOptionIndex; // The index of the option selected by the student
}
