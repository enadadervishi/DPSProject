package fullSimulator;

import java.util.ArrayList;
import java.util.HashMap;

public class AveragesIn15Secs {

    private ArrayList<Double> averagesIn15Secs;




    private String timestamp;

    private HashMap<Double, String> hashMapAverageTRY;


    public ArrayList<Double> getAveragesIn15Secs() {
        return averagesIn15Secs;
    }

    public void setAveragesIn15Secs(ArrayList<Double> averagesIn15Secs) {
        this.averagesIn15Secs = averagesIn15Secs;
    }


    public AveragesIn15Secs() {
        this.hashMapAverageTRY = new HashMap<>();
        //this.timestamp = new String();
    }


    public HashMap<Double, String> getHashMapAverageTRY() {
        return hashMapAverageTRY;
    }
    public void setHashMapAverageTRY(HashMap<Double, String> hashMapAverageTRY) {
        this.hashMapAverageTRY = hashMapAverageTRY;
    }


}
