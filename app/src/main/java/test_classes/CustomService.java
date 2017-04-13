package test_classes;


import java.util.ArrayList;
import java.util.HashMap;
import ai69.psoui.MainActivity;
import ai69.psoui.ParticleActivity;
import android_tests.Test;
import bpso.Goodness;
import bpso.Particle;

import java.util.Collections;
import java.util.Map;

/**
 * Created by Azhar on 25/03/2017.
 */

public class CustomService implements Goodness {


    public static int numOfServices = MainActivity.servicenames.size();
    public static final int NUM_DIMENSIONS = 1 + numOfServices;
    public static final int DATA = 0;
    public static final int WLAN = 1;
    public static int UserService;
    public static HashMap<String, Integer> serviceMap = new HashMap<String, Integer>(); //hashmap to store values
    public static Map<String, Boolean> userServices = new HashMap<String, Boolean>();
    ArrayList<String> serviceNames = MainActivity.servicenames;
    ArrayList<Double> costData = MainActivity.costDATA;
    ArrayList<Double> costWlan = MainActivity.costWLAN;
    ArrayList<Double> costUtilities = MainActivity.costUTILITY;
    double batteryCost;

    public void setBatteryCost(double batteryCost) {
        this.batteryCost = batteryCost;
    }

    public CustomService(double batteryCost, ArrayList<Double> costData, ArrayList<Double> costWlan,
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
        int NumOfDimensions = serviceMap.size();
        boolean[] bestCombo = Particle.bestGlobal();

        if (bits[DATA] && bits[WLAN]) {
            return -500;
        }
        if (!bits[WLAN] || bits[WLAN]) {
            return -1000; //particle goodness always returns this??
        }
        if (bits[DATA]) {
            resourceCost = costData;
        } else if (bits[WLAN]) {
            resourceCost = costWlan;
        }

        for (int i = 0; i <= NumOfDimensions; i++) {
            String usersService = serviceNames.get(i);
            for (int k = 1; k < NUM_DIMENSIONS; k++) {

                serviceMap.put(usersService, k);

                if (bits[k] == costUtilities.contains(maxValue)) {
                    return -2000;
                }
            }
        }

        for (int n = serviceMap.get(0); n <= serviceMap.size(); n++) {
            if (bits[n]) {
                utility += costUtilities.get(n - serviceMap.get(0));
                rcost += resourceCost.get(n - serviceMap.get(0));
            }
        }

        if (rcost < batteryCost) {
            return utility;
        }

        return utility * 0.50;
    }
}

//show how to upload it onto github



