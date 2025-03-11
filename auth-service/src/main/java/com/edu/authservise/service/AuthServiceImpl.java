package com.edu.authservise.service;

import com.edu.grpc.*;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@Slf4j
@GrpcService
public class AuthServiceImpl extends AuthServiceGrpc.AuthServiceImplBase {
    @Override
    public void createUser(CreateUserRequest request, StreamObserver<CreateUserResponse> responseObserver) {
        log.info("create user request = {}", request);

        CreateUserResponse response = CreateUserResponse.newBuilder()
                .setJwtToken("this is jwt token :)")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
