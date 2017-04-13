package bpso;

/**
 * Created by Dazsta on 05/11/2015.
 */
public interface BinaryPsoInt {

    void optimize(int iterations, Goodness goodness, boolean maximize);

    void setSolution(double solution);

    int getSolIterations();

    boolean getFound();
}
