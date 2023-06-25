package administratorServer;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;
import mosquitto.AdminSub;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.IOException;

/**
 * Check for the robot if:
 * ID is unique && Port is available (always actually) && IP address is available
 */
public class AdminServer {

    private static final String HOST = "localhost";
    private static final int PORT = 8888;

    public static void main(String[] args) throws IOException, MqttException, InterruptedException, ClassNotFoundException {


        AdminSub serverSubscriber = new AdminSub();

        HttpServer server = HttpServerFactory.create("http://" +HOST+":"+PORT+"/");
        server.start();

        System.out.println("Administrator Server is now running!");
        System.out.println("Server started on: http://" +HOST+":"+PORT);

        serverSubscriber.subscription();


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
