package com.Tezpur.University.Management.System.Backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.List;

@Entity
@Getter
@Setter
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer semesterName;

    @OneToMany(mappedBy = "semester")
    private List<Course> courses;
}
