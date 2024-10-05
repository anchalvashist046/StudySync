package com.Tezpur.University.Management.System.Backend.service;

import com.Tezpur.University.Management.System.Backend.dto.UserDto;
import com.Tezpur.University.Management.System.Backend.model.*;
import com.Tezpur.University.Management.System.Backend.repository.FacultyRepository;
import com.Tezpur.University.Management.System.Backend.repository.StudentRepository;
import com.Tezpur.University.Management.System.Backend.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDto createUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));

        if(userDto.getRole().equals("ROLE_FACULTY")){
            Faculty faculty = new Faculty();
            faculty.setName(userDto.getFirstName()+" "+userDto.getLastName());
            faculty.setDesignation(userDto.getDesignation());
            user.setFaculty(faculty);
            facultyRepository.save(faculty);
        } else if (userDto.getRole().equals("ROLE_STUDENT")) {
            Student student = new Student();
            student.setProgram(userDto.getProgram());
            student.setName(userDto.getFirstName()+userDto.getLastName());
            student.setRollNumber(userDto.getRollNumber());
            user.setStudent(student);
            studentRepository.save(student);
        }
        this.userRepository.save(user);

        return this.modelMapper.map(user, UserDto.class);
    }
}
