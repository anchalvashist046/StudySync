package com.Tezpur.University.Management.System.Backend.service;

import com.Tezpur.University.Management.System.Backend.dto.*;
import com.Tezpur.University.Management.System.Backend.model.*;
import com.Tezpur.University.Management.System.Backend.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private AssignmentSubmissionRepository assignmentSubmissionRepository;

    @Autowired
    private SubTopicRepository subTopicRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private QuizSubmissionRepository quizSubmissionRepository;

    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> getUnassignedFacultyCourses() {
        return courseRepository.getUnassignedFacultyCourses();
    }

    public Course getCourse(Long id) {
        return courseRepository.findById(id).get();
    }

    public List<Course> getCoursesByFacultyId(Long facultyId) {
        List<Course> courses = courseRepository.findByFacultyId(facultyId);

        // Check if the courses list is empty or null
        if (courses == null || courses.isEmpty()) {
            throw new EntityNotFoundException("No courses found for faculty ID: " + facultyId);
        }

        return courses;
    }

    public List<Course> getCurrentCoursesOfStudent(Long studentId) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        List<Course> courses = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getGrade() == null)
                courses.add(enrollment.getCourse());
        }
        return courses;
    }

    public List<PastCoursesResponse> getPastEnrollmentsOfStudent(Long studentId) {
        List<PastCoursesResponse> pastCoursesResponseList = new ArrayList<>();
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        enrollments.removeIf(enrollment -> enrollment.getGrade() == null);
        for (Enrollment enrollment : enrollments) {
            PastCoursesResponse pastCoursesResponse = new PastCoursesResponse();
            pastCoursesResponse.setEnrollment(enrollment);
            pastCoursesResponse.setEnrollment(enrollment);
            Course course = courseRepository.findById(enrollment.getId()).get();
            pastCoursesResponse.setCourseName(course.getCourseName());
            pastCoursesResponse.setCourseCode(course.getCourseCode());

            pastCoursesResponseList.add(pastCoursesResponse);
        }
        return pastCoursesResponseList;
    }

    public List<Topic> addTopicByCourseId(Long courseId, List<Topic> topics) {
        Course course = courseRepository.findById(courseId).get();
        for (Topic topic : topics) {
            topic.setCourse(course);

        }
        return topicRepository.saveAll(topics);

    }

    public Subtopic addSubTopicByCourseId(Long topicId, Long courseId, Subtopic subtopic) {
        Course course = courseRepository.findById(courseId).get();
        List<Topic> topicList = course.getTopics().stream().filter(t -> Objects.equals(t.getId(), topicId)).toList();
        Topic topic = topicList.get(0);

        subtopic.setTopic(topic);
        Subtopic subtopic1 = subTopicRepository.save(subtopic);

        return subtopic1;
    }

    public Subtopic addQuiz(Long subTopicId, Long topicId, Long courseId, Quiz quiz) {
        Course course = courseRepository.findById(courseId).get();
        List<Topic> topicList = course.getTopics().stream().filter(t -> Objects.equals(t.getId(), topicId)).toList();
        Topic topic = topicList.get(0);

        Subtopic subtopic = topic.getSubtopics().stream().filter(s -> s.getId().equals(subTopicId)).toList().get(0);

        quizRepository.save(quiz);
        subtopic.setQuiz(quiz);
        return subTopicRepository.save(subtopic);
    }

    public Student enrollCourses(EnrollCourseRequest enrollCourseRequest) {
        List<Long> courseIds = enrollCourseRequest.getCourseIds();
        Long studentId = enrollCourseRequest.getStudentId();
        Integer semester = enrollCourseRequest.getSemester();

        List<Course> courseList = courseRepository.findAllById(courseIds);
        Student student = studentRepository.findById(studentId).get();
        for (Course course : courseList) {
            Enrollment enrollment = new Enrollment();
            enrollment.setCourse(course);
            enrollment.setStudent(student);
            enrollment.setSemester(semester);
            enrollmentRepository.save(enrollment);
        }
        return student;
    }

    public QuizSubmission quizSubmission(QuizSubmissionRequest quizSubmissionRequest) {
        Enrollment enrollment = enrollmentRepository.findByCourseIdAndStudentId(quizSubmissionRequest.getCourseId(), quizSubmissionRequest.getStudentId());
        Quiz quiz = quizRepository.findById(quizSubmissionRequest.getQuizId()).get();
        QuizSubmission quizSubmission = new QuizSubmission();
        quizSubmission.setMarks(quizSubmissionRequest.getMarks());
        quizSubmission.setEnrollment(enrollment);
        quizSubmission.setQuiz(quiz);

        return quizSubmissionRepository.save(quizSubmission);
    }

    public List<QuizMarksResponse> getQuizMarks(QuizMarksRequest quizMarksRequest) {
        Long courseId = quizMarksRequest.getCourseId();
        Long quizId = quizMarksRequest.getQuizId();
        List<QuizMarksResponse> quizMarksResponses = new ArrayList<>();
        Course course = courseRepository.findById(courseId).get();
        if (course.getEnrollments() != null && !course.getEnrollments().isEmpty()) {
            for (Enrollment enrollment : course.getEnrollments()) {
                Student student = enrollment.getStudent();
                User user = userRepository.findByStudentId(student.getId());

                QuizMarksResponse quizMarksResponse = new QuizMarksResponse();
                quizMarksResponse.setName(user.getFirstName() + " " + user.getLastName());
                quizMarksResponse.setProgram(student.getProgram());
                quizMarksResponse.setRollNumber(student.getRollNumber());

                Integer marks = quizSubmissionRepository.getMarksByEnrollmentIdAndQuizId(enrollment.getId(), quizId);
                quizMarksResponse.setMarks(marks);

                quizMarksResponses.add(quizMarksResponse);
            }
        } else return new ArrayList<>();

        return quizMarksResponses;
    }

    public List<EnrolledStudent> getEnrolledStudentsList(Long courseId) {
        List<EnrolledStudent> enrolledStudents = new ArrayList<>();

        Course course = courseRepository.findById(courseId).get();
        if (course.getEnrollments() != null && !course.getEnrollments().isEmpty()) {
            for (Enrollment enrollment : course.getEnrollments()) {
                Student student = enrollment.getStudent();
                User user = userRepository.findByStudentId(student.getId());

                EnrolledStudent enrolledStudent = new EnrolledStudent();
                enrolledStudent.setName(user.getFirstName() + " " + user.getLastName());
                enrolledStudent.setProgram(student.getProgram());
                enrolledStudent.setRollNumber(student.getRollNumber());
                enrolledStudent.setGrade(enrollment.getGrade());
                enrolledStudent.setId(enrollment.getId());

                enrolledStudents.add(enrolledStudent);
            }
        } else return new ArrayList<>();

        return enrolledStudents;
    }

    public List<Course> getCoursesNotEnrolledByStudent(Long id) {
        List<Course> courses = courseRepository.findAll();
        List<Enrollment> enrollmentList = enrollmentRepository.findAll();
        for (Enrollment enrollment : enrollmentList) {
            if (enrollment.getStudent().getId() == id) {
                courses.remove(enrollment.getCourse());
            }
        }

        return courses;
    }

    public Enrollment assignGrade(Long id, String grade) {
        Enrollment enrollment = enrollmentRepository.findById(id).get();
        enrollment.setGrade(grade);
        return enrollmentRepository.save(enrollment);
    }

    public AssignmentSubmission assignMarks(Long id, Integer marks) {
        AssignmentSubmission assignmentSubmission = assignmentSubmissionRepository.findById(id).get();
        assignmentSubmission.setMarks(marks);
        return assignmentSubmissionRepository.save(assignmentSubmission);
    }

    public Student getStudentDetails(Long id) {
        return studentRepository.findById(id).get();
    }

    public Student updateStudentDetails( Long id, Student student) {
        // Retrieve the existing student by ID
        Student existingStudent = studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Student not found"));
        existingStudent.setRollNumber(student.getRollNumber());
        existingStudent.setProgram(student.getProgram());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setBranch(student.getBranch());
        existingStudent.setMobile(student.getMobile());
        existingStudent.setGender(student.getGender());
        existingStudent.setAadhaarNumber(student.getAadhaarNumber());
        existingStudent.setDob(student.getDob());
        existingStudent.setAddress(student.getAddress());
        existingStudent.setBloodGroup(student.getBloodGroup());
        existingStudent.setFatherName(student.getFatherName());
        existingStudent.setFatherOccupation(student.getFatherOccupation());
        existingStudent.setFatherMobile(student.getFatherMobile());
        existingStudent.setMotherName(student.getMotherName());
        existingStudent.setMotherOccupation(student.getMotherOccupation());
        existingStudent.setMotherMobile(student.getMotherMobile());
        return studentRepository.save(existingStudent);
    }

    public Enrollment unEnrollStudentFromCourse(Long studentId, Long courseId) {
     Enrollment enrollment =   enrollmentRepository.findByCourseIdAndStudentId(courseId,studentId);
     enrollmentRepository.delete(enrollment);
     return enrollment;
    }

    public List<Student> getAllStudents() {
        List<Student> students= studentRepository.findAll();
        for (Student student: students){
            User user = userRepository.findByStudentId(student.getId());
            if (user!=null){
                student.setUsername(user.getUsername());
            }
        }
        return students;
    }

    public Student assignStudentsCredentials(UserDto userDto, Long id) {
        User user = this.modelMapper.map(userDto, User.class);
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));

        Student student = studentRepository.findById(id).get();
        user.setStudent(student);
        user.setFirstName(student.getName());
        userRepository.save(user);

        return student;
    }
}
