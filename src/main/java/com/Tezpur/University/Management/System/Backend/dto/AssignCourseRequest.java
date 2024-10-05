package com.Tezpur.University.Management.System.Backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class AssignCourseRequest {
    private Long facultyId;
    private List<Long> courseIds;
}
