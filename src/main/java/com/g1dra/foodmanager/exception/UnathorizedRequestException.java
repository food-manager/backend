package com.g1dra.foodmanager.exception;

import org.springframework.http.HttpStatus;

public class UnathorizedRequestException extends FmRuntimeException {
    public UnathorizedRequestException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
