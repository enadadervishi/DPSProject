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

    private String serverAddress;
    private String id;
    private int port;

    private int[] coordinates;
    private int district;
    private double avgPM10;

    public Robot() {}

    public Robot(String serverAddress, String id, int port ) {
        this.serverAddress = serverAddress;
        this.id = id;
        this.port = port;
        this.coordinates = new int[2];
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

    public int[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(int[] coordinates) {
        this.coordinates = coordinates;
    }

    public int getDistrict() {
        return district;
    }

    public void setDistrict(int district) {
        this.district = district;
    }

    public double getAvgPM10() { return avgPM10;}

    public void setAvgPM10(double avgPM10) { this.avgPM10 = avgPM10; }

}
