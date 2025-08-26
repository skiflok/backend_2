package com.edu.eventservice.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class JwtTokenProvider {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String authUrl;
    private final String username;
    private final String password;
    private final ReentrantLock lock = new ReentrantLock();

    // текущий токен и его expiry
    private String currentToken;
    private Instant expiresAt;

    public JwtTokenProvider(
//            RestTemplate restTemplate,
            @Value("${rest.client.shop-service.auth.url}") String authUrl,
            @Value("${rest.client.shop-service.auth.username}") String username,
            @Value("${rest.client.shop-service.auth.password}") String password
    ) {
//        this.restTemplate = restTemplate;
        this.authUrl = authUrl;
        this.username = username;
        this.password = password;
    }

    /**
     * Возвращает валидный токен, «подтягивает» новый, если нет или истёк.
     */
    public String getToken() {
        // проверка на валидность
        if (currentToken == null || Instant.now().isAfter(expiresAt)) {
            lock.lock();
            try {
                // повторная проверка под замком
                if (currentToken == null || Instant.now().isAfter(expiresAt)) {
                    refreshToken();
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }
        return currentToken;
    }

    private void refreshToken() throws JsonProcessingException {
        // POST { "email": ..., "password": ... } → в header Authorization: Bearer xxx
        ResponseEntity<Void> resp = restTemplate.postForEntity(
                authUrl,
                Map.of("email", username, "password", password),
                Void.class
        );
        String bearer = resp.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (bearer == null || !bearer.startsWith("Bearer ")) {
            throw new IllegalStateException("Не удалось получить JWT");
        }
        currentToken = bearer.substring(7);
        // а теперь пытаемся узнать expiry из самого JWT (без валидации)
        String[] parts = bearer.split("\\.");
        String payload = new String(java.util.Base64.getUrlDecoder().decode(parts[1]));
        // payload вида: { ..., "exp":1750568809, ... }
        long exp = com.fasterxml.jackson.databind.json.JsonMapper
                .builder().build()
                .readTree(payload).get("exp").asLong();
        expiresAt = Instant.ofEpochSecond(exp).minusSeconds(10);
    }
}
