package com.g1dra.foodmanager.contollers;

import com.g1dra.foodmanager.config.AuthRequest;
import com.g1dra.foodmanager.config.JwtResponse;
import com.g1dra.foodmanager.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/auth")
    public ResponseEntity<JwtResponse> createJwtToken(@RequestBody @Valid AuthRequest authRequest) {
        String jwt = userService.createJwtToken(authRequest);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }
}
