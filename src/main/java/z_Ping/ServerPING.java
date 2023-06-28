package z_Ping;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class ServerPING {


    public ServerPING(){}

    public void startPING(){

        try {

            System.out.println("Launching chat service on port "+PingServiceImpl.PORT);

            Server server = ServerBuilder.forPort(PingServiceImpl.PORT).addService(new PingServiceImpl()).build();

            server.start();

            System.out.println("Server started!");

            server.awaitTermination();

        } catch (IOException | InterruptedException e) {

            e.printStackTrace();

        }

    }

    /**
    public static void main(String[] args) {
        try {

            System.out.println("Launching chat service on port "+PingServiceImpl.PORT);

            Server server = ServerBuilder.forPort(PingServiceImpl.PORT).addService(new PingServiceImpl()).build();

            server.start();

            System.out.println("Server started!");

            server.awaitTermination();

        } catch (IOException | InterruptedException e) {

            e.printStackTrace();

        }
    }
     */



}
