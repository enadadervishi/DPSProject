package _main;

import grpcChat.ChatServer;
import mosquitto.AdminSub;
import org.eclipse.paho.client.mqttv3.MqttException;
import restAPI.RestServer;

import java.io.IOException;

/**
 * Check for the robot if:
 * ID is unique && Port is available (always actually) && IP address is available
 */
public class AdminServer {

    public static void main(String[] args) throws IOException, MqttException, InterruptedException, ClassNotFoundException {

        System.out.println("    LAUNCHING SERVER REST API...");
        RestServer serverRest = new RestServer();
        serverRest.runServer_REST();

        System.out.println("\n\n    LAUNCHING SERVER MOSQUITTO...");
        AdminSub serverSubscriber = new AdminSub();
        serverSubscriber.subscription();

        System.out.println("\n\n    LAUNCHING SERVER GRPC...");
        ChatServer serverGrpc = new ChatServer();
        serverGrpc.runGRPC();

        System.out.println("\n\n---------------SERVER IS READY---------------\n\n");



        /** ATTEMPT TO EXTRACT VALUES FROM AN ARRAY */
        /**
        //System.out.println("    LET'S CAPIRE SE VA: " + serverSubscriber.getReceivedMessage());

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(serverSubscriber.getReceivedMessage().getBytes());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

            String[] trial_messArr = (String[]) objectInputStream.readObject();
            System.out.println("    TRYING TO UNDERSTAND SE SI PUò DESERIALISEDDDDDDD!!!!!! " + trial_messArr.toString());

         */
        //System.out.println("    HERE THE NAME OF THE ROBOT CONNECTED" + Robots.getInstance().getAPLevelsRobot(serverSubscriber.getNameOfRobot()));

        // System.out.println("Hit any key to stop...");
        // System.in.read(); //remember to uncomment
        // System.out.println("Stopping server"); // remember to uncomment
        // server.stop(0);
        // System.out.println("Administrator Server stopped");

    }

}
