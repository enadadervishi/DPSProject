package grpcChat;

import chatService.ChatServiceGrpc;
import chatService.ChatServiceOuterClass;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChatClient {

    private final static String IP = "localhost";
    private static int PORT = 1337;

    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private String nickname;

    private ManagedChannel channel;
    private ChatServiceGrpc.ChatServiceStub stub;
    private StreamObserver<ChatServiceOuterClass.ChatMessage> serverStream;



    public ChatClient(int p){
        PORT = p;
    }

    public void letsChat() throws IOException {

        System.out.println("What's your nickname?");
        nickname = br.readLine();


        System.out.println("---------");
        System.out.println("[CHAT CLIENT] Connecting to chat server @ "+IP+":"+PORT);

        //opening a connection with chat server

        channel = ManagedChannelBuilder.forTarget(IP+":"+PORT).usePlaintext().build();

        System.out.println("[CHAT CLIENT] Connected!");

        System.out.println("[CHAT CLIENT] Establishing stream from server...");

        //creating the asynchronous stub

        stub = ChatServiceGrpc.newStub(channel);

        //the stub returns a stream (to communicate with the server, and thus with all the other clients).
        //the argument is the stream of messages which are transmitted by the server.

        serverStream = stub.chat(new StreamObserver<ChatServiceOuterClass.ChatMessage>() {

            //remember: all the methods here are CALLBACKS which are handled in an asynchronous manner.

            //we define what to do when a message from the server arrives (just print the message)
            public void onNext(ChatServiceOuterClass.ChatMessage chatMessage) {

                String from = chatMessage.getFrom();
                String message = chatMessage.getMessage();

                System.out.println("\n"+from+" -> "+message);

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
                //we communicate to the server that we are done and we exit the loop
                serverStream.onCompleted();
                break;
            }

            //we use the stream to communicate to the server our message.
            serverStream.onNext(ChatServiceOuterClass.ChatMessage.newBuilder().setFrom(nickname).setMessage(message).build());

        }


        System.out.println("Goodbye!");
    }
}


