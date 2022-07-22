package com.g1dra.foodmanager.integration;

import com.g1dra.foodmanager.Repositories.UserRepository;
import com.g1dra.foodmanager.config.AuthRequest;
import com.g1dra.foodmanager.config.JwtConfig;
import com.g1dra.foodmanager.config.JwtResponse;
import com.g1dra.foodmanager.exception.UnathorizedRequestException;
import com.g1dra.foodmanager.factory.UserFactory;
import com.g1dra.foodmanager.models.User;
import com.g1dra.foodmanager.models.UserRole;
import com.g1dra.foodmanager.utility.PasswordUtility;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserIntegrationTest extends FoodManagerBaseIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtConfig jwtConfig;

    private static final String url = "/api/users";

    List<User> userList = new ArrayList<>();
    User user = new User();

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void createJwtToken_WithValidData_expectedIsOk() throws Exception {
        userList = UserFactory.create(1, UserRole.ADMIN);
        user = userList.get(0);
        AuthRequest authRequest = new AuthRequest(user.getEmail(), user.getPassword());

        String hashedPassword = PasswordUtility.generateHashedPassword(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);

        MvcResult result = mockMvc.perform(post(url + "/auth").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void createJwtToken_WithValidData_checkClaims() throws Exception {
        userList = UserFactory.create(1, UserRole.ADMIN);
        user = userList.get(0);
        AuthRequest authRequest = new AuthRequest(user.getEmail(), user.getPassword());

        String hashedPassword = PasswordUtility.generateHashedPassword(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);

        MvcResult result = mockMvc.perform(post(url + "/auth").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        JwtResponse tokenObject = objectMapper.readValue(response, JwtResponse.class);
        String token = tokenObject.getAccessToken();

        Claims claims = Jwts.parser()
                .setSigningKey(jwtConfig.getSecret())
                .parseClaimsJws(token)
                .getBody();

        assertThat(claims.getSubject()).isEqualTo(user.getEmail());
        assertThat(claims.get("role")).isEqualTo(UserRole.ADMIN.toString());

        long now = (new Date()).getTime();
        assertThat(claims.getExpiration()).isBefore(new Date(now + jwtConfig.getTokenValidity()));
    }

    @Test
    void createJwtToken_WithInvalidEmail_expectedUnauthorized() throws Exception {
        userList = UserFactory.create(1, UserRole.ADMIN);
        user = userList.get(0);
        AuthRequest authRequest = new AuthRequest(user.getEmail() + "anything", user.getPassword());

        String hashedPassword = PasswordUtility.generateHashedPassword(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);

        MvcResult result = mockMvc.perform(post(url + "/auth").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(result1 -> assertTrue(result1.getResolvedException() instanceof UnathorizedRequestException))
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).contains("\"message\":\"Wrong credentials!\"");
    }

    @Test
    void createJwtToken_WithInvalidPassword_expectedUnauthorized() throws Exception {
        userList = UserFactory.create(1, UserRole.ADMIN);
        user = userList.get(0);
        AuthRequest authRequest = new AuthRequest(user.getEmail(), user.getPassword() + "anything");

        String hashedPassword = PasswordUtility.generateHashedPassword(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);

        MvcResult result = mockMvc.perform(post(url + "/auth").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).contains("\"message\":\"Wrong credentials!\"");
    }

    @Test
    void createAuthRequest_WithoutEmail_expectedBadRequest() throws Exception {
        userList = UserFactory.create(1, UserRole.ADMIN);
        user = userList.get(0);
        AuthRequest authRequest = new AuthRequest(null, user.getPassword());

        String hashedPassword = PasswordUtility.generateHashedPassword(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);

        MvcResult result = mockMvc.perform(post(url + "/auth").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(res -> assertTrue(res.getResolvedException() instanceof MethodArgumentNotValidException))
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).contains("\"message\":\"email must not be null\"");
    }

    @Test
    void createAuthRequest_WithoutPassword_expectedBadRequest() throws Exception {
        userList = UserFactory.create(1, UserRole.ADMIN);
        user = userList.get(0);
        AuthRequest authRequest = new AuthRequest(user.getEmail(), null);

        String hashedPassword = PasswordUtility.generateHashedPassword(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);

        MvcResult result = mockMvc.perform(post(url + "/auth").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(res -> assertTrue(res.getResolvedException() instanceof MethodArgumentNotValidException))
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).contains("\"message\":\"password must not be null\"");
    }
}