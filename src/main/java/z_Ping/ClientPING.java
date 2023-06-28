package z_Ping;

import example.ping.Ping.*;
import example.ping.PingServiceGrpc;
import example.ping.PingServiceGrpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientPING {

    public final static String IP = "localhost";
    //public final static int PORT = 1998;

    public ClientPING(){}

    public void startPING() throws IOException {

        //buffered reader to read from standard input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        //simply asking the nickname
        String nickname;

        System.out.println("What's your nickname?");
        nickname = br.readLine();


        System.out.println("---------");
        System.out.println("[CHAT CLIENT] Connecting to chat server @ "+IP+":"+PingServiceImpl.PORT);

        //opening a connection with chat server

        final ManagedChannel channel = ManagedChannelBuilder.forTarget(IP+":"+PingServiceImpl.PORT).usePlaintext().build();

        System.out.println("[CHAT CLIENT] Connected!");

        System.out.println("[CHAT CLIENT] Establishing stream from server...");

        //creating the asynchronous stub

        PingServiceStub stub = PingServiceGrpc.newStub(channel);

        //the stub returns a stream (to communicate with the server, and thus with all the other clients).
        //the argument is the stream of messages which are transmitted by the server.

        StreamObserver<PingMessage> serverStream = stub.sendPing(new StreamObserver<PingMessage>() {

            //remember: all the methods here are CALLBACKS which are handled in an asynchronous manner.

            //we define what to do when a message from the server arrives (just print the message)
            public void onNext(PingMessage pingMessage) {

                String from = pingMessage.getId();
                String message = pingMessage.getMessage();
                boolean alive = pingMessage.getIsAlive();
                String position = pingMessage.getPosition();
                int district = pingMessage.getDistrict();
                int portUsed = pingMessage.getPort();

                System.out.println("\n"+from+" is alive: "+alive
                        + " and they say: "+ message
                        +". Position: "+position
                        +" District: "+district
                        +" Port used: "+portUsed);

            }

            public void onError(Throwable throwable) {

            }

            public void onCompleted() {

            }
        });


        System.out.println("[CHAT CLIENT] Now you can chat :)");


        //dumb code to handle the chat
        //it is a while loop which reads the message
        while(true){

            String message = br.readLine();


            //if the message is quit
            if(message.equals("quit")){
                serverStream.onNext(PingMessage.newBuilder()
                        .setId(nickname)
                        .setMessage(message)
                        .setIsAlive(false)
                        .setPosition("Position 0 cause this robot decides to quit")
                        .setDistrict(0)
                        .setPort(0)
                        .build());
                //we communicate to the server that we are done and we exit the loop
                serverStream.onCompleted();

                break;
            }

            //we use the stream to communicate to the server our message
            serverStream.onNext(PingMessage.newBuilder()
                    .setId(nickname)
                    .setMessage(message)
                    .setIsAlive(true)
                    .setPosition("Position 1 1")
                    .setDistrict(1)
                    .setPort(PingServiceImpl.PORT)
                    .build());

        }


        System.out.println("Goodbye!");
    }


    /**
    public static void main(String[] args) throws IOException {

        //buffered reader to read from standard input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        //simply asking the nickname
        String nickname;

        System.out.println("What's your nickname?");
        nickname = br.readLine();


        System.out.println("---------");
        System.out.println("[CHAT CLIENT] Connecting to chat server @ "+IP+":"+PingServiceImpl.PORT);

        //opening a connection with chat server

        final ManagedChannel channel = ManagedChannelBuilder.forTarget(IP+":"+PingServiceImpl.PORT).usePlaintext().build();

        System.out.println("[CHAT CLIENT] Connected!");

        System.out.println("[CHAT CLIENT] Establishing stream from server...");

        //creating the asynchronous stub

        PingServiceStub stub = PingServiceGrpc.newStub(channel);

        //the stub returns a stream (to communicate with the server, and thus with all the other clients).
        //the argument is the stream of messages which are transmitted by the server.

        StreamObserver<PingMessage> serverStream = stub.sendPing(new StreamObserver<PingMessage>() {

            //remember: all the methods here are CALLBACKS which are handled in an asynchronous manner.

            //we define what to do when a message from the server arrives (just print the message)
            public void onNext(PingMessage pingMessage) {

                String from = pingMessage.getId();
                String message = pingMessage.getMessage();
                boolean alive = pingMessage.getIsAlive();
                String position = pingMessage.getPosition();
                int district = pingMessage.getDistrict();
                int portUsed = pingMessage.getPort();

                System.out.println("\n"+from+" is alive: "+alive
                        + " and they say: "+ message
                        +". Position: "+position
                        +" District: "+district
                        +" Port used: "+portUsed);

            }

            public void onError(Throwable throwable) {

            }

            public void onCompleted() {

            }
        });


        System.out.println("[CHAT CLIENT] Now you can chat :)");


        //dumb code to handle the chat
        //it is a while loop which reads the message
        while(true){

            String message = br.readLine();


            //if the message is quit
            if(message.equals("quit")){
                serverStream.onNext(PingMessage.newBuilder()
                        .setId(nickname)
                        .setMessage(message)
                        .setIsAlive(false)
                        .setPosition("Position 0 cause this robot decides to quit")
                        .setDistrict(0)
                        .setPort(0)
                        .build());
                //we communicate to the server that we are done and we exit the loop
                serverStream.onCompleted();

                break;
            }

            //we use the stream to communicate to the server our message
            serverStream.onNext(PingMessage.newBuilder()
                    .setId(nickname)
                    .setMessage(message)
                    .setIsAlive(true)
                    .setPosition("Position 1 1")
                    .setDistrict(1)
                    .setPort(PingServiceImpl.PORT)
                    .build());

        }


        System.out.println("Goodbye!");
    }
*/

}