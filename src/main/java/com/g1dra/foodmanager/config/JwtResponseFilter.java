package com.g1dra.foodmanager.config;

import javax.servlet.Filter;

public class JwtResponseFilter implements Filter {
    public JwtResponseFilter(TokenProvider tokenProvider) {
    }
}
