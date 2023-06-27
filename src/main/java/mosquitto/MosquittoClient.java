package mosquitto;

import mosquitto.fullSimulator.MyBuffer;
import mosquitto.fullSimulator.simulator.Buffer;
import mosquitto.fullSimulator.simulator.Measurement;
import mosquitto.fullSimulator.simulator.PM10Simulator;
import org.eclipse.paho.client.mqttv3.MqttException;
import restAPI.RestClient;
import restAPI.cleaningRobots.beans.Robots;

import java.util.ArrayList;

public class MosquittoClient extends Thread {

    private static final ArrayList<Measurement> measurements = new ArrayList<>();
    private static double avgAP = 0;

    private static RobotPub robotPublisher;

    public static ArrayList<Double> getAvgToSend() {
        return avgToSend;
    }

    private static ArrayList<Double> avgToSend = new ArrayList<>();


    public MosquittoClient(){}

    public void startingBuffer(){

        Buffer buff = new MyBuffer(measurements, avgAP); //measurementArrayList
        PM10Simulator simulator = new PM10Simulator(buff);
        simulator.start(); //IN HERE THE THREAD RUNS AND ADDS VALUES TO THE BUFFER

    }


    public void run(){

        while (true) {
            if (Robots.getInstance() != null) { //existingRobots
                try {
                    robotPublisher = new RobotPub(Robots.getInstance().getRobotById(RestClient.getNewR().getId()).getDistrict()); //existingRobots.getRobotById(newR[0].getId()).getDistrict()
                } catch (MqttException e) {
                    throw new RuntimeException(e);
                }
                try {
                    robotPublisher.publishing();
                } catch (MqttException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("    LIST OF ALL AVERAGES BY ONE ROBOT!!!!!!!!!!" + Robots.getInstance().getAPLevelsRobot(RestClient.getNewR().getId()));//getExistingRobots().getRobotById(getNewR()[0].getId()).getAvgPM10()

                avgToSend = Robots.getInstance().getRobotById(RestClient.getNewR().getId()).getAvgPM10(); //getExistingRobots().getRobotById(getNewR()[0].getId()).getAvgPM10();
                avgToSend.clear();
            }
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }



    }

}

//NON ANDAVA bene perche non mi faceva partire in contemporanea sia mosquitto sia grpc!!!!
/**
 public void startPublishing() throws MqttException, InterruptedException {

 while (true) {
 if (Robots.getInstance() != null) { //existingRobots
 robotPublisher = new RobotPub(Robots.getInstance().getRobotById(RestClient.getNewR().getId()).getDistrict()); //existingRobots.getRobotById(newR[0].getId()).getDistrict()
 robotPublisher.publishing();

 System.out.println("    LIST OF ALL AVERAGES BY ONE ROBOT!!!!!!!!!!" + Robots.getInstance().getAPLevelsRobot(RestClient.getNewR().getId()));//getExistingRobots().getRobotById(getNewR()[0].getId()).getAvgPM10()

 avgToSend = Robots.getInstance().getRobotById(RestClient.getNewR().getId()).getAvgPM10(); //getExistingRobots().getRobotById(getNewR()[0].getId()).getAvgPM10();
 avgToSend.clear();
 }
 Thread.sleep(15000);
 }
 }
 */