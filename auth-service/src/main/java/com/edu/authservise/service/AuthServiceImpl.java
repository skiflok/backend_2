package com.edu.authservise.service;

import com.edu.authservise.entity.User;
import com.edu.authservise.repository.UsersRepository;
import com.edu.authservise.security.JwtService;
import com.edu.grpc.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class AuthServiceImpl extends AuthServiceGrpc.AuthServiceImplBase {
    private final UsersRepository usersRepository;
    private final JwtService jwtService;

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
                    //todo
//                    .password(passwordEncoder.encode(request.getPassword()))
                    .password(request.getPassword())
                    .build());

            log.debug("User created: {}", user.getEmail());

            String jwtToken = jwtService.generateToken(user.getEmail());

            JwtTokenReturn response = JwtTokenReturn.newBuilder()
                    .setJwtToken(jwtToken)
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


    //todo
    @Override
    public void accessCheck(AccessCheckRequest request, StreamObserver<JwtTokenReturn> responseObserver) {
        log.debug("request = \n{}", request);

        JwtTokenReturn response = JwtTokenReturn.newBuilder()
                .setJwtToken("this is jwt token :)")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    //todo
    @Override
    public void changePassword(ChangePasswordRequest request, StreamObserver<ChangePasswordResponse> responseObserver) {
        log.debug("request = \n{}", request);

        ChangePasswordResponse response = ChangePasswordResponse.newBuilder()
                .setSuccess(true)
                .setMessage("password change success")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    //todo
    @Override
    public void recoverPassword(PasswordRecoveryRequest request, StreamObserver<PasswordRecoveryResponse> responseObserver) {
        log.debug("request = \n{}", request);

        PasswordRecoveryResponse response = PasswordRecoveryResponse.newBuilder()
                .setSuccess(true)
                .setMessage("password success recover")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
