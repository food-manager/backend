package com.g1dra.foodmanager.mappers;

import com.g1dra.foodmanager.dto.FoodRequest;
import com.g1dra.foodmanager.models.Food;
import org.springframework.stereotype.Component;

@Component
public class FoodMapper {
    public Food toEntity(FoodRequest food) {
        return Food.builder()
                .name(food.name)
                .build();
    }
}
