package cleaningRobots.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * This class wants to represent the list of robots
 * correctly connected to the AdministratorServer
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Robots {

    @XmlElement(name="connected_cleaning_robots")
    private List<Robot> robotsList;

    private static Robots instance;

    private Robots() {
        robotsList = new ArrayList<>();
    }

    //singleton
    public synchronized static Robots getInstance(){
        if(instance==null)
            instance = new Robots();
        return instance;
    }

    public synchronized List<Robot> getRobotsList() {
        return new ArrayList<>(robotsList);
    }
    public synchronized void setRobotsList(List<Robot> rList) {
        this.robotsList = rList;
    }

    public synchronized void postRobot(Robot r){
        System.out.println("Added the robot in the list");
        robotsList.add(r);
    }
    //public synchronized void deleteRobot(Robot w){
        //robotsList.remove(w);
    //}

    public String getAPLevelsRobot(String t){
        List<Robot> robotsCopy = getRobotsList();
        for(Robot w: robotsCopy)
            if(w.getID().equalsIgnoreCase(t))
                return w.getTestToGetAirPollution();
        return null;
    }
}
