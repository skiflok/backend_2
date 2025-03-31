package com.edu.shopservice.service;

import com.edu.grpc.PasswordRecoveryResponse;
import com.edu.shopservice.client.AuthGrpcClient;
import com.edu.shopservice.dto.auth.AuthDto;
import com.edu.shopservice.dto.auth.RegisterDto;
import com.edu.shopservice.dto.auth.ResetPasswordResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;

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

    public ResetPasswordResponseDto reset(String email) throws AuthenticationException {
        PasswordRecoveryResponse response = authGrpcClient.reset(email);
        return ResetPasswordResponseDto.builder()
                .success(response.getSuccess())
                .message(response.getMessage())
                .build();
    }
}
