package com.Tezpur.University.Management.System.Backend.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class AssignmentSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filePath;  // Path to submitted assignment file

    @ManyToOne
    @JsonBackReference("assignmentsubmission-assignment")
    private Assignment assignment; // The assignment this submission is for

    @ManyToOne
    @JsonBackReference("enrollment-assignment")
    private Enrollment enrollment; // The student who submitted the assignment

    private Integer marks;

    private LocalDate submissionDate;
}
