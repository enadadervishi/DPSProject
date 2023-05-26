package mySimulator;

import mySimulator.simulator.Buffer;
import mySimulator.simulator.Measurement;

import java.util.ArrayList;
import java.util.List;

public class MyBuffer implements Buffer {


    private final int bufferSize = 8;

    private int bufferIndex;

    public MyBuffer() {
        this.bufferIndex = 0;
    }


    @Override
    public void addMeasurement(Measurement m) {
        ArrayList<Measurement> listOfMeasurements = new ArrayList<>();
        listOfMeasurements.add(m);

        System.out.println("MEASUREMENT: "+ m.getId() + m.getValue() + this.bufferIndex);
        this.bufferIndex ++;

    }

    @Override
    public List<Measurement> readAllAndClean() {
        return null;
    }

    public int getBufferIndex() {
        return bufferIndex;
    }
}
