package com.Tezpur.University.Management.System.Backend.service;

import com.Tezpur.University.Management.System.Backend.model.Quiz;
import com.Tezpur.University.Management.System.Backend.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    // Add a new quiz
    public Quiz addQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    // Update an existing quiz
    public Quiz updateQuiz(Long quizId, Quiz updatedQuiz) {
        return quizRepository.findById(quizId).map(quiz -> {
            quiz.setTitle(updatedQuiz.getTitle());
            quiz.setDescription(updatedQuiz.getDescription());
            quiz.setDueDate(updatedQuiz.getDueDate());
            // Update other fields as necessary
            return quizRepository.save(quiz);
        }).orElseThrow(() -> new RuntimeException("Quiz not found with id " + quizId));
    }

    // Get all quizzes
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    // Get a quiz by ID
    public Quiz getQuizById(Long quizId) {
        return quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id " + quizId));
    }

    // Delete a quiz
    public void deleteQuiz(Long quizId) {
        quizRepository.deleteById(quizId);
    }
}
