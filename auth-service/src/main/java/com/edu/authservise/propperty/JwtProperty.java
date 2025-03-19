package com.edu.authservise.propperty;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtProperty {
    String secret;
    Long expiration;
}
