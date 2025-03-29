package com.edu.shopservice.service;

import com.edu.shopservice.client.AuthGrpcClient;
import com.edu.shopservice.dto.auth.AuthDto;
import com.edu.shopservice.dto.auth.RegisterDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthGrpcClient authGrpcClient;

    public String register(RegisterDto registerDto) throws AuthenticationException {
        return authGrpcClient.createUser(registerDto);
    }

    public String auth(AuthDto authDto) throws AuthenticationException {
        return authGrpcClient.auth(authDto);
    }

    public void reset(String email) {
        log.debug("reset pass by email [{}]", email);
    }
}
