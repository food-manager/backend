package com.g1dra.foodmanager.services;

import com.g1dra.foodmanager.config.JwtAuthenticationProvider;
import com.g1dra.foodmanager.config.JwtRequest;
import com.g1dra.foodmanager.config.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final JwtAuthenticationProvider authenticationProvider;

    private final TokenProvider tokenProvider;

    public String createJwtToken(JwtRequest jwtRequest) {
        jwtRequest.setEmail(jwtRequest.getEmail().toLowerCase());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(), jwtRequest.getPassword());
        Authentication authentication = authenticationProvider.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);
        return jwt;
    }
}
