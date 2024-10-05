package com.Tezpur.University.Management.System.Backend.repository;

import com.Tezpur.University.Management.System.Backend.model.QuizSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuizSubmissionRepository extends JpaRepository<QuizSubmission, Long> {

    // Custom query to fetch marks by enrollmentId and quizId
    @Query("SELECT qs.marks FROM QuizSubmission qs WHERE qs.enrollment.id = :enrollmentId AND qs.quiz.id = :quizId")
    Integer getMarksByEnrollmentIdAndQuizId(@Param("enrollmentId") Long enrollmentId, @Param("quizId") Long quizId);
}
