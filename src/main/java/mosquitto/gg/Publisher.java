package mosquitto.gg;


import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Publisher is each robot
 * We recognize the number of district in which he belong and
 * the  we publish in that specific topic the air pollution level
 */
public class Publisher {


    private static final int nDistrict = 3; //from 0 to 3 => 4 districts
    public static void main(String[] args) {

        MqttClient client;
        String broker = "tcp://localhost:1883";
        String robotId = MqttClient.generateClientId();
        String topic = "greenfield/district" + nDistrict;
        int qos = 2;

        //brew services start mosquitto


        try{

            client = new MqttClient(broker, robotId);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

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


        }catch (MqttException me){me.printStackTrace();}

    }

}
