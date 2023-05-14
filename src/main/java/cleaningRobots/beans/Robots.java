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

    public Robots() {
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
        if(checkId(this.robotsList,r)&&(checkPort(this.robotsList,r))){
            System.out.println("[from postRobot] Added the robot in the list");
            robotsList.add(r);
        }else {
            System.out.println("[from postRobot] NOT added");
        }

    }

    //public synchronized void deleteRobot(Robot w){
        //robotsList.remove(w);
    //}

    public String getAPLevelsRobot(String t){
        List<Robot> robotsCopy = getRobotsList();
        for(Robot w: robotsCopy)
            if(w.getId().equalsIgnoreCase(t))
                return w.getTestToGetAirPollution();
        return null;
    }

    public synchronized boolean checkId(List<Robot> list, Robot newRobot){
        for(Robot rob : list){
            if(rob.getId().equals(newRobot.getId()))
            {
                System.out.println("    [from checkId method] ID already used");
                return false;
            }
        }
        System.out.println("    [from checkId method] ID available");
        return true;
    }

    public synchronized boolean checkPort(List<Robot> list, Robot newRobot){
        for(Robot rob: list){
            if (rob.getPort() == newRobot.getPort()){
                System.out.println("    [from checkPort method] PORT already used");
                return false;
            }
        }
        System.out.println("    [from checkPort method] Correct port");
        return true;
    }

}
