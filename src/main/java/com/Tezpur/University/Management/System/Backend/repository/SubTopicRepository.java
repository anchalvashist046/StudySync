package com.Tezpur.University.Management.System.Backend.repository;

import com.Tezpur.University.Management.System.Backend.model.Subtopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubTopicRepository extends JpaRepository<Subtopic, Long> {
    List<Subtopic> findByTopicId(Long topicId);
}
