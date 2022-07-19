package com.g1dra.foodmanager.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class JwtResponse {

    private final String accessToken;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public JwtResponse(@JsonProperty("accessToken") String accessToken) {
        this.accessToken = accessToken;
    }
}
