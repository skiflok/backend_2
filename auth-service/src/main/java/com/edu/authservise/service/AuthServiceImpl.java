package com.edu.authservise.service;

import com.edu.grpc.*;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@Slf4j
@GrpcService
public class AuthServiceImpl extends AuthServiceGrpc.AuthServiceImplBase {
    @Override
    public void createUser(CreateUserRequest request, StreamObserver<JwtTokenReturn> responseObserver) {
        log.info("create user request = {}", request);

        JwtTokenReturn response = JwtTokenReturn.newBuilder()
                .setJwtToken("this is jwt token :)")
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
