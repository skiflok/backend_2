package com.edu.authservise.security;

import com.edu.authservise.property.JwtProperty;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
@Service
public class JwtService {

    //todo del after design
    private final JwtProperty jwtProperty;

    private final long expirationTime;
    private final Key signingKey;

    public JwtService(JwtProperty jwtProperty) {
        this.jwtProperty = jwtProperty;
        this.signingKey = Keys.hmacShaKeyFor(this.jwtProperty.getSecretKey().getBytes(StandardCharsets.UTF_8));
        this.expirationTime = jwtProperty.getExpiration();
    }

    //todo del after design
//    @PostConstruct
//    private void test() {
//        log.info(generateToken("test"));
//        String jws = generateToken("email");
//        boolean isValid = validateToken(jws);
//        String userName = extractUsername(jws);
//        assert Jwts.parser().verifyWith((SecretKey) signingKey).build().parseSignedClaims(jws).getPayload().getSubject().equals("email1");
//    }

    /**
     * Генерация JWT-токена
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
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
