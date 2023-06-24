package mosquitto;

import fullSimulator.simulator.Measurement;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;

import static cleaningRobots.RobotClient.*;

/**
 * Robots publish averages of air pollution levels
 */
public class RobotPub {

    private final MqttClient client;
    private final String broker;
    private final String robotId;
    private final String topic;
    int qos = 2;


    private final String whichRobotIs;

    MqttConnectOptions connOpts;


    private static ArrayList<Measurement> realTrying_measurementArrayList = new ArrayList<>();
    private static double realTrying_averageOfAirPollution = 0;

    public RobotPub(int nDistrict) throws MqttException {

        this.broker = "tcp://localhost:1883";
        this.robotId = MqttClient.generateClientId();
        this.topic  = "greenfield/district" +nDistrict; // + nDistrict; in constructor add district

        this.whichRobotIs = getExistingRobots().getRobotById(getNewR()[0].getId()).getId();

        client = new MqttClient(broker, robotId);
        this.connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);

    }

    public void publishing() throws MqttException {

        // Connect the client
        System.out.println(robotId + " Connecting Broker " + broker);
        client.connect(connOpts);
        System.out.println(robotId + " Connected");

        String whatToSend = ("Robot "+ this.whichRobotIs + " => "+ getAvgToSendThroughMQTT().toString());

        MqttMessage message = new MqttMessage(whatToSend.getBytes());

        // Set the QoS on the Message

        message.setQos(qos);

        System.out.println(robotId + " publishing the list of averages: " + whatToSend);
        client.publish(topic, message);
        System.out.println(robotId + ": " + whichRobotIs +" published the message");

        /** ONLY IF THE ROBOT IS DEAD*/
        /**
        if (client.isConnected())
            client.disconnect();
        System.out.println("Publisher " + robotId + ": " + whichRobotIs + " disconnected");
         */

    }

}
