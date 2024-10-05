package com.Tezpur.University.Management.System.Backend.repository;

import com.Tezpur.University.Management.System.Backend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
