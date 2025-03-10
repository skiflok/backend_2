package com.edu.authservise.grpc;

import com.edu.authservise.service.helloservice.HelloServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder
                .forPort(9090)
                .addService(new HelloServiceImpl()).build();

        server.start();
        server.awaitTermination();
    }
}
