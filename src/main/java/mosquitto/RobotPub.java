package mosquitto;

import restAPI.RestClient;
import restAPI.cleaningRobots.beans.Robots;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Robots publish averages of air pollution levels
 */
public class RobotPub extends Thread {

    private final MqttClient client;
    private final String broker;
    private final String robotId;
    private final String topic;
    int qos = 2;


    private final String whichRobotIs;

    MqttConnectOptions connOpts;


    //private static ArrayList<Measurement> realTrying_measurementArrayList = new ArrayList<>();
    //private static double realTrying_averageOfAirPollution = 0;

    public RobotPub(int nDistrict) throws MqttException {

        this.broker = "tcp://localhost:1883";
        this.robotId = MqttClient.generateClientId();
        this.topic  = "greenfield/district" +nDistrict;


        this.whichRobotIs = Robots.getInstance().getRobotById(RestClient.getNewR().getId()).getId(); //getExistingRobots().getRobotById(getNewR()[0].getId()).getId();

        client = new MqttClient(broker, robotId);
        this.connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);

    }


    public void publishing() throws MqttException {

        // Connect the client
        System.out.println(robotId + " Connecting Broker " + broker);
        client.connect(connOpts);
        System.out.println(robotId + " Connected");

        //String[] trial_whatToSent = new String[2];
        //trial_whatToSent[0] = this.whichRobotIs;
        //trial_whatToSent[1] = MosquittoClient.getAvgToSend().toString();
        ArrayList<Double> averageToBePublished = MosquittoClient.getAvgToSend();
        ArrayList<String> trialtrial = new ArrayList<>();
        for(Double a: averageToBePublished){

            trialtrial.add(a.toString());
        }
        String stringAverages = String.join(" ", trialtrial).replaceAll(",", "");

        System.out.println("GET AVERAGE TO SEND: "+ MosquittoClient.getAvgToSend() );

        Robots.getInstance().getRobotById(RestClient.getNewR().getId()).setAvgPM10(MosquittoClient.getNewArrayToReplaceInAPLevels());
        //Robots.getInstance().setListAPLevelsRobot(RestClient.getNewR().getId()).addAll(MosquittoClient.getAvgToSend());
        /**ALTRA PROVA*/
        //System.out.println("    PROVAAAA DEL NOVEEE: "+ Robots.getInstance().getListAPLevelsRobot(RestClient.getNewR().getId()));
        System.out.println("    PROVAAAA DEL NOVEEE: "+ Robots.getInstance().getRobotById(RestClient.getNewR().getId()).getAvgPM10());

        /**FINE*/

        //String whatToSend = (this.whichRobotIs + " "+ MosquittoClient.getAvgToSend().toString());
        String whatToSend_trial = (this.whichRobotIs + " "+ stringAverages);

        //MqttMessage trial_message = new MqttMessage(Arrays.toString(trial_whatToSent).getBytes());
        //MqttMessage message = new MqttMessage(whatToSend.getBytes());
        MqttMessage message_trial = new MqttMessage(whatToSend_trial.getBytes());

        // Set the QoS on the Message

        //trial_message.setQos(qos);
        //message.setQos(qos);
        message_trial.setQos(qos);


        //System.out.println(robotId + " publishing the list of averages: " + whatToSend);

        //client.publish(topic, trial_message);
        //client.publish(topic, message);
        client.publish(topic, message_trial);

        //System.out.println(robotId + ": " + whichRobotIs +" published the message");

        /** ONLY IF THE ROBOT IS DEAD*/
        /**
        if (client.isConnected())
            client.disconnect();
        System.out.println("Publisher " + robotId + ": " + whichRobotIs + " disconnected");
         */

   }


}
