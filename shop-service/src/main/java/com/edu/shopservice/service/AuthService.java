package com.edu.shopservice.service;

import com.edu.shopservice.dto.auth.AuthDto;
import com.edu.shopservice.dto.auth.RegisterDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {


    public String register(@Valid RegisterDto registerDto) {
        return "test token";
    }

    public String auth(@Valid AuthDto authDto) {
        return "test token";
    }

    public void reset(@Valid @Email String email) {
        log.debug("reset pass by email [{}]", email);
    }
}
