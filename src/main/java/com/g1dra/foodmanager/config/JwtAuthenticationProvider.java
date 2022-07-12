package com.g1dra.foodmanager.config;

import com.g1dra.foodmanager.Repositories.UserRepository;
import com.g1dra.foodmanager.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Optional<User> optionalUser = userRepository.findByEmail((String) authentication.getPrincipal());

        return optionalUser.map(user -> {
                    String plainPassword = (String) authentication.getCredentials();
                    if (!BCrypt.checkpw(plainPassword.trim(), user.getPassword())) {
                        throw new RuntimeException("Wrong credentials");
                    }

                    return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), Arrays.asList(new SimpleGrantedAuthority(user.getRole().toString())));
                })
                .orElseThrow(() -> new RuntimeException("Wrong credentials"));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
