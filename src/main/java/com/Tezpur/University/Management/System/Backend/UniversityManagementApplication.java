package com.Tezpur.University.Management.System.Backend;

import com.Tezpur.University.Management.System.Backend.model.User;
import com.Tezpur.University.Management.System.Backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class UniversityManagementApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(UniversityManagementApplication.class, args);
    }

    @Override
    public void run(String... args) {
        if(userRepository.findByUsername("admin").isEmpty()){
            User user = new User();
            user.setFirstName("anchal");
            user.setLastName("vashist");
            user.setUsername("admin");
            user.setEmail("anchal@gmail.com");
            user.setPassword(this.passwordEncoder.encode("admin"));
            user.setAddress("Tezpur");
            user.setRole("ROLE_ADMIN");
            user.setAbout("I am very good programmer.");
           // user.setRoles(Set.of(roleRepository.findByName("ROLE_ADMIN")));
            User save = this.userRepository.save(user);
            System.out.println(save);
         }
    }
}
