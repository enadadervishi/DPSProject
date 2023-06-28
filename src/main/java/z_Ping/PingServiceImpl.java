package z_Ping;

import _main.RobotClient;
import example.ping.Ping.*;
import example.ping.PingServiceGrpc.*;
import io.grpc.stub.StreamObserver;
import io.grpc.*;
import restAPI.cleaningRobots.beans.Robots;

import java.util.HashSet;
import java.util.LinkedHashSet;


public class PingServiceImpl extends PingServiceImplBase {

    public final static int PORT = 1998;


    //an hashset to store all the streams which the server uses to communicate with each client
    HashSet<StreamObserver> observers = new LinkedHashSet<StreamObserver>();

    @Override public StreamObserver<PingMessage> sendPing(final StreamObserver<PingMessage> responseObserver){

        //the stream used to communicate with a specific client is stored in a hash set (avoiding duplicates)
        synchronized (observers) {

            observers.add(responseObserver);

        }
        //it returns the stream that will be used by the clients to send messages.
        //the client will write on this stream
        return new StreamObserver<PingMessage>() {

            //receiving a message from a specific client
            public void onNext(PingMessage pingMessage) {

                //unwrapping message
                String message = pingMessage.getMessage();
                String fromId = pingMessage.getId();
                boolean alive = pingMessage.getIsAlive();
                String position = pingMessage.getPosition();
                int district = pingMessage.getDistrict();
                int portUsed = pingMessage.getPort();

                System.out.println("[MESSAGE RECEIVED] From "+fromId
                        + ". Is alive: "+ alive
                        +" Message: "+message
                        +" Position: "+position
                        +" District: "+district
                        +" Port used: "+portUsed);


                HashSet<StreamObserver> copy;

                synchronized (observers) {

                    copy = new HashSet<>(observers);

                }

                //iterating on all the streams to communicate with all the clients
                for(StreamObserver<PingMessage> observer: copy){

                    //we exclude the one which is sending the message
                    if(!observer.equals(responseObserver))
                        //we simply forward the message
                        try {

                            observer.onNext(PingMessage.newBuilder()
                                    .setMessage(message)
                                    .setId(fromId)
                                    .setIsAlive(true)
                                    .setPosition("Position 1 1")
                                    .setDistrict(1)
                                    .setPort(PORT)
                                    .build());

                        }
                        catch(StatusRuntimeException e){
                            //peer no longer available
                            synchronized (observers){

                                observers.remove(observer);

                            }
                        }
                }

            }

            //if there is an error (client abruptly disconnect) we remove the client.
            public void onError(Throwable throwable) {

                synchronized (observers) {

                    observers.remove(responseObserver);

                }
            }

            //if the client explicitly terminated, we remove it from the hashset.
            public void onCompleted() {
                synchronized (observers) {

                    observers.remove(responseObserver);

                }
            }
        };
    }



}