package cleaningRobots;

import cleaningRobots.beans.Robot;
import cleaningRobots.beans.Robots;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static requestHandler.RequestHandler.*;

public class RobotClient {

    private final BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));

    private String serverAdr;
    private String id;
    private int port;

    private ClientResponse response;

    public static void main(String[] args) throws IOException {

        Client client = Client.create();
        RobotClient robotClient = new RobotClient();
        String postPath = "/cleaning_robots/add";
        String getPath = "/cleaning_robots/list";

        robotClient.response = getRequest(client, robotClient.enterAddress());
        while (robotClient.response == null){
            robotClient.response = getRequest(client,robotClient.enterAddress());
        }

        Robot newR = robotClient.signIn(robotClient.serverAdr);
        robotClient.response = getRequest(client, newR.getServerAddress() + getPath);
        Robots otherRobots = robotClient.response.getEntity(Robots.class);
        robotClient.printAllRobots(otherRobots);
        robotClient.postNewRobot(client, postPath, newR);

        for(Robot allRobs: otherRobots.getRobotsList()){
            while((allRobs.getId().equals(newR.getId()))||(allRobs.getPort()==newR.getPort())){
                System.out.println("SOMETHING WENT WRONG");
                if((allRobs.getId().equals(newR.getId()))){
                    System.out.println("    ID already used");
                }else{
                    System.out.println("    PORT already used");
                }
                newR = robotClient.signIn(robotClient.serverAdr);
                robotClient.response = getRequest(client, newR.getServerAddress() + getPath);

                otherRobots = robotClient.response.getEntity(Robots.class);
                robotClient.printAllRobots(otherRobots);
                robotClient.postNewRobot(client, postPath, newR);
            }
        }

        System.out.println("WELCOME TO GREENFIELD, BELOW A LIST OF ALL CLEANING ROBOTS IN GREENFIELD");
        robotClient.printAllRobots(otherRobots);
    }

    public Robot signIn(String serverAdd) throws IOException {
        System.out.println("Now enter an ID: ");
        this.id = buff.readLine();
        System.out.println("Last enter a valid PORT number: ");
        this.port = Integer.parseInt(buff.readLine());
        return new Robot(serverAdd, id, port);
    }

    public String enterAddress() throws IOException {
        System.out.println("HI ROBOT!");
        System.out.println("To start enter a valid server address please: ");
        this.serverAdr = buff.readLine();
        return this.serverAdr;
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

    public void printAllRobots(Robots list) {
        for(Robot l: list.getRobotsList()){
            System.out.println("ID: "+l.getId()+" Port: "+l.getPort() +" Server address: "+ l.getServerAddress());
        }
    }

}