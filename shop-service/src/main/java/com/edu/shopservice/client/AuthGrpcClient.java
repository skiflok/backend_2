package com.edu.shopservice.client;

import com.edu.grpc.*;
import com.edu.shopservice.dto.auth.AuthDto;
import com.edu.shopservice.dto.auth.RegisterDto;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

import javax.security.sasl.AuthenticationException;
import java.util.function.Supplier;

@Slf4j
@Component
public class AuthGrpcClient {

    @GrpcClient("authGrpcClient")
    private AuthServiceGrpc.AuthServiceBlockingStub authServiceBlockingStub;

    public String createUser(RegisterDto registerDto) throws AuthenticationException {
        return handleGrpcCall(() -> {
            CreateUserRequest request = CreateUserRequest.newBuilder()
                    .setEmail(registerDto.getEmail())
                    .setFirstName(registerDto.getFirstName())
                    .setLastName(registerDto.getLastName())
                    .setPhoneNumber(registerDto.getPhoneNumber())
                    .setPassword(registerDto.getPassword())
                    .build();
            log.info("Calling createUser gRPC method");
            return authServiceBlockingStub.createUser(request);
        });
    }

    public String auth(AuthDto authDto) throws AuthenticationException {
        return handleGrpcCall(() -> {
            AccessCheckRequest request = AccessCheckRequest.newBuilder()
                    .setEmail(authDto.getEmail())
                    .setPassword(authDto.getPassword())
                    .build();
            log.info("Calling accessCheck gRPC method");
            return authServiceBlockingStub.accessCheck(request);
        });
    }

    public PasswordRecoveryResponse reset(String email) throws AuthenticationException {
        try {
            log.info("gRPC call to reset password");
            PasswordRecoveryResponse response = authServiceBlockingStub.recoverPassword(PasswordRecoveryRequest.newBuilder()
                    .setEmail(email)
                    .build());
            log.info("reset password success");
            return response;
        } catch (StatusRuntimeException e) {
            handleGrpcException(e);
            throw new RuntimeException("Unhandled gRPC error", e);
        } catch (Exception e) {
            log.error("Unexpected error", e);
            throw new RuntimeException("Unexpected error", e);
        }
    }

    private String handleGrpcCall(Supplier<JwtTokenReturn> grpcCall) throws AuthenticationException {
        try {
            JwtTokenReturn response = grpcCall.get();
            log.info("gRPC call successful");
            log.debug("JWT token = [{}]", response.getJwtToken());
            return response.getJwtToken();
        } catch (StatusRuntimeException e) {
            handleGrpcException(e);
            throw new RuntimeException("Unhandled gRPC error", e);
        } catch (Exception e) {
            log.error("Unexpected error", e);
            throw new RuntimeException("Unexpected error", e);
        }
    }

    private void handleGrpcException(StatusRuntimeException e) throws AuthenticationException {
        log.error("gRPC error: {}", e.getStatus(), e);
        Status.Code code = e.getStatus().getCode();

        switch (code) {
            case NOT_FOUND -> throw new EntityNotFoundException("User not found");
            case ALREADY_EXISTS -> throw new EntityExistsException("User already exists");
            case INVALID_ARGUMENT -> throw new IllegalArgumentException("Invalid request data");
            case UNAUTHENTICATED -> throw new AuthenticationException(e.getMessage());
            default -> throw new RuntimeException("Internal gRPC error", e);
        }
    }
}
