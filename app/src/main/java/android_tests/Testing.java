package android_tests;

import java.util.ArrayList;

import pso.algo.ResultSet;

public interface Testing {

    public double[] test(); //same as optimize in the original implementation

    public void setServices(ArrayList<ResultSet> s);

    public void setSol(double sol);

    public void setBatteryCost(double batteryCost);

}
