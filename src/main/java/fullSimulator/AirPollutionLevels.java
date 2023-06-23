package fullSimulator;

import fullSimulator.simulator.Buffer;
import fullSimulator.simulator.Measurement;
import fullSimulator.simulator.PM10Simulator;

import java.util.ArrayList;

public class AirPollutionLevels {

    private ArrayList<Measurement> try_measurementArrayList = new ArrayList<>();
    private double try_averageOfAirPollution;
    public AirPollutionLevels(ArrayList<Measurement> m, double a) {
        this.try_measurementArrayList = m;
        this.try_averageOfAirPollution = a;
    }

    public synchronized double averageTot(){
        Buffer buff = new MyBuffer(try_measurementArrayList, try_averageOfAirPollution);
        PM10Simulator newSimulator = new PM10Simulator(buff);
        newSimulator.start();//IN HERE THE THREAD RUNS AND ADDS VALUES TO THE BUFFER

       return try_averageOfAirPollution;
    }

    public ArrayList<Measurement> getTry_measurementArrayList() {
        return try_measurementArrayList;
    }

    public void setTry_measurementArrayList(ArrayList<Measurement> try_measurementArrayList) {
        this.try_measurementArrayList = try_measurementArrayList;
    }

    public double getTry_averageOfAirPollution() {
        return try_averageOfAirPollution;
    }

    public void setTry_averageOfAirPollution(double try_averageOfAirPollution) {
        this.try_averageOfAirPollution = try_averageOfAirPollution;
    }
}
