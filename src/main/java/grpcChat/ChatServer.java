package grpcChat;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class ChatServer {

    private static final int PORT = 1337;

    public ChatServer(){}


    public void runGRPC() throws IOException, InterruptedException {

        System.out.println("Launching chat service on port "+PORT);


        Server server = ServerBuilder.forPort(PORT).addService(new ChatServiceImpl()).build();
        server.start();
        System.out.println("Server started!");
        //server.awaitTermination();

    }


}
