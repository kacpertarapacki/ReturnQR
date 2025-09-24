package com.kactar.returnqr.config;

import com.kactar.returnqr.model.User;
import com.kactar.returnqr.model.UserRole;
import com.kactar.returnqr.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminSeeder {

    @Bean
    CommandLineRunner createAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String adminEmail = "admin@returnqr.com";

            if (userRepository.findByEmail(adminEmail).isEmpty()) {
                User admin = new User();
                admin.setName("System Admin");
                admin.setEmail(adminEmail);
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setUserRole(UserRole.ADMIN);

                userRepository.save(admin);

                System.out.println(">>> Admin account created: " + adminEmail + " / admin123");
            } else {
                System.out.println(">>> Admin account already exists");
            }
        };
    }
}
