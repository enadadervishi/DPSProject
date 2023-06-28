package restAPI;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import restAPI.cleaningRobots.beans.Robot;
import restAPI.cleaningRobots.beans.Robots;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import static restAPI.requestHandler.RequestHandler.*;

public class RestClient {

    private final BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
    private ClientResponse response;
    private static String serverAdr;

    private static Robot newR= null;

    private static String id;
    private int port;
    //private ArrayList<Double> aaaaa = new ArrayList<>();

    public RestClient(){}

    public void runClient_REST() throws IOException {

        Client client = Client.create();

        String postPath = "/cleaning_robots/add";
        String getPath = "/cleaning_robots/list";

        //till the IP address entered by the user is invalid, ask for it again
        response = getRequest(client, enterAddress());
        while (response == null){
            response = getRequest(client,enterAddress());
        }

        //when IP address is correct then ask for signing in
        newR = signIn(serverAdr);

        //now we have to check if in the serverAdd where this new robot wants to
        //be added there has already existed the same name
        response = getRequest(client, newR.getServerAddress()+getPath);
        //Robots existingRobots = null;
        if (response != null) {

            Robots.getInstance().setRobotsList(response.getEntity(Robots.class).getRobotsList());//existingRobots = robotClient.response.getEntity(Robots.class);

            //robotClient.printAllRobots(existingRobots);
            Robots.getInstance().printAllRobots_Robots();//existingRobots.printAllRobots_Robots();
        }

        //ask to put a valid ID until it is put
        boolean invalid_input = true;
        while(invalid_input) {
            if ( Robots.getInstance() != null) { //existingRobots
                for(Robot w : Robots.getInstance().getRobotsList() ) { //existingRobots.getRobotsList()
                    if(newR.getId().equals(w.getId())) {
                        System.out.println("    ID already used");
                        newR = signIn(serverAdr);
                    }
                }
            }
            //post the new robot
            invalid_input = false;
            postNewRobot(client,postPath, newR);
        }

        //update the list of the existing robots
        response = getRequest(client, newR.getServerAddress()+getPath);
        if (response != null) {
            Robots.getInstance().setRobotsList(response.getEntity(Robots.class).getRobotsList());//existingRobots = robotClient.response.getEntity(Robots.class);
        }

        //welcoming message
        System.out.println(newR.getId() +" WELCOME TO GREENFIELD!");
        if (Robots.getInstance() != null) { //existingRobots
            if(Robots.getInstance().getRobotsList().isEmpty()) //existingRobots.getRobotsList()
                System.out.println("You're the first cleaning robot of Greenfield");
            else{
                System.out.println("Below a list of all cleaning robots in Greenfield");
                Robots.getInstance().printAllRobots_Robots(); //existingRobots.printAllRobots_Robots();
                //robotClient.printAllRobots(existingRobots);
            }
        }


    }

    public String enterAddress() throws IOException {
        System.out.println("HI ROBOT!");
        System.out.println("To start enter a valid server address please: ");
        serverAdr = buff.readLine();
        return serverAdr;
    }

    public Robot signIn(String serverAdd) throws IOException {
        System.out.println("Enter a valid ID: ");
        id = buff.readLine();
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

    public static Robot getNewR() {
        return newR;
    }

}
