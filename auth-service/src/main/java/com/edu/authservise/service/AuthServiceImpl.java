package com.edu.authservise.service;

import com.edu.authservise.entity.User;
import com.edu.authservise.repository.UsersRepository;
import com.edu.authservise.security.JwtService;
import com.edu.grpc.*;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
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
        log.info("create user request = {}", request);

        User user = usersRepository.save(User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .password(request.getPassword())
                .build());

        log.debug(String.valueOf(user));

        String jwtToken = jwtService.generateToken(user.getEmail());

        log.debug("jwt token = {}", jwtToken);

        JwtTokenReturn response = JwtTokenReturn.newBuilder()
                .setJwtToken(jwtToken)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void accessCheck(AccessCheckRequest request, StreamObserver<JwtTokenReturn> responseObserver) {
        log.debug("request = \n{}", request);

        JwtTokenReturn response = JwtTokenReturn.newBuilder()
                .setJwtToken("this is jwt token :)")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

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
