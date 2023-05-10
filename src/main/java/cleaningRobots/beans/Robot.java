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

    private String ID;
    private int port;
    private String serverAddress;

    public String getTestToGetAirPollution() {
        // and more vars like: maintenance (boolean), position (coordinates <X> & <Y>), air pollution...
        String testToGetAirPollution = "COOL";
        return testToGetAirPollution;
    }

    public Robot() {}

    public Robot(String ID, int port, String serverAddress) {
        this.ID = ID;
        this.port = port;
        this.serverAddress = serverAddress;
    }

    public String getID() {
        return ID;
    }
}
