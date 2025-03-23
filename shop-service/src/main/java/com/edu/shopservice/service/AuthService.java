package com.edu.shopservice.service;

import com.edu.shopservice.dto.auth.RegisterDto;
import jakarta.validation.Valid;
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
}
