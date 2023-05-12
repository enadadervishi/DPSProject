package administratorClient;

import cleaningRobots.beans.Robots;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * This application prints a straightforward menu to select one of the services offered
 * by the administrator server described, such as:
 * - The list of the cleaning robots currently located in Greenfield
 * - The average of the last n air pollution levels sent to the server by a given robot
 * - The average of the air pollution levels sent by all the robots to the server and
 *  occurred from timestamps t1 and t2
 */
public class AdminClient {

    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {

        Client client = Client.create();
        String serverAddress = "http://localhost:8888";
        ClientResponse clientResponse;

        String choicePath;


        while(true){
            System.out.println("Please select a number from the menu: ");
            System.out.println("1. Overview of Cleaning Robots " +
                    "\n2. Average of Air Pollution levels by a specific Robot" +
                    "\n3. Average of Air Pollution levels by all Robots" +
                    "\n4. Exit");
            String choice = bufferedReader.readLine();


            switch (choice) {
                case "1":
                    System.out.println("You want an overview of Cleaning Robots");

                    // the choice result should be sent from the server?
                    choicePath = "/cleaning_robots/list";
                    clientResponse = getRequest(client, serverAddress + choicePath);

                    try {
                        System.out.println(clientResponse.toString());
                        Robots robotsResponse = clientResponse.getEntity(Robots.class);
                        System.out.println("List of robots " + robotsResponse.getRobotsList().toString());
                    } catch (NullPointerException e) {
                        System.out.println("clientResponse: " + clientResponse);
                    }

                    break;
                case "2":
                    System.out.println("You want an average of Air Pollution levels by a specific Robot");
                    choicePath = "/cleaning_robots/{robot}/air_pollution_level/"; //STILL NOT DEFINED

                    break;
                case "3":
                    System.out.println("You want an average of Air Pollution levels by all Robots");
                    choicePath = "/cleaning_robots/get/air_pollution_level"; //STILL NOT DEFINED

                    break;
                case "4":
                    System.out.println("You want to exit. Goodbye!");
                    System.exit(0);
                    System.out.println("Server stopped");
                    break;
                default:
                    System.out.println("Invalid input, try again");
                    break;
            }
        }


        /*
        GET REQUEST #2
        System.out.println("What word are you looking for? ");
        String getWordPath = "/words/get/"+bufferedReader.readLine();
        clientResponse = getRequest(client,serverAddress+getWordPath);
        System.out.println(clientResponse.toString());
        Word wordResponse = clientResponse.getEntity(Word.class);
        System.out.println("Term: " + wordResponse.getTerm() + " Definition: " + wordResponse.getDefinition());
        */


    }


    public static ClientResponse getRequest(Client client, String url){
        WebResource webResource = client.resource(url);
        try {
            return webResource.type("application/json").get(ClientResponse.class);
        } catch (ClientHandlerException e) {
            System.out.println("[getRequest AdminClient] Server not available");
            return null;
        }
    }

}
