package mosquitto;

import org.eclipse.paho.client.mqttv3.*;

import java.sql.Timestamp;

/**
 * Admin is subscribed to cleaning robots publishing
 */
public class AdminSub {

    private final MqttClient client;
    private final String broker;
    private final String server;
    private final String topic;
    int qos;

    MqttConnectOptions connOpts;


    public AdminSub() throws MqttException {

        this.broker = "tcp://localhost:1883";
        this.server = MqttClient.generateClientId();
        this.topic = "greenfield/#";
        this.qos = 2;

        this.client = new MqttClient(broker, server); //server -> AdminClient
        this.connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
    }

    public void subscription() throws MqttException{

        // Connect the client
        System.out.println(server + " connecting to broker " + broker);
        client.connect(connOpts);
        System.out.println(server + " connected - Thread PID: " + Thread.currentThread().getId());

        // Callback
        client.setCallback(new MqttCallback() {

            public void messageArrived(String topicWithDistrict, MqttMessage message) {
                // Called when a message arrives from the broker that matches any subscription made by the client
                String time = new Timestamp(System.currentTimeMillis()).toString();
                String receivedMessage = new String(message.getPayload());
                System.out.println(server +" Received a Message! - Callback - Thread PID: " + Thread.currentThread().getId() +
                        "\n\tTime:    " + time +
                        "\n\tTopic:   " + topicWithDistrict +
                        "\n\tAVERAGES OF THE AIR POLLUTION LEVELS: " + receivedMessage +
                        "\n\tQoS:     " + message.getQos() + "\n");

                //System.out.println("\n ***  Press a random key to exit *** \n");

            }

            public void connectionLost(Throwable cause) {
                System.out.println(server + " Connection lost! cause:" + cause.getMessage()+ "-  Thread PID: " + Thread.currentThread().getId());
            }

            public void deliveryComplete(IMqttDeliveryToken token) {
                // Not used here
            }

        });


        System.out.println(server + " Subscribing ... - Thread PID: " + Thread.currentThread().getId());
        client.subscribe(topic,qos);
        System.out.println(server + " Subscribed to topics : " + topic);


        //System.out.println("\n ***  Press a random key to exit *** \n");
        //Scanner command = new Scanner(System.in);
        //command.nextLine();

        /** USED TO DISCONNECT*/
        //client.disconnect();
    }
}
