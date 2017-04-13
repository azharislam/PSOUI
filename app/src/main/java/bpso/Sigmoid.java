package bpso;

public class Sigmoid {

    public static double get(double x) {
        return 1.0 / ( 1.0 + Math.pow(Math.E, -x));
    }
}

