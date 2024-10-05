package com.Tezpur.University.Management.System.Backend.dto;

import lombok.Data;


@Data
public class QuizSubmissionRequest {
    private Long studentId;
    private Long courseId;
    private Long quizId;
    private Integer marks;
}
