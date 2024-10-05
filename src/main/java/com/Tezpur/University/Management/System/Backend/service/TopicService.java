package com.Tezpur.University.Management.System.Backend.service;

import com.Tezpur.University.Management.System.Backend.model.Subtopic;
import com.Tezpur.University.Management.System.Backend.model.Topic;
import com.Tezpur.University.Management.System.Backend.repository.QuizRepository;
import com.Tezpur.University.Management.System.Backend.repository.SubTopicRepository;
import com.Tezpur.University.Management.System.Backend.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private SubTopicRepository subtopicRepository;

    @Autowired
    private QuizRepository quizRepository;

    // Add a new topic
    public Topic addTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    // Update an existing topic
    public Topic updateTopic(Long id, Topic updatedTopic) {
        return topicRepository.findById(id).map(topic -> {
            topic.setName(updatedTopic.getName());
            topic.setCourse(updatedTopic.getCourse());
            return topicRepository.save(topic);
        }).orElseThrow(() -> new RuntimeException("Topic not found with id " + id));
    }

    // Add a new subtopic
// Add a new subtopic
    public Subtopic addSubtopic(Long topicId, Subtopic subtopic) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Topic not found with id " + topicId));

        subtopic.setTopic(topic); // Set the topic for the subtopic
        return subtopicRepository.save(subtopic); // Save and return the new subtopic
    }

    // Get all topics
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    // Get all subtopics for a topic
    public List<Subtopic> getSubtopicsByTopicId(Long topicId) {
        return subtopicRepository.findByTopicId(topicId);
    }
}
