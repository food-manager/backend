package com.g1dra.foodmanager.config;

import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class RegisterFilters extends AbstractHttpConfigurer<RegisterFilters, HttpSecurity> {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        ApplicationContext context = http.getSharedObject(ApplicationContext.class);

        // here we lookup from the ApplicationContext. You can also just create a new instance.
        JwtAuthenticationFilter myFilter = context.getBean(JwtAuthenticationFilter.class);
        http.addFilterBefore(myFilter, UsernamePasswordAuthenticationFilter.class);
    }

    public static RegisterFilters customRegisterFilters() {
        return new RegisterFilters();
    }
}
