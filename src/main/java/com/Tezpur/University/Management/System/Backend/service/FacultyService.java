package com.Tezpur.University.Management.System.Backend.service;

import com.Tezpur.University.Management.System.Backend.model.*;
import com.Tezpur.University.Management.System.Backend.repository.CourseRepository;
import com.Tezpur.University.Management.System.Backend.repository.FacultyRepository;
import com.Tezpur.University.Management.System.Backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FacultyService {

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    public Faculty assignCourses(Long facultyId, List<Long> courseIds) {
        Faculty faculty = facultyRepository.findById(facultyId).orElse(null);
        List<Course> courseList = courseRepository.findAllById(courseIds);
        assert faculty != null;
        for (Course course:courseList){
            course.setFaculty(faculty);
        }
        courseRepository.saveAll(courseList);
        if(faculty.getCourses().isEmpty()){
            List<Course> courses = new ArrayList<>(courseList);
            faculty.setCourses(courses);
        }
        else {
            faculty.getCourses().addAll(courseList);
        }
        return faculty;
    }

    public List<Faculty> getFaculty() {
        return facultyRepository.findAll();
    }

    public List<Course> getFacultyById(Long id) {
        List<Course> courses = courseRepository.findByFacultyId(id);
        return courses;
    }
}
