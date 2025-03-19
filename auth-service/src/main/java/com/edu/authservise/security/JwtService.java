package com.edu.authservise.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private static String secretKey; // Должен быть минимум 256 бит
    @Value("${jwt.expiration}")
    private static final long EXPIRATION_TIME_MS = 3600000; // 1 час
    Key signingKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));


    //    private final Key signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
//    private final Key signingKey = Jwts.SIG.HS256.key().build();


    @PostConstruct
    private void test() {
        String pass = "1234567890qwerty1234567890qwerty";
        String encodedPass = Base64.getEncoder().encodeToString(pass.getBytes(StandardCharsets.UTF_8));

        Key testKey = Keys.hmacShaKeyFor(pass.getBytes(StandardCharsets.UTF_8));

        log.info(generateToken("test"));


        String jws = generateToken("email");

        boolean isValid = validateToken(jws);

        String userName = extractUsername(jws);

        assert Jwts.parser().verifyWith((SecretKey) signingKey).build().parseSignedClaims(jws).getPayload().getSubject().equals("email");

    }

    /**
     * Генерация JWT-токена
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
//                .header()
//                    .keyId("123")
//                    .and()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MS))
                .signWith(signingKey)
                .compact();
    }

    /**
     * Валидация JWT-токена
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) signingKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Извлечение имени пользователя из токена
     */
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
