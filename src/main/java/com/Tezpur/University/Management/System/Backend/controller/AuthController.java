package com.Tezpur.University.Management.System.Backend.controller;

import com.Tezpur.University.Management.System.Backend.dto.JwtRequest;
import com.Tezpur.University.Management.System.Backend.dto.JwtResponse;
import com.Tezpur.University.Management.System.Backend.model.User;
import com.Tezpur.University.Management.System.Backend.repository.UserRepository;
import com.Tezpur.University.Management.System.Backend.security.JwtHelper;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Api(tags = "Auth Controller", value = "AuthController")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) throws Exception {
        this.authenticate(request.getUsername(), request.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
        String token = this.jwtHelper.generateToken(userDetails);
        JwtResponse jwtResponse = new JwtResponse();
        User user = this.userRepository.findByUsername(request.getUsername()).orElseThrow(null);
        jwtResponse.setRole(user.getRole());
        jwtResponse.setJwtToken(token);
        if(user.getRole().equals("ROLE_FACULTY"))
            jwtResponse.setIds(user.getFaculty().getId());

        if(user.getRole().equals("ROLE_STUDENT"))
            jwtResponse.setIds(user.getStudent().getId());
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    public void authenticate(String name, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(name, password));
        } catch (DisabledException e) {
            throw new Exception(" User is disabled !!");
        } catch (BadCredentialsException e) {
            throw new Exception(" Invalid username and password !!");
        }
    }

}