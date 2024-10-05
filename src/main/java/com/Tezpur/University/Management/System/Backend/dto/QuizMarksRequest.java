package com.Tezpur.University.Management.System.Backend.dto;

import lombok.Data;


@Data
public class QuizMarksRequest {
    private Long courseId;
    private Long quizId;
}
