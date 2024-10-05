package com.Tezpur.University.Management.System.Backend.dto;

import lombok.Data;


@Data
public class QuizMarksResponse {
    private String  name;
    private String  rollNumber;
    private String program;
    private Integer marks;
}
