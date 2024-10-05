package com.Tezpur.University.Management.System.Backend.dto;

import com.Tezpur.University.Management.System.Backend.model.Course;
import com.Tezpur.University.Management.System.Backend.model.Enrollment;
import lombok.Data;

@Data
public class PastCoursesResponse {
    private Enrollment enrollment;
    private String courseName;
    private String courseCode;
}
