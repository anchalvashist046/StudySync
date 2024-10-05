package com.Tezpur.University.Management.System.Backend.dto;

import lombok.Data;


@Data
public class EnrolledStudent {
    private String name;
    private String program;
    private String rollNumber;
    private String grade;
    private Long id;
}
