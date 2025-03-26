package com.edu.shopservice.client;

import com.edu.grpc.AuthServiceGrpc;
import com.edu.grpc.CreateUserRequest;
import com.edu.grpc.JwtTokenReturn;
import com.edu.shopservice.dto.auth.RegisterDto;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthGrpcClient {

    @GrpcClient("authGrpcClient")
    private AuthServiceGrpc.AuthServiceBlockingStub authServiceBlockingStub;

    public String createUser(RegisterDto registerDto) {
        CreateUserRequest request = CreateUserRequest.newBuilder()
                .setEmail(registerDto.getEmail())
                .setFirstName(registerDto.getFirstName())
                .setLastName(registerDto.getLastName())
                .setPhoneNumber(registerDto.getPhoneNumber())
                .setPassword(registerDto.getPassword())
                .build();

        try {
            log.info("create user procedure call");
            JwtTokenReturn response = authServiceBlockingStub.createUser(request);
            log.info("create user call success");
            //todo delete after design
            log.debug("jwt token = [{}]", response.getJwtToken());
            return response.getJwtToken();
        } catch (StatusRuntimeException e) {

            //todo need new handler for grpc
            log.error("gRPC error: {}", e.getStatus(), e);
            if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
                throw new EntityNotFoundException("User not found");
            } else if (e.getStatus().getCode() == Status.Code.ALREADY_EXISTS) {
                throw new EntityExistsException("User already exists");
            } else if (e.getStatus().getCode() == Status.Code.INVALID_ARGUMENT) {
                throw new IllegalArgumentException("Invalid request data");
            } else {
                throw new RuntimeException("Internal gRPC error", e);
            }
        } catch (Exception e) {
            log.error("Unexpected error", e);
            throw new RuntimeException("Unexpected error", e);
        }
    }


}
