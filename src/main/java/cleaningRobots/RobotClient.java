package cleaningRobots;

import cleaningRobots.beans.Robot;
import cleaningRobots.beans.Robots;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import mosquitto.publishers.RobotPub;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import static requestHandler.RequestHandler.*;

public class RobotClient {

    private final BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));

    private static String serverAdr;
    private String id;
    private int port;

    private ClientResponse response;

    private static RobotPub robotPublisher;

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
        Robots existingRobots = robotClient.response.getEntity(Robots.class);
        robotClient.printAllRobots(existingRobots);

        //ask to put a valid ID until it is put
        boolean invalid_input = true;
        while(invalid_input) {
            for(Robot w : existingRobots.getRobotsList()) {
                if(newR.getId().equals(w.getId())) {
                    System.out.println("    ID already used");
                    newR = robotClient.signIn(serverAdr);
                }
            }
            //post the new robot
            invalid_input = false;
            robotClient.postNewRobot(client,postPath,newR);
        }

        //update the list of the existing robots
        robotClient.response = getRequest(client, newR.getServerAddress()+getPath);
        existingRobots = robotClient.response.getEntity(Robots.class);

        //welcoming message
        System.out.println(newR.getId() +" WELCOME TO GREENFIELD!");
        if(existingRobots.getRobotsList().isEmpty())
            System.out.println("You're the first cleaning robot of Greenfield");
        else{
            System.out.println("Below a list of all cleaning robots in Greenfield");
            robotClient.printAllRobots(existingRobots);
        }

        Thread.sleep(10000);

        if(newR.getId().equals("eni"))
            Thread.sleep(10000);
        else
            Thread.sleep(40000);

        System.out.println("Removing "+ newR.getId() + "...");
        robotClient.deleteActualRobot(client, newR.getServerAddress()+ "/cleaning_robots/remove", newR);

        //update the list of the existing robots
        robotClient.response = getRequest(client, newR.getServerAddress()+getPath);
        existingRobots = robotClient.response.getEntity(Robots.class);
        //let's check if it's been removed correctly
        robotClient.printAllRobots(existingRobots);




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
            System.out.println(robotCResponse.toString());
        } catch (NullPointerException ex) {
            System.out.println("robotCResponse: " + robotCResponse);
        }
    }

    public void deleteActualRobot(Client robotC, String deletePath, Robot robot){
        ClientResponse robotCResponse;
        robotCResponse = deleteRequest(robotC, deletePath, robot);
        try {
            System.out.println(robotCResponse.toString());
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