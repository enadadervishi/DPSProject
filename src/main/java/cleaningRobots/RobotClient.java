package cleaningRobots;

import cleaningRobots.beans.Robot;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RobotClient {

    private static final String HOST = "localhost";
    private static final int PORT = 8888;
    private static final String serverAddress = "http://" +HOST+":"+PORT;


    public static void main(String[] args) {
        Client robotC = Client.create();
        ClientResponse robotCResponse;


        String postPath = "/cleaning_robots/add";
        //1998 usera con grpc
        Robot firstR = new Robot("FIRST",1998,serverAddress);
        robotCResponse = postRequest(robotC,serverAddress+postPath,firstR);
        try{
            System.out.println(robotCResponse.toString());
        }catch (NullPointerException e){
            System.out.println("robotCResponse: " + robotCResponse);
        }


    }


    public static ClientResponse postRequest(Client client, String url, Robot r){
        WebResource webResource = client.resource(url);
        String input = new Gson().toJson(r);
        try {
            return webResource.type("application/json").post(ClientResponse.class, input);
        } catch (ClientHandlerException e) {
            System.out.println("[POST REQUEST] Server not available");
            return null;
        }
    }
}
