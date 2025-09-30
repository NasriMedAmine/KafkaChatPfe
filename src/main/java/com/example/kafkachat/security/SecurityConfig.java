/*
package com.example.kafkachat.security;

import com.example.kafkachat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Autowired private UserService userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/chat/**").authenticated()
                .anyRequest().permitAll()
        ).httpBasic();
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            com.example.kafkachat.Models.User u = userService.findByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            return org.springframework.security.core.userdetails.User
                    .withUsername(u.getUsername())
                    .password("{noop}password")
                    .roles(u.getRole())
                    .build();
        };
    }
}*/
