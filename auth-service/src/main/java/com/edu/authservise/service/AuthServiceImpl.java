package com.edu.authservise.service;

import com.edu.authservise.entity.User;
import com.edu.authservise.repository.UsersRepository;
import com.edu.authservise.security.JwtService;
import com.edu.grpc.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.stream.Collectors;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class AuthServiceImpl extends AuthServiceGrpc.AuthServiceImplBase {
    private final UsersRepository usersRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final SecureRandom secureRandom;

    @Override
    @Transactional
    public void createUser(CreateUserRequest request, StreamObserver<JwtTokenReturn> responseObserver) {
        try {
            log.info("Creating user with email: {}", request.getEmail());

            User user = usersRepository.save(User.builder()
                    .email(request.getEmail())
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .phoneNumber(request.getPhoneNumber())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .build());

            log.debug("User created: {}", user.getEmail());

            JwtTokenReturn response = JwtTokenReturn.newBuilder()
                    .setJwtToken(jwtService.generateToken(user.getEmail()))
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (DataIntegrityViolationException e) {
            log.warn("User with email {} already exists", request.getEmail());
            responseObserver.onError(
                    Status.ALREADY_EXISTS.withDescription("User with this email already exists")
                            .asRuntimeException()
            );
        } catch (Exception e) {
            log.error("Unexpected error", e);
            responseObserver.onError(
                    Status.INTERNAL.withDescription("An unexpected error occurred")
                            .asRuntimeException()
            );
        }
    }

    @Override
    public void accessCheck(AccessCheckRequest request, StreamObserver<JwtTokenReturn> responseObserver) {
        try {
            log.info("AccessCheckRequest by account = [{}]", request.getEmail());

            User user = getUserByEmail(request.getEmail());

            log.info("Account [{}] found in DB", request.getEmail());

            if (isPassNotEqual(request.getPassword(), user.getPassword())) {
                log.warn("Invalid current password for account [{}]", request.getEmail());
                responseObserver.onError(
                        Status.UNAUTHENTICATED.withDescription("Invalid current password").asRuntimeException());
                return;
            }

            String token = jwtService.generateToken(user.getEmail());
            log.info("Token generated successfully for account [{}]", request.getEmail());

            responseObserver.onNext(JwtTokenReturn.newBuilder().setJwtToken(token).build());
            responseObserver.onCompleted();
            log.info("Send token by account [{}]", request.getEmail());

        } catch (IllegalArgumentException e) {
            responseObserver.onError(
                    Status.UNAUTHENTICATED.withDescription(e.getMessage()).asRuntimeException()
            );
            log.warn("Account [{}] does not exist", request.getEmail());
        } catch (Exception e) {
            responseObserver.onError(
                    Status.INTERNAL.withDescription("An unexpected error occurred").asRuntimeException()
            );
            log.error("Unexpected error", e);
        }
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest request, StreamObserver<ChangePasswordResponse> responseObserver) {
        try {
            log.info("Password change request for account = [{}]", request.getEmail());

            User user = getUserByEmail(request.getEmail());

            if (isPassNotEqual(request.getOldPassword(), user.getPassword())) {
                log.warn("Invalid current password for account [{}]", request.getEmail());
                responseObserver.onError(Status.UNAUTHENTICATED.withDescription("Invalid current password").asRuntimeException());
                return;
            }

            String newPasswordHash = passwordEncoder.encode(request.getNewPassword());
            user.setPassword(newPasswordHash);
            usersRepository.save(user);

            log.info("Password changed successfully for account [{}]", request.getEmail());

            responseObserver.onNext(ChangePasswordResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Password changed successfully")
                    .build());
            responseObserver.onCompleted();

        } catch (IllegalArgumentException e) {
            log.warn("Account [{}] does not exist", request.getEmail());
            responseObserver.onError(Status.UNAUTHENTICATED.withDescription(e.getMessage()).asRuntimeException());
        } catch (Exception e) {
            log.error("Unexpected error", e);
            responseObserver.onError(Status.INTERNAL.withDescription("An unexpected error occurred").asRuntimeException());
        }
    }

    @Override
    @Transactional
    public void recoverPassword(PasswordRecoveryRequest request, StreamObserver<PasswordRecoveryResponse> responseObserver) {
        try {
            log.info("Password recovery request for account = [{}]", request.getEmail());

            User user = getUserByEmail(request.getEmail());

            String tempPassword = generateSecureTemporaryPassword();
            String hashedPassword = passwordEncoder.encode(tempPassword);

            user.setPassword(hashedPassword);
            usersRepository.save(user);

            sendTempPassToUserEmail(request.getEmail(), tempPassword);

            responseObserver.onNext(PasswordRecoveryResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Temporary password has been sent to your email")
                    .build());
            responseObserver.onCompleted();

            // todo need send to notification service
            log.info("new password by user {} = [{}]", user.getEmail(), tempPassword);
        } catch (IllegalArgumentException e) {
            log.warn("Account [{}] does not exist", request.getEmail());
            responseObserver.onError(Status.UNAUTHENTICATED.withDescription(e.getMessage()).asRuntimeException());
        } catch (Exception e) {
            log.error("Unexpected error", e);
            responseObserver.onError(Status.INTERNAL.withDescription("An unexpected error occurred").asRuntimeException());
        }
    }

    @Override
    public void validateToken(TokenValidationRequest request, StreamObserver<TokenValidationResponse> responseObserver) {
        try {
            log.info("Validate token start");
            String email = jwtService.extractUsername(request.getToken());
            log.info("Validate token success by email [{}]", email);

            responseObserver.onNext(TokenValidationResponse.newBuilder()
                    .setEmail(email)
                    .build());
            responseObserver.onCompleted();
        } catch (SignatureException e) {
            log.warn("Error ", e);
            responseObserver.onError(Status.UNAUTHENTICATED.withDescription(e.getMessage()).asRuntimeException());
        } catch (Exception e) {
            log.error("Unexpected error", e);
            responseObserver
                    .onError(Status.INTERNAL.withDescription("An unexpected error occurred").asRuntimeException());
        }
    }

    //todo need send by email logic
    private void sendTempPassToUserEmail(String email, String tempPassword) {
        log.info("Temporary password for account [{}]: [{}]", email, tempPassword);

    }

    private boolean isPassNotEqual(String requestPass, String currentPass) {
        return !passwordEncoder.matches(requestPass, currentPass);
    }

    private String generateSecureTemporaryPassword() {
        return secureRandom.ints(8, 33, 122)
                .mapToObj(i -> String.valueOf((char) i))
                .collect(Collectors.joining());
    }

    private User getUserByEmail(String email) {
        return usersRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User with this email does not exist"));
    }
}
