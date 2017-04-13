package pso.algo;


import java.util.ArrayList;

/**
 * Created by Dazsta on 25/11/2015.
 */
public class ResultSet {

    String utilName     = "";
    Double cost         = 0.00;
    boolean priority    = false;

    ArrayList<String> depend = new ArrayList<>();

    public void addDepnd(String dep){ depend.add(dep); }

    public ArrayList<String> getDepnd(){ return depend; }

    public void setCost(Double cost) { this.cost = cost; }

    public void setUtilName(String utilName) { this.utilName = utilName; }

    public void setPriority(boolean priority) { this.priority = priority; }

    public Double getCost() { return cost; }

    public String getUtilName() { return utilName; }

    public boolean getPriority(){ return this.priority; }

}
