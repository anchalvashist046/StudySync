package com.Tezpur.University.Management.System.Backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import java.util.List;

@Entity
@Getter
@Setter
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JsonBackReference("course-topics")
    private Course course;

    @OneToMany(mappedBy = "topic",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonManagedReference("subtopics-topics")
    private List<Subtopic> subtopics;

    @OneToOne
    private Assignment assignment;
}

