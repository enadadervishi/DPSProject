package _main;

import mosquitto.MosquittoClient;
import restAPI.RestClient;
import restAPI.cleaningRobots.beans.Robot;

import grpcChat.ChatClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import proto.InfosOuterClass;
import z_Ping.ClientPING;

import java.io.IOException;

public class RobotClient {

    //private static Robots existingRobots = null;

    private static final Robot newR= null;

    public RobotClient() {}

    //public static Robots getExistingRobots() {return existingRobots;}

    public static Robot getNewR() {
        return newR;
    }

    public static void main(String[] args) throws IOException, InterruptedException, MqttException {

        /**HERE REST API*/
        //RobotClient robotClient = new RobotClient();
        RestClient trial = new RestClient();
        trial.runClient_REST();


        /** HERE MOSQUITTO WITH AIR POLLUTION LEVELS */
        MosquittoClient trial_mosquitto = new MosquittoClient();
        trial_mosquitto.startingBuffer();


        /** HERE MOSQUITTO */
        trial_mosquitto.start();//trial_mosquitto.startPublishing();


        /** HERE GRPC WITH PROTO FILES */
        //ChatClient trial_chatting = new ChatClient(RestClient.getNewR().getPort());
        //trial_chatting.letsChat();

        ClientPING clientPING = new ClientPING();
        clientPING.startPING();


        /**
        InfosOuterClass.Infos try_infos = InfosOuterClass.Infos.newBuilder()
                .setId("Trying")
                .setDistrict(3)
                .build();

        System.out.println("                HERE PROTO FILE!!!! : "+ try_infos);
*/
        /*
        if(newR.getId().equals("eni"))
            Thread.sleep(10000);
        else
            Thread.sleep(40000);

        System.out.println("Removing "+ newR.getId() + "...");
        robotClient.deleteActualRobot(client, newR.getServerAddress()+ "/cleaning_robots/remove", newR);

        //update the list of the existing robots
        robotClient.response = getRequest(client, newR.getServerAddress()+getPath);
        if (robotClient.response != null) {
            existingRobots = robotClient.response.getEntity(Robots.class);
            //let's check if it's been removed correctly
            existingRobots.printAllRobots_Robots();
            //robotClient.printAllRobots(existingRobots);
        }
         */
    }


}


/**
 Buffer trying_buff = new Buffer() {
@Override
public void addMeasurement(Measurement m) {
if(measurementArrayList.size()<8) {
//System.out.println("HERE TO ADD THE MEASUREMENT TO THE BUFFER");
measurementArrayList.add(m);
//System.out.println("Measurement: " + m.getId() + " " + m.getValue() + " => " + arrayList.indexOf(m));
}else readAllAndClean();
}

@Override
public List<Measurement> readAllAndClean() {
ArrayList<Measurement> newArrayList = new ArrayList<>();
for(Measurement m : measurementArrayList){
//System.out.println("Sum for avg: "+ m.getValue());
averageOfAirPollution = averageOfAirPollution + m.getValue();
if(measurementArrayList.indexOf(m)>3){
newArrayList.add(m);
//System.out.println("ADDED TO A NEW ARRAY: "+  m.getId() + " " +  m.getValue() + " => " + newArrayList.indexOf(m));
}
}
averageOfAirPollution = averageOfAirPollution/8;
System.out.println("Printing average: " + averageOfAirPollution);
existingRobots.getRobotById(newR[0].getId()).getAvgPM10().add(averageOfAirPollution);
System.out.println("     List of averages: " + existingRobots.getRobotById(newR[0].getId()).getAvgPM10());
measurementArrayList = newArrayList;
return measurementArrayList;
}
};
 */