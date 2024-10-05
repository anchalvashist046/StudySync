package com.Tezpur.University.Management.System.Backend.controller;

import com.Tezpur.University.Management.System.Backend.model.Subtopic;
import com.Tezpur.University.Management.System.Backend.model.Topic;
import com.Tezpur.University.Management.System.Backend.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;

    // Add a new topic
    @PostMapping
    public ResponseEntity<Topic> createTopic(@RequestBody Topic topic) {
        Topic createdTopic = topicService.addTopic(topic);
        return new ResponseEntity<>(createdTopic, HttpStatus.CREATED);
    }

    // Update an existing topic
    @PutMapping("/{id}")
    public ResponseEntity<Topic> updateTopic(@PathVariable Long id, @RequestBody Topic topic) {
        Topic updatedTopic = topicService.updateTopic(id, topic);
        return new ResponseEntity<>(updatedTopic, HttpStatus.OK);
    }

    // Add a new subtopic
    @PostMapping("/{topicId}/subtopics")
    public ResponseEntity<Subtopic> createSubtopic(@PathVariable Long topicId, @RequestBody Subtopic subtopic) {
        Subtopic createdSubtopic = topicService.addSubtopic(topicId, subtopic);
        return new ResponseEntity<>(createdSubtopic, HttpStatus.CREATED);
    }

    // Get all topics
    @GetMapping
    public ResponseEntity<List<Topic>> getAllTopics() {
        List<Topic> topics = topicService.getAllTopics();
        return new ResponseEntity<>(topics, HttpStatus.OK);
    }

    // Get all subtopics by topic ID
    @GetMapping("/{topicId}/subtopics")
    public ResponseEntity<List<Subtopic>> getSubtopicsByTopicId(@PathVariable Long topicId) {
        List<Subtopic> subtopics = topicService.getSubtopicsByTopicId(topicId);
        return new ResponseEntity<>(subtopics, HttpStatus.OK);
    }
}
