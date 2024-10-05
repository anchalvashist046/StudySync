package com.Tezpur.University.Management.System.Backend.dto;

import lombok.Data;

import java.time.LocalDate;


@Data
public class AssignmentSubmissionResponse {
    private String name;
    private String program;
    private String rollNumber;
    private String filePath;
    private Integer marks;
    private LocalDate submissionDate;
    private Long id;
}
