import java.util.Arrays;
import java.util.Random;

public class Main {

    private static final Random rand = new Random();

    public static int[] getDistrict() {
        return district;
    }

    private static final int[] district = new int[4];


    public static void main(String[] args) {
        System.out.println("Hello Eni!");


        int[] arr = new int[20];

        for (int j=0; j<arr.length; j++) {
            arr[j] = j + 101;
        }

        for (int i : arr) {
            int numberOfDistrict = whereToGo();
            System.out.println(i + " => " + numberOfDistrict + " : " + Arrays.toString(assignPosition(numberOfDistrict)));
        }



    }

    public static int[] assignPosition(int district){

        int ver, hor;
        ver = rand.nextInt(4);
        hor = rand.nextInt(4);
        if(district==1){
            hor = hor +5;
            getDistrict()[1]++;
        }else if(district == 2){
            hor = hor +5;
            ver = ver +5;
            getDistrict()[2]++;
        }else if(district ==3){
            getDistrict()[3]++;
            ver = ver +5;
        }else
            getDistrict()[0]++;

        int [] p = new int[2];
        p[0]= ver;
        p[1]= hor;

        return p;
    }

    public static int whereToGo(){

        int howManyRobotsInDistrict = getDistrict()[0]; //controllo il numero di robot per ogni distretto
        for(int i=0; i<getDistrict().length; i++){

            if(howManyRobotsInDistrict >= getDistrict()[i]){
                howManyRobotsInDistrict = getDistrict()[i]; //qui ci sarà il numero minore di robot
            }
        }
        System.out.println(howManyRobotsInDistrict);
        //il minimo
        int val=0;
        for(int i=0; i<getDistrict().length; i++){

            if(getDistrict()[i] == howManyRobotsInDistrict)
                val = i;
        }


        return val;



/*

        for(int i = 0; i< getDistrict().length; i++){
            System.out.println("whereLessRobots: " +whereLessRobots +
                    " whichDistrict: "+ whichDistrict +
                    " getDistrict[" +i+"] "+ getDistrict()[i] );
            if(whereLessRobots >= getDistrict()[i]){
                System.out.println("    whereLess is >= getDistrict()[i]");
                whereLessRobots = getDistrict()[i];
                System.out.println("        whereLessRobots: "+whereLessRobots);
                whichDistrict = i;
                System.out.println("        whichDistrict: "+ whichDistrict);
            }else {
                System.out.println("    whereLess is < getDistrict()[i]");
                System.out.println("        whereLessRobots: "+whereLessRobots);
                System.out.println("        whichDistrict: "+ whichDistrict);
            }
        }
        System.out.println("\n");
        return whichDistrict;

 */
    }



}
