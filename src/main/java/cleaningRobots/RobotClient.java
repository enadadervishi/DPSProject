package cleaningRobots;

import cleaningRobots.beans.Robot;
import cleaningRobots.beans.Robots;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import fullSimulator.MyBuffer;
import fullSimulator.simulator.Buffer;
import fullSimulator.simulator.Measurement;
import fullSimulator.simulator.PM10Simulator;
import mosquitto.RobotPub;
import org.eclipse.paho.client.mqttv3.MqttException;
import proto.InfosOuterClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import static requestHandler.RequestHandler.*;

public class RobotClient {

    private final BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));

    private static String serverAdr;
    private String id;
    private int port;

    private ClientResponse response;

    private static RobotPub robotPublisher;
    private static ArrayList<Measurement> measurementArrayList = new ArrayList<>();
    private static double averageOfAirPollution = 0;

    public static void main(String[] args) throws IOException, InterruptedException, MqttException {

        Client client = Client.create();
        RobotClient robotClient = new RobotClient();
        String postPath = "/cleaning_robots/add";
        String getPath = "/cleaning_robots/list";

        //till the IP address entered by the user is invalid, ask for it again
        robotClient.response = getRequest(client, robotClient.enterAddress());
        while (robotClient.response == null){
            robotClient.response = getRequest(client,robotClient.enterAddress());
        }

        //when IP address is correct then ask for signing in
        Robot newR = robotClient.signIn(serverAdr);

        //now we have to check if in the serverAdd where this new robot wants to
        //be added there has already existed the same name
        robotClient.response = getRequest(client, newR.getServerAddress()+getPath);
        Robots existingRobots = null;
        if (robotClient.response != null) {
            existingRobots = robotClient.response.getEntity(Robots.class);
            //robotClient.printAllRobots(existingRobots);
            existingRobots.printAllRobots_Robots();

        }

        //ask to put a valid ID until it is put
        boolean invalid_input = true;
        while(invalid_input) {
            if (existingRobots != null) {
                for(Robot w : existingRobots.getRobotsList()) {
                    if(newR.getId().equals(w.getId())) {
                        System.out.println("    ID already used");
                        newR = robotClient.signIn(serverAdr);
                    }
                }
            }
            //post the new robot
            invalid_input = false;
            robotClient.postNewRobot(client,postPath,newR);
        }

        //update the list of the existing robots
        robotClient.response = getRequest(client, newR.getServerAddress()+getPath);
        if (robotClient.response != null) {
            existingRobots = robotClient.response.getEntity(Robots.class);
        }

        //welcoming message
        System.out.println(newR.getId() +" WELCOME TO GREENFIELD!");
        if (existingRobots != null) {
            if(existingRobots.getRobotsList().isEmpty())
                System.out.println("You're the first cleaning robot of Greenfield");
            else{
                System.out.println("Below a list of all cleaning robots in Greenfield");
                existingRobots.printAllRobots_Robots();
                //robotClient.printAllRobots(existingRobots);
            }
        }

        Buffer buff = new MyBuffer(measurementArrayList, averageOfAirPollution);
        PM10Simulator simulator = new PM10Simulator(buff);
        simulator.start(); //IN HERE THE THREAD RUNS AND ADDS VALUES TO THE BUFFER


        //AirPollutionLevels trial = new AirPollutionLevels(measurementArrayList, averageOfAirPollution);

        /** HERE PROTO FILE */


        InfosOuterClass.Infos try_infos = InfosOuterClass.Infos.newBuilder()
                .setId("Trying")
                .setDistrict(3)
                .build();

        System.out.println("                HERE PROTO FILE!!!! : "+ try_infos);


        /** HERE COMMENTED ONLY CAUSE USED WHILE TRUE */


        //let's initialize the mosquitto and connection to district topic
        while (true){
            if (existingRobots != null) {
                robotPublisher = new RobotPub(existingRobots.getRobotById(newR.getId()).getDistrict());
                robotPublisher.publishing();
            }
            Thread.sleep(15000);
        }


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

    public String enterAddress() throws IOException {
        System.out.println("HI ROBOT!");
        System.out.println("To start enter a valid server address please: ");
        serverAdr = buff.readLine();
        return serverAdr;
    }

    public Robot signIn(String serverAdd) throws IOException {
        System.out.println("Enter a valid ID: ");
        this.id = buff.readLine();
        System.out.println("Enter the PORT number: ");
        this.port = Integer.parseInt(buff.readLine());
        return new Robot(serverAdd, id, port);
    }


    public void postNewRobot(Client robotC, String postPath, Robot newR) {
        ClientResponse robotCResponse;
        robotCResponse = postRequest(robotC, newR.getServerAddress() + postPath, newR);
        try {
            if (robotCResponse != null) {
                System.out.println(robotCResponse); //just cause "robotCResponse.toString threw warning
            }
        } catch (NullPointerException ex) {
            System.out.println("robotCResponse: " + robotCResponse);
        }
    }

    public void deleteActualRobot(Client robotC, String deletePath, Robot robot){
        ClientResponse robotCResponse;
        robotCResponse = deleteRequest(robotC, deletePath, robot);
        try {
            if (robotCResponse != null) {
                System.out.println(robotCResponse); //just cause "robotCResponse.toString threw warning
             }
        } catch (NullPointerException ex) {
            System.out.println("robotCResponse: " + robotCResponse);
        }
    }

    public void printAllRobots(Robots list) {
        for(Robot l: list.getRobotsList()){
            System.out.println("ID: "+l.getId()+" Port: "+l.getPort()
                    +" Server address: "+ l.getServerAddress()
                    +" Coordinates: "+ Arrays.toString(l.getCoordinates())
                    +" District: "+ l.getDistrict());
        }
    }

}