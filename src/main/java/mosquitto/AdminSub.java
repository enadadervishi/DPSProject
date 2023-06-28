package mosquitto;

import org.eclipse.paho.client.mqttv3.*;
import restAPI.cleaningRobots.beans.Robots;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

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

    private ArrayList<String> existingList = new ArrayList<>();
    private String trial;

    //QUIIIIIIIIIII NEWWW
    ArrayList<Double> exampleNew = new ArrayList<>();


    String receivedMessage;
    public String getReceivedMessage() {
        return receivedMessage;
    }

    public AdminSub() throws MqttException {

        this.broker = "tcp://localhost:1883";
        this.server = MqttClient.generateClientId();
        this.topic = "greenfield/#";
        this.qos = 2;

        //this.trial = String.valueOf(Robots.getInstance().getListAPLevelsRobot(Robots.getInstance().getRobotsList().get(Robots.getInstance().getRobotsList().size()).getId()));


        this.receivedMessage = null;

        this.client = new MqttClient(broker, server); //server -> AdminClient
        this.connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);

    }


    public void subscription() throws MqttException{

        // Connect the client
        System.out.println(server + " connecting to broker " + broker);
        client.connect(connOpts);
        System.out.println(server + " connected - Thread PID: " + Thread.currentThread().getId());

        //System.out.println("robot list: "+ Robots.getInstance().getRobotsList() );

        /**
        if(Robots.getInstance().getRobotsList() != null) {

            System.out.println("robot ap levels: " + Robots.getInstance().getListAPLevelsRobot(Robots.getInstance().getRobotsList().get(Robots.getInstance().getRobotsList().size()).getId()));

            for (int i = 1; i < Robots.getInstance().getListAPLevelsRobot("enada").size(); i++) {
                String value = String.valueOf(Robots.getInstance().getListAPLevelsRobot(RobotClient.getNewR().getId()).get(i));  // Get the double value from the original list
                String boxedValue = value;           // Box the double value into a Double object
                existingList.add(boxedValue);             // Add the Double object to the new list
            }
        }
        */


        // Callback
        client.setCallback(new MqttCallback() {

            public void messageArrived(String topicWithDistrict, MqttMessage message){
                // Called when a message arrives from the broker that matches any subscription made by the client
                String time = new Timestamp(System.currentTimeMillis()).toString();
                receivedMessage = new String(message.getPayload()); //String

                /**
                if(Robots.getInstance().getRobotsList().size() != 0 );{

                    //System.out.println("TRYING: "+ trial);
                    System.out.println("LIST: "+ Robots.getInstance().getRobotsList());

                }*/

                /**
                System.out.println("RECEIVED LIST: " + receivedMessage);
                ArrayList<String> receivedList = (ArrayList<String>) Arrays.asList(receivedMessage.split(" "));


                //System.out.println("[AdminSub]:    PRINTING ALL RECEIVED MESSAGE IN ARRAY LIST"+ receivedList);

                //Robots.getInstance().getListAPLevelsRobot(receivedList.get(0)).add(0.00000000000000001);
                //existingList.addAll(receivedList);

                System.out.println("RECEIVED LIST: "+ receivedList);

                //System.out.println("SIZE OF RECEIVED LIST: "+ receivedList.size());
                //System.out.println("PROVA QUI TUTTO BENE"+ exampleNew);
                if(receivedList.size() > 1){

                    for (int i = 1; i < receivedList.size(); i++) {
                        System.out.println("QUI?");
                        String str = receivedList.get(i);
                        double value = Double.parseDouble(str);
                        exampleNew.add(value);
                    }

                }


                System.out.println("PROVA QUI TUTTO BENE"+ exampleNew);
                Robots.getInstance().getRobotById(receivedList.get(0)).setAvgPM10(exampleNew);
                System.out.println("[AdminSub]:     Printing all existing list" + Robots.getInstance().getListAPLevelsRobot(receivedList.get(0)));

                 */

                /**
                String[] valueArray = receivedMessage.split(" ");
                ArrayList<Double> doubleList = new ArrayList<>();
                for (int i = 1; i < valueArray.length; i++) {
                    String str = valueArray[i];
                    double value = Double.parseDouble(str);
                    doubleList.add(value);
                }
                System.out.println("[AdminSub]: before adding all the array to che existing one");


                Robots.getInstance().getListAPLevelsRobot(valueArray[0]).addAll(doubleList);
                System.out.println("    SERVER LET'S SEE THE VALUES"+ Robots.getInstance().getListAPLevelsRobot(valueArray[0]));
                */

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
