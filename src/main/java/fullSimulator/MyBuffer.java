package fullSimulator;

import fullSimulator.simulator.Buffer;
import fullSimulator.simulator.Measurement;

import java.util.ArrayList;
import java.util.List;


public class MyBuffer implements Buffer {

    private ArrayList<Measurement> arrayList;
    private double avg;

    public MyBuffer(ArrayList<Measurement> measurements, double res) {
        this.arrayList = measurements;
        this.avg = res;
    }


    @Override
    public void addMeasurement(Measurement m) {

        if(arrayList.size()<8) {
            System.out.println("HERE TO ADD THE MEASUREMENT TO THE BUFFER");

            arrayList.add(m);
            System.out.println("Measurement: " + m.getId() + " " + m.getValue() + " => " + arrayList.indexOf(m));

        }else readAllAndClean();


        /**
         for(int i=0; i<8; i++)
         measurementArrayList.add(m);
         for(Measurement n: measurementArrayList)
         System.out.println("Measurement: "+ n.getId() + " "+ n.getValue());
         */


        /**
         //while(measurementArrayList.size()<8) {
         measurementArrayList.add(m);
         System.out.println("MEASUREMENT: " + m.getId() + m.getValue());
         //}
         */



    }

    @Override
    public List<Measurement> readAllAndClean() {

        ArrayList<Measurement> newArrayList = new ArrayList<>();
        for(Measurement m : arrayList){
            System.out.println("Sum for avg: "+ m.getValue());
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
                System.out.println("ADDED TO A NEW ARRAY: "+  m.getId() + " " +  m.getValue() + " => " + newArrayList.indexOf(m));
            }
        }
        avg = avg/8;
        System.out.println("Printing average: " + avg);

                /*
                double sum=0;
                for(Measurement m: measurementArrayList){
                    sum = m.getValue();
                    if(measurementArrayList.indexOf(m)<4)
                        measurementArrayList.remove(m);
                }
                average[0] = sum/measurementArrayList.size();

                */
        //return measurementArrayList;

        arrayList = newArrayList;
        return arrayList;
    }

}