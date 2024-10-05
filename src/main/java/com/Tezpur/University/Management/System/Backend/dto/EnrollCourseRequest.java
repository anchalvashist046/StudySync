package com.Tezpur.University.Management.System.Backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class EnrollCourseRequest {
    private Long studentId;
    private Integer semester;
    private List<Long> courseIds;
}
