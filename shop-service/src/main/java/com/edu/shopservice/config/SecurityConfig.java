package com.edu.shopservice.config;

import com.edu.shopservice.client.AuthGrpcClient;
import com.edu.shopservice.filter.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthGrpcClient authGrpcClient;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/authorization/**").permitAll() // Открытые эндпоинты
                        .anyRequest().authenticated() // Остальные требуют авторизации
                )
                .addFilterBefore(new JwtAuthenticationFilter(authGrpcClient), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
