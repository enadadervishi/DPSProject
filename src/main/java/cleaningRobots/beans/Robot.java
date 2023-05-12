package cleaningRobots.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * A cleaning robot is initialized by specifying:
 * (the constructor has as param)
 * - ID
 * - Listening port for communications with the other robots
 * - Administrator Server's address
 * Check if: ID is unique && Port is available && IP address is available
 */
@XmlRootElement
public class Robot {

    private String id;
    private int port;
    private String serverAddress;

    public String getTestToGetAirPollution() {
        // and more vars like: maintenance (boolean), position (coordinates <X> & <Y>), air pollution...
        String testToGetAirPollution = "COOL";
        return testToGetAirPollution;
    }

    public Robot() {}

    public Robot(String id, int port, String serverAddress) {
        this.id = id;
        this.port = port;
        this.serverAddress = serverAddress;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }

    public String getServerAddress() {
        return serverAddress;
    }
    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }



}
