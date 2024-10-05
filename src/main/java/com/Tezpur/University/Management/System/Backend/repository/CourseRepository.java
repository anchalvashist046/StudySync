package com.Tezpur.University.Management.System.Backend.repository;

import com.Tezpur.University.Management.System.Backend.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course , Long> {

    @Query("SELECT c FROM Course c WHERE c.faculty IS NULL")
    List<Course> getUnassignedFacultyCourses();

    @Query("SELECT c FROM Course c WHERE c.faculty.id = :id")
    List<Course> findByFacultyId(Long id);

}
