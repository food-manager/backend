package com.g1dra.foodmanager.config;

import javax.servlet.Filter;

public class JwtRequestFilter implements Filter {
    public JwtRequestFilter(TokenProvider tokenProvider) {
    }
}
