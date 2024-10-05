package com.Tezpur.University.Management.System.Backend.repository;

import com.Tezpur.University.Management.System.Backend.model.AssignmentSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentSubmissionRepository extends JpaRepository<AssignmentSubmission , Long> {
}
