package com.g1dra.foodmanager.contollers.exception;

import com.g1dra.foodmanager.dto.ApiErrorDTO;
import com.g1dra.foodmanager.exception.FmRuntimeException;
import com.g1dra.foodmanager.exception.UnathorizedRequestException;
import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler({UnathorizedRequestException.class})
    public ResponseEntity<ApiErrorDTO> handleException(FmRuntimeException e) {
        return ResponseEntity.status(e.getApiErrorDTO().getStatus()).body(e.getApiErrorDTO());
    }

    @ExceptionHandler({ExpiredJwtException.class, ClaimJwtException.class, UnsupportedJwtException.class, MalformedJwtException.class, SignatureException.class})
    public ResponseEntity<ApiErrorDTO> jwtException(RuntimeException e) {
        var apiError = new ApiErrorDTO(HttpStatus.UNAUTHORIZED, e);
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @ExceptionHandler(FmRuntimeException.class)
    public ResponseEntity<ApiErrorDTO> handleFmRuntimeException(Exception e) {
        log.error("Unhandled FM exception: " + e.getMessage(), e);
        var error = new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR, e);
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler({RuntimeException.class, Exception.class})
    public ResponseEntity<ApiErrorDTO> handleRuntimeException(Exception e) {
        log.error("Unhandled exception", e);
        var error = new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR, e);
        return ResponseEntity.status(error.getStatus()).body(error);
    }
}
