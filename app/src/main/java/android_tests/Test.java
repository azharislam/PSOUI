package android_tests;

import java.util.ArrayList;

import pso.algo.ResultSet;

//superclass
public class Test implements Testing {

    public double sumTimes   = 0;
    public double iterations = 0;
    public double found      = 0;
    public long max          = 10000;
    int noParticles          = 0;
    int maxIter              = 0;
    double batteryCost       = 0;

    @Override
    public double[] test() {
        return null;
    }

    @Override
    public void setServices(ArrayList<ResultSet> s) {}

    @Override
    public void setSol(double sol) {}

    @Override
    public void setBatteryCost(double batteryCost) {
        this.batteryCost = batteryCost;
    }

}