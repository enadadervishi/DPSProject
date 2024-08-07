package restAPI.cleaningRobots.beans;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

/**
 * This class wants to represent the list of robots
 * correctly connected to the AdministratorServer.
 * Practically those methods are directly for the Server
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Robots {

    @XmlElement(name="connected_cleaning_robots")
    private ArrayList<Robot> robotsList;

    public int[] getCounterForDistrict() {
        return counterForDistrict;
    }
    public void setCounterForDistrict(int[] counterForDistrict) {
        this.counterForDistrict = counterForDistrict;
    }

    private int[] counterForDistrict = new int[4];

    private static Robots instance;

    public Robots() {
        this.robotsList = new ArrayList<>();
    }

    //singleton
    public synchronized static Robots getInstance(){
        if(instance==null)
            instance = new Robots();
        return instance;
    }

    public synchronized ArrayList<Robot> getRobotsList() {
        return new ArrayList<>(robotsList);
    }

    public synchronized void setRobotsList(ArrayList<Robot> rList) {
        this.robotsList = rList;
    }

    public synchronized void postRobot(Robot r){
        if(checkId(this.robotsList,r)){ /**&&(checkPort(this.robotList,r))*/
            System.out.println("[from postRobot] "+r.getId() +" has been added");
            this.robotsList.add(r);

            int d = whereToGo();
            r.setDistrict(d);
            r.setCoordinates(assigningPosition(r.getDistrict()));


            System.out.println("POSITION: "+ Arrays.toString(r.getCoordinates()));
            System.out.println("DISTRICT: "+ r.getDistrict());


        }else {
            System.out.println("[from postRobot] NOT added");
        }

    }

    public int[] assigningPosition(int district){
        Random rand = new Random();
        int ver, hor;

        ver = rand.nextInt(4);
        hor = rand.nextInt(4);
        if(district==1){
            hor = hor +5;
            getCounterForDistrict()[1]++;
        }else if(district == 2){
            hor = hor +5;
            ver = ver +5;
            getCounterForDistrict()[2]++;
        }else if(district ==3){
            getCounterForDistrict()[3]++;
            ver = ver +5;
        }else
            getCounterForDistrict()[0]++;

        int [] p = new int[2];
        p[0]= ver;
        p[1]= hor;

        return p;
    }

    public int whereToGo(){

        int min = getCounterForDistrict()[0]; //controllo il numero di robot per ogni distretto
        for(int i=0; i<getCounterForDistrict().length; i++){

            if(min >= getCounterForDistrict()[i]){
                min = getCounterForDistrict()[i]; //qui ci sarà il numero minore di robot
            }
        }

        //il minimo
        int val=0;
        for(int i=0; i<getCounterForDistrict().length; i++){

            if(getCounterForDistrict()[i] == min)
                val = i;
        }

        return val;
    }

    public synchronized Robot getRobotById(String name){

        for(Robot r: this.robotsList){
            if(r.getId().equals(name))
                return r;
        }
        return null; //tecnicamente impossibile perche gli altri check li faccio prima
    }

    public synchronized void deleteRobot(Robot r){
        for(Robot w: getRobotsList()){
            if(w.getId().equals(r.getId())){
                this.robotsList.remove(w);
                this.counterForDistrict[w.getDistrict()]--;
                System.out.println("[from deleteRobot] "+ w.getId()+" has been removed");
            }
        }
        //System.out.println("CHECK IF REMOVED: "+ robotsList);
    }

    /**
    public String getAPLevelsRobot(String r){ //ArrayList<Double>
        List<Robot> robotsCopy = getRobotsList();
        for(Robot w: robotsCopy)
            if(w.getId().equalsIgnoreCase(r))
                return String.valueOf(w.getAvgPM10());
        return null;
    }*/

    /**
    public synchronized ArrayList<Double> getListAPLevelsRobot(String r){
        ArrayList<Robot> robotsCopy = getRobotsList();
        for(Robot w: robotsCopy)
            if(w.getId().equalsIgnoreCase(r))
                return w.getAvgPM10();
        return null;
    }
     */



    public synchronized boolean checkId(ArrayList<Robot> list, Robot newRobot){
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


    public synchronized void printAllRobots_Robots() {
        for(Robot l: this.robotsList){
            System.out.println("ID: "+l.getId()+" Port: "+l.getPort()
                    +" Server address: "+ l.getServerAddress()
                    +" Coordinates: "+ Arrays.toString(l.getCoordinates())
                    +" District: "+ l.getDistrict());
        }
    }

    /** SEE ABOVE ITS USAGE SEARCH JAVADOC IN THIS CLASS
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
     */

}
