package com.Tezpur.University.Management.System.Backend.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String filePath;

    @OneToMany(mappedBy = "assignment", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonManagedReference("assignmentsubmission-assignment")
    private List<AssignmentSubmission> assignmentSubmissions;

    private Date dueDate;

}
