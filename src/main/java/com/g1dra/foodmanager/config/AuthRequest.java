package com.g1dra.foodmanager.config;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class AuthRequest {
    @NotNull(message = "email must not be null")
    @Size(min = 4, max = 50)
    private String email;

    @NotNull(message = "password must not be null")
    @Size(min = 4, max = 128)
    private String password;
}
