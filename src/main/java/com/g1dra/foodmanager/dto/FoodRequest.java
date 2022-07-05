package com.g1dra.foodmanager.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FoodRequest {
    public String name;
    public Long user_id;


}
