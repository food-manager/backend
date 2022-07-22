package com.g1dra.foodmanager.exception;

import com.g1dra.foodmanager.dto.ApiErrorDTO;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class FmRuntimeException extends RuntimeException {
    private ApiErrorDTO apiErrorDTO;

    public FmRuntimeException() {
    }

    public FmRuntimeException(HttpStatus status) {
        this.apiErrorDTO = new ApiErrorDTO(status);
    }

    public FmRuntimeException(HttpStatus status, String message) {
        this.apiErrorDTO = new ApiErrorDTO(status, message);
    }

}
