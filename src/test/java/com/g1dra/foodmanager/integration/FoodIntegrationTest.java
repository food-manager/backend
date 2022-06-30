package com.g1dra.foodmanager.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.g1dra.foodmanager.FoodManagerApplication;
import com.g1dra.foodmanager.FoodManagerApplicationTests;
import com.g1dra.foodmanager.Repositories.FoodRepository;
import com.g1dra.foodmanager.models.Food;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.hamcrest.Matchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

public class FoodIntegrationTest extends FoodManagerBaseIntegrationTest {

    @Autowired
    private FoodRepository foodRepository;

    @BeforeEach
    void setup(){
        foodRepository.deleteAll();
    }

    @Test
    @WithMockUser
    public void listAllFoods() throws Exception {
        List<Food> emptyList = new ArrayList<>();
        ResultActions response = mockMvc.perform(get("/foods")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emptyList))
        );

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(0)));
    }

    @Test
    public void crateFood() throws Exception {

        /*User user = new User("darko", "password");
        Food food = Food.builder()
                .name("Hrana1")
                .user()
                .build();*/
    }
}
