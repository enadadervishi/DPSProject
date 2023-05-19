package mosquitto.gg;

import org.eclipse.paho.client.mqttv3.*;

import java.sql.Timestamp;
import java.util.Scanner;

/**
 * The subscriber is subscribed to all districts #
 */
public class Subscriber {



    public static void main(String[] args) {

        MqttClient client;
        String broker = "tcp://localhost:1883";
        String server = MqttClient.generateClientId();
        String topic = "greenfield/#";
        int qos = 2;

        try {

            client = new MqttClient(broker, server); //server -> AdminClient
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            // Connect the client
            System.out.println(server + " Connecting Broker " + broker);
            client.connect(connOpts);
            System.out.println(server + " Connected - Thread PID: " + Thread.currentThread().getId());

            // Callback
            client.setCallback(new MqttCallback() {

                public void messageArrived(String topicWithDistrict, MqttMessage message) {
                    // Called when a message arrives from the broker that matches any subscription made by the client
                    String time = new Timestamp(System.currentTimeMillis()).toString();
                    String receivedMessage = new String(message.getPayload());
                    System.out.println(server +" Received a Message! - Callback - Thread PID: " + Thread.currentThread().getId() +
                            "\n\tTime:    " + time +
                            "\n\tTopic:   " + topic +
                            "\n\tMessage: " + receivedMessage +
                            "\n\tQoS:     " + message.getQos() + "\n");

                    System.out.println("\n ***  Press a random key to exit *** \n");

                }

                public void connectionLost(Throwable cause) {
                    System.out.println(server + " Connectionlost! cause:" + cause.getMessage()+ "-  Thread PID: " + Thread.currentThread().getId());
                }

                public void deliveryComplete(IMqttDeliveryToken token) {
                    // Not used here
                }

            });

            System.out.println(server + " Subscribing ... - Thread PID: " + Thread.currentThread().getId());
            client.subscribe(topic,qos);
            System.out.println(server + " Subscribed to topics : " + topic);


            System.out.println("\n ***  Press a random key to exit *** \n");
            Scanner command = new Scanner(System.in);
            command.nextLine();
            client.disconnect();



        }catch (MqttException me){

            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();

        }







    }


}