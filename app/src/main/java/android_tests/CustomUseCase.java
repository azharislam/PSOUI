package android_tests;

import java.util.ArrayList;
import java.util.Arrays;

import ai69.psoui.MainActivity;
import ai69.psoui.ParticleActivity;
import bpso.BinaryPso;
import bpso.Particle;
import test_classes.CustomServiceSelection;
import test_classes.CustomService;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Azhar on 25/03/2017.
 */

public class CustomUseCase extends Test {

    public ArrayList<Double> costData = MainActivity.costDATA; //costs that the user enters for each resource
    public ArrayList<Double> costWlan = MainActivity.costWLAN;
    public ArrayList<Double> costUtilities = MainActivity.costUTILITY;
    double batteryCost;
    double userSolution;
    private int maxIter;
    private int noParticles;
    public final static int dimensions = CustomService.userServices.size();


    public CustomUseCase(int noParticles, int maxIterations){
        this.noParticles = noParticles; this.maxIter = maxIterations;
    }
    public void setBatteryCost(double batteryCost) {
        this.batteryCost = batteryCost;
    }

    @Override
    public double[] test(){
        long max = 10000; //maximum number of iterations, override
        int numOfBits = 3 + MainActivity.servicenames.size(); //3 bits for the results, the rest for the amount of services the user inputs
        double[] results = new double[numOfBits]; //new array of results with numOfBits as number of elements

        for (int i = 1; i <= max; i++) {
            BinaryPso bpso = new BinaryPso(noParticles,
                    dimensions);

           // CustomServiceSelection customServiceSelection = //custom service
                    //new CustomServiceSelection(batteryCost, costData, costWlan, costUtilities); //costBattery
            CustomService customService=
                    new CustomService(batteryCost, costData, costWlan, costUtilities);

            long start = System.currentTimeMillis(); //start time
            bpso.setSolution(userSolution); //changed this to user selection
           // bpso.optimize(maxIter, customServiceSelection, true);
            bpso.optimize(maxIter, customService, true);

            this.found += (bpso.getFound() ? 1 : 0);
            this.iterations += bpso.getSolIterations(); //use the method in bpso to get number of iterations taken
            long end = System.currentTimeMillis() - start; //end time minus start time

            this.sumTimes += end; //override the time spent variable

            System.out.println("Particle value: "      + Particle.getValue(Particle.bestGlobal()));
            System.out.println("Particle bit string: " + Arrays.toString(Particle.bestGlobal()));
            System.out.println("Particle goodness: "   + customService.getGoodness(Particle.bestGlobal()));
        }

        System.out.println("Time spend: " + sumTimes / max);
        System.out.println("Iterations: " + iterations / max);
        System.out.println("Success: " + found);

        boolean[] bestCombo = Particle.bestGlobal();

        for(Boolean b: bestCombo){
            System.out.print(b + " ");
        }
        System.out.println();

        results[0] = sumTimes/max;
        results[1] = iterations/max;
        results[2] = found;

        for(int i = 0; i < numOfBits; i++){
            results[i] = (bestCombo[i]) ? 1.0 : 0.0; //for every element in the user adds, check bit combination then return the resultscxv cv
        }
        return results;
    }
}

