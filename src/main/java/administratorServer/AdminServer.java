package administratorServer;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsServer;

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
        System.out.println("Server started on: http://" +HOST+":"+PORT);

        System.out.println("Hit any key to stop...");
        //System.in.read(); //remember to uncomment
        //System.out.println("Stopping server"); // remember to uncomment

        //server.stop(0);
        //System.out.println("Administrator Server stopped");

    }


}