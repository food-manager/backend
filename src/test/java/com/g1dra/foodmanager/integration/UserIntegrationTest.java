package com.g1dra.foodmanager.integration;

import com.g1dra.foodmanager.Repositories.UserRepository;
import com.g1dra.foodmanager.config.AuthRequest;
import com.g1dra.foodmanager.config.JwtConfig;
import com.g1dra.foodmanager.config.JwtResponse;
import com.g1dra.foodmanager.contollers.UserController;
import com.g1dra.foodmanager.models.User;
import com.g1dra.foodmanager.models.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserIntegrationTest extends FoodManagerBaseIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserController userController;

    @Autowired
    private JwtConfig jwtConfig;

    private static final String url = "/api/users";

    User user = new User();
    String name = "TestUser";
    String email = "fmtest@vegait.rs";
    String password = "foodTest";
    UserRole roleAdmin = UserRole.ADMIN;

    @BeforeEach
    void setUp() {
        String salt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(password, salt);

        user = userRepository.save(User.builder()
                .name(name)
                .email(email)
                .password(hashedPassword)
                .role(roleAdmin)
                .createdAt(LocalDateTime.now())
                .build());
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void createJwtToken_WithValidData_expectedIsOk() throws Exception {
        AuthRequest authRequest = new AuthRequest(email, password);

        MvcResult result = mockMvc.perform(post(url + "/auth").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void createJwtToken_WithValidData_checkClaims() {
        AuthRequest authRequest = new AuthRequest(email, password);

        ResponseEntity<JwtResponse> response = userController.createJwtToken(authRequest);
        String token = response.getBody().getAccessToken();

        Claims claims = Jwts.parser()
                .setSigningKey(jwtConfig.getSecret())
                .parseClaimsJws(token)
                .getBody();

        assertThat(claims.getSubject()).isEqualTo(email);
        assertThat(claims.get("role")).isEqualTo(roleAdmin.toString());

        long now = (new Date()).getTime();
        assertThat(claims.getExpiration()).isBefore(new Date(now + jwtConfig.getTokenValidity()));
    }
}