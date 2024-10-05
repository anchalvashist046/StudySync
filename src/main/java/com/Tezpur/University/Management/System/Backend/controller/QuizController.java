package com.Tezpur.University.Management.System.Backend.controller;

import com.Tezpur.University.Management.System.Backend.model.Quiz;
import com.Tezpur.University.Management.System.Backend.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    // Add a new quiz
    @PostMapping
    public ResponseEntity<Quiz> addQuiz(@RequestBody Quiz quiz) {
        Quiz createdQuiz = quizService.addQuiz(quiz);
        return ResponseEntity.ok(createdQuiz);
    }

    // Update an existing quiz
    @PutMapping("/{quizId}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable Long quizId, @RequestBody Quiz updatedQuiz) {
        Quiz quiz = quizService.updateQuiz(quizId, updatedQuiz);
        return ResponseEntity.ok(quiz);
    }

    // Get all quizzes
    @GetMapping
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        List<Quiz> quizzes = quizService.getAllQuizzes();
        return ResponseEntity.ok(quizzes);
    }

    // Get a quiz by ID
    @GetMapping("/{quizId}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable Long quizId) {
        Quiz quiz = quizService.getQuizById(quizId);
        return ResponseEntity.ok(quiz);
    }

    // Delete a quiz
    @DeleteMapping("/{quizId}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long quizId) {
        quizService.deleteQuiz(quizId);
        return ResponseEntity.noContent().build();
    }
}
