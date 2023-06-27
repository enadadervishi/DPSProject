package restAPI;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;

public class RestServer {

    private static final String HOST = "localhost";
    private static final int PORT = 8888;

    public RestServer(){}

    public void runServer_REST() throws IOException {

        HttpServer server = HttpServerFactory.create("http://" +HOST+":"+PORT+"/");

        server.start();

        System.out.println("Administrator Server is now running!");
        System.out.println("Server started on: http://" +HOST+":"+PORT);


    }

}
