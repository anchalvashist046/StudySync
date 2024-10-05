package com.Tezpur.University.Management.System.Backend.repository;

import com.Tezpur.University.Management.System.Backend.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByCourseId(Long courseId);

    List<Enrollment> findByStudentId(Long studentId);

    Enrollment findByCourseIdAndStudentId(Long courseId, Long studentId);
}
