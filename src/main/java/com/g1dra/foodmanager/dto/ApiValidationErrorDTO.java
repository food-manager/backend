package com.g1dra.foodmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiValidationErrorDTO extends ApiSubError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    public ApiValidationErrorDTO(String object, String message) {
        this.object = object;
        this.message = message;
    }
}
