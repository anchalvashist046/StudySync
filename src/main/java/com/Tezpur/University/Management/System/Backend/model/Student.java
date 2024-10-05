package com.Tezpur.University.Management.System.Backend.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String rollNumber;
    private String program;
    private String username;
    private String name;
    private String email;
    private String semester;
    private String batch;
    private String branch;
    private String mobile;
    private String gender;
    private String aadhaarNumber;
    private String dob;
    private String address;
    private String bloodGroup;
    private String fatherName;
    private String fatherOccupation;
    private String fatherMobile;
    private String motherName;
    private String motherOccupation;
    private String motherMobile;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("student-enrollments")
    private List<Enrollment> enrollments;

}
