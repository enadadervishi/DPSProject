package cleaningRobots;

import cleaningRobots.beans.Robot;
import cleaningRobots.beans.Robots;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import static requestHandler.RequestHandler.*;

public class RobotClient {

    private final BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));

    private String serverAdr;
    private String id;
    private int port;

    private ClientResponse response;

    public static void main(String[] args) throws IOException, InterruptedException {

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
                robotClient.postNewRobot(client, postPath, newR);
            }
        }

        System.out.println(newR.getId() +" WELCOME TO GREENFIELD!");
        if(otherRobots.getRobotsList().isEmpty())
            System.out.println("You're the first cleaning robot of Greenfield");
        else{
            System.out.println("Below a list of all cleaning robots in Greenfield");
            robotClient.printAllRobots(otherRobots);
        }

        if(newR.getId().equals("eni"))
            Thread.sleep(10000);
        else
            Thread.sleep(40000);

        System.out.println("Removing "+ newR.getId() + "...");
        robotClient.deleteActualRobot(client, newR.getServerAddress()+ "/cleaning_robots/remove", newR);

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