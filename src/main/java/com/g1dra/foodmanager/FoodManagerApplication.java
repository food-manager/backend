package com.g1dra.foodmanager;

import com.g1dra.foodmanager.Repositories.UserRepository;
import com.g1dra.foodmanager.models.User;
import com.g1dra.foodmanager.models.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.time.LocalDateTime;

@SpringBootApplication()
public class FoodManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodManagerApplication.class, args);
    }

    @Bean
    public CommandLineRunner userToCheckAuth(UserRepository userRepository) {
        return args -> {
            String salt = BCrypt.gensalt();
            if (userRepository.findByEmail("fmadmin@vegait.rs").isEmpty()) {
                String hashedPassword = BCrypt.hashpw("foodAdmin", salt);
                userRepository.save(
                        User.builder()
                                .name("Admin")
                                .email("fmadmin@vegait.rs")
                                .password(hashedPassword)
                                .role(UserRole.ADMIN)
                                .createdAt(LocalDateTime.now())
                                .build()
                );
            }
        };
    }
}
