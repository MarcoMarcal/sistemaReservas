package com.estudos.reservas.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/users/register", "/users/login",
                                "/tables", "/tables/create", "/tables/delete/{tableNumber}", "tables/update/{numberTable}",
                                "/reservations",
                                "/error").permitAll()
                        .anyRequest().authenticated()
                )
                .logout(LogoutConfigurer::permitAll
                );
        return http.build();
    }
}