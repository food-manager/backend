package com.g1dra.foodmanager.seeders;

import com.g1dra.foodmanager.Repositories.UserRepository;
import com.g1dra.foodmanager.models.User;
import com.g1dra.foodmanager.models.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class InitSeederClass implements CommandLineRunner {

    private final UserRepository userRepository;

    private void loadInitUsers() {
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
    }

    @Override
    public void run(String... args) throws Exception {
        loadInitUsers();
    }
}
