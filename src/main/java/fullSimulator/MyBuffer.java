package fullSimulator;

import cleaningRobots.beans.Robots;
import fullSimulator.simulator.Buffer;
import fullSimulator.simulator.Measurement;

import java.util.ArrayList;
import java.util.List;

import static cleaningRobots.RobotClient.*;

public class MyBuffer implements Buffer {

    private ArrayList<Measurement> arrayList;
    private double avg;

    //private ArrayList<Double> averageListToSend;

    public MyBuffer(ArrayList<Measurement> measurements, double res) {
        this.arrayList = measurements;
        this.avg = res;
        //this.averageListToSend = new ArrayList<>();
    }

    @Override
    public void addMeasurement(Measurement m) {

        if(arrayList.size()<8) {
            //System.out.println("HERE TO ADD THE MEASUREMENT TO THE BUFFER");
            arrayList.add(m);
            //System.out.println("Measurement: " + m.getId() + " " + m.getValue() + " => " + arrayList.indexOf(m));
        }else readAllAndClean();

    }

    @Override
    public List<Measurement> readAllAndClean() {

        ArrayList<Measurement> newArrayList = new ArrayList<>();
        for(Measurement m : arrayList){
            //System.out.println("Sum for avg: "+ m.getValue());
            avg = avg + m.getValue();

            /**if(arrayList.indexOf(m)>4)
                //arrayList.remove(m);
                System.out.println("HAS TO BE REMOVED");
             */
            //THERE IS A PROBLEM HERE CAUSE ConcurrentModificationException WAS THROWN
            // WHICH MEANS THAT YOU CANNOT REMOVE AN ELEMENT CAUSE IT'S STILL IN USAGE
            // THE SOLUTION IS TO CREATE ANOTHER ONE

            if(arrayList.indexOf(m)>3){
                newArrayList.add(m);
                //System.out.println("ADDED TO A NEW ARRAY: "+  m.getId() + " " +  m.getValue() + " => " + newArrayList.indexOf(m));
            }
        }
        avg = avg/8;
        //System.out.println("Printing average: " + avg);


        System.out.println("            MyBuffer: LET'S ADD AVERAGES TO EXISTING AVERAGES!!! ");

        Robots.getInstance().getRobotById(getNewR().getId()).getAvgPM10().add(avg);//getExistingRobots().getRobotById(getNewR()[0].getId()).getAvgPM10().add(avg);
        //averageListToSend = getExistingRobots().getRobotById(getNewR()[0].getId()).getAvgPM10();
        //System.out.println("     List of averages: " + averageListToSend);

        arrayList = newArrayList;
        return arrayList;
    }

}