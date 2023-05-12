package administratorServer;

import cleaningRobots.beans.Robot;
import cleaningRobots.beans.Robots;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;

/**
 * Check for the robot if:
 * ID is unique && Port is available && IP address is available
 */
public class AdminServer {

    private static final String HOST = "localhost";
    private static final int PORT = 8888;


    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServerFactory.create("http://" +HOST+":"+PORT+"/");
        server.start();

        System.out.println("Administrator Server is now running!");
        System.out.println("Server started on: https://" +HOST+":"+PORT);

        System.out.println("Hit any key to stop...");
        //System.in.read(); //remember to uncomment
        //System.out.println("Stopping server"); // remember to uncomment

        //server.stop(0);
        //System.out.println("Administrator Server stopped");


    }


    //ALL METHODS BELOW FOR ROBOT CLIENT

    public boolean checkID(String chosenID){
        Robots r = new Robots();
        for(Robot rob : r.getRobotsList()){
            if(rob.equals(chosenID))
            {
                System.out.println("ID already used");
                return false;
            }
        }
        System.out.println("ID available");
        return true;
    }

    public boolean checkPORT(int chosenPORT){
        if (PORT != chosenPORT){
            System.out.println("Incorrect port");
            return false;
        }
        System.out.println("Correct port");
        return true;
    }








}
