package test_classes;


import java.util.ArrayList;
import java.util.HashMap;
import ai69.psoui.MainActivity;
import ai69.psoui.ParticleActivity;
import android_tests.Test;
import bpso.Goodness;
import java.util.Collections;

/**
 * Created by Azhar on 25/03/2017.
 */

public class CustomServiceSelection implements Goodness {


    public static final int numOfServices = MainActivity.servicenames.size();
    public static final int NUM_DIMENSIONS = 2 + numOfServices;
    public static final int DATA = 0;
    public static final int WLAN = 1;
    public static final int UserServices1 = 3;
    HashMap<Integer, String> serviceMap = new HashMap<Integer, String>(); //hashmap to store values
    ArrayList<String> serviceNames = MainActivity.servicenames;
    ArrayList<Double> costData = MainActivity.costDATA;
    ArrayList<Double> costWlan = MainActivity.costWLAN;
    ArrayList<Double> costUtilities = MainActivity.costUTILITY;
    //public static double batteryCost = ParticleActivity.batteryLevel;
    double batteryCost;

    public void setBatteryCost(double batteryCost) {
        this.batteryCost = batteryCost;
    }

    public CustomServiceSelection(double batteryCost, ArrayList<Double> costData, ArrayList<Double> costWlan,
                                  ArrayList<Double> costUtilities) {
        if (costUtilities == null || costUtilities.size() < 1 || costData.size() < 1 || costWlan.size() < 1) {
            throw new RuntimeException("Please add atleast 1 cost to Data, WLAN and Utility");
        }
        this.batteryCost = batteryCost; //make sure you add battery field to UI, user enters battery level
        this.costData = costData;
        this.costWlan = costWlan;
        this.costUtilities = costUtilities;
    }


    public double getGoodness(boolean[] bits) {
        double utility = 0.0;
        double rcost = 0.0;
        ArrayList<Double> resourceCost = new ArrayList<Double>();

        Collections.sort(costUtilities);
        double maxValue = Collections.max(costUtilities);

        for (int i = 2; i <= NUM_DIMENSIONS; i++) { //hashmap that stores the service names with a bit value i
            for (int k = 0; k < numOfServices; k++) {
                serviceMap.put(i, serviceNames.get(k));
            }

            if (!bits[i] == costUtilities.contains(maxValue)) {
                //return -2000 if the highest utility service in the arraylist is not on
                return -2000;
            }

            if (bits[DATA] && bits[WLAN]) {
                return -500;
            }
            if (!bits[DATA] || bits[WLAN]) {
                return -1000; //particle goodness always returns this??
            }
            if (bits[DATA]) {
                resourceCost = costData;
            } else if (bits[WLAN]) {
                resourceCost = costWlan;
            }

            for (int n = 2; n <= numOfServices; n++) {
                if (bits[n]) {
                    utility += costUtilities.get(n - 1);
                    rcost += resourceCost.get(n - 1);
                }
            }
            if (rcost < batteryCost) {
                return utility;
            }

            return utility * 0.50;
        }
    return utility;
    }
}



