package com.Tezpur.University.Management.System.Backend.controller;

import com.Tezpur.University.Management.System.Backend.dto.AssignmentSubmissionResponse;
import com.Tezpur.University.Management.System.Backend.model.*;
import com.Tezpur.University.Management.System.Backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/assignment")
public class AssignmentController {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AssignmentSubmissionRepository submissionRepository;

    @PostMapping("/upload/{topicId}")
    public ResponseEntity<String> uploadAssignment(@RequestPart("file") MultipartFile file, @PathVariable Long topicId) {
        try {
            // Define the destination folder
            String uploadDir = "/C:/Users/vashi/Desktop/Uni/"; // Ensure the path exists and is writable

            // Ensure the directory exists before saving
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                boolean dirCreated = directory.mkdirs();
                if (!dirCreated) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Could not create directory for upload");
                }
            }

            // Store the uploaded file
            String filePath = uploadDir + file.getOriginalFilename();
            File destinationFile = new File(filePath);

            // Transfer the file
            file.transferTo(destinationFile);

            Topic topic = topicRepository.findById(topicId).get();
            // Create a new assignment
            Assignment assignment = new Assignment();
            assignment.setFilePath(filePath);
            assignmentRepository.save(assignment);

            topic.setAssignment(assignment);
            topicRepository.save(topic);

            return ResponseEntity.ok("Assignment uploaded successfully");

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while uploading the file: " + e.getMessage());
        }
    }


    @PostMapping("/submit/{assignmentId}/{enrollmentId}")
    public String submitAssignment(@PathVariable Long assignmentId,
                                   @PathVariable Long enrollmentId,
                                   @RequestPart("file") MultipartFile file) throws IOException {

        // Find the assignment
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        // Store the uploaded file
        String filePath = "/C:/Users/vashi/Desktop/Uni/" + file.getOriginalFilename();
        file.transferTo(new File(filePath));

        // Create a new assignment submission
        AssignmentSubmission submission = new AssignmentSubmission();
        submission.setSubmissionDate(LocalDate.now());
        submission.setAssignment(assignment);
        submission.setFilePath(filePath);
        submission.setEnrollment(enrollmentRepository.findById(enrollmentId).get());
        submissionRepository.save(submission);

        return "Assignment submitted successfully";
    }

    @GetMapping("/download/assignment/{assignmentId}")
    public ResponseEntity<InputStreamResource> downloadAssignment(@PathVariable Long assignmentId) throws FileNotFoundException {

        // Fetch the assignment by ID
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(() -> new RuntimeException("Assignment not found"));

        // Get the file path from the assignment
        String filePath = assignment.getFilePath();
        File file = new File(filePath);

        if (!file.exists() || !file.getName().endsWith(".pdf")) {
            throw new RuntimeException("File not found or not a PDF");
        }

        // Return the file as a downloadable resource
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(file.length())
                .body(resource);
    }

    // API to download a submitted assignment by ID
    @GetMapping("/download/{submissionId}")
    public ResponseEntity<Resource> downloadSubmittedAssignment(@PathVariable Long submissionId) {
        // Find the submission by ID
        AssignmentSubmission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Assignment submission not found"));

        // Load file from the file path
        Path filePath = Paths.get(submission.getFilePath()).toAbsolutePath();
        Resource resource;
        try {
            resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                throw new RuntimeException("File not found " + submission.getFilePath());
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error while downloading file", e);
        }

        // Return file as a response entity
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/list/{assignmentId}")
    public ResponseEntity<List<AssignmentSubmissionResponse>> getSubmissionOfAssignment(@PathVariable Long assignmentId) {
        List<AssignmentSubmission> list = this.assignmentRepository.findById(assignmentId).get().getAssignmentSubmissions();
        List<AssignmentSubmissionResponse> response = new ArrayList<>();
        for (AssignmentSubmission assignmentSubmission: list){
            AssignmentSubmissionResponse assignmentSubmissionResponse = new AssignmentSubmissionResponse();
            assignmentSubmissionResponse.setId(assignmentSubmission.getId());
            User user = userRepository.findByStudentId(assignmentSubmission.getEnrollment().getStudent().getId());
            assignmentSubmissionResponse.setName(user.getFirstName()+" "+user.getLastName());
            assignmentSubmissionResponse.setProgram(assignmentSubmission.getEnrollment().getStudent().getProgram());
            assignmentSubmissionResponse.setMarks(assignmentSubmission.getMarks());
            assignmentSubmissionResponse.setFilePath(assignmentSubmission.getFilePath());
            assignmentSubmissionResponse.setSubmissionDate(assignmentSubmission.getSubmissionDate());
            assignmentSubmissionResponse.setRollNumber(assignmentSubmission.getEnrollment().getStudent().getRollNumber());
            response.add(assignmentSubmissionResponse);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}