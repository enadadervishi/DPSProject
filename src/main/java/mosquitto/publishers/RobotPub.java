package mosquitto.publishers;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * robots publish air pollution levels
 */
public class RobotPub {

    private final MqttClient client;
    private final String broker;
    private final String robotId;
    private final String topic;
    int qos = 2;

    MqttConnectOptions connOpts;


    public RobotPub(int nDistrict) throws MqttException {

        this.broker = "tcp://localhost:1883";
        this.robotId = MqttClient.generateClientId();
        this.topic  = "greenfield/district" +nDistrict; // + nDistrict; in constructor add district

        client = new MqttClient(broker, robotId);
        this.connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);

    }

    public void publishing() throws MqttException {


        // Connect the client
        System.out.println(robotId + " Connecting Broker " + broker);
        client.connect(connOpts);
        System.out.println(robotId + " Connected");

        String payload = String.valueOf(0 + (Math.random() * 10)); // create a random number between 0 and 10
        MqttMessage message = new MqttMessage(payload.getBytes());

        // Set the QoS on the Message
        message.setQos(qos);
        System.out.println(robotId + " Publishing message: " + payload + " ...");
        client.publish(topic, message);
        System.out.println(robotId + " Message published");

        if (client.isConnected())
            client.disconnect();
        System.out.println("Publisher " + robotId + " disconnected");

    }

}
