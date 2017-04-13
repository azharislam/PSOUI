package bpso;

import java.util.Random;

public class Particle {

    private static final Random random = new Random();
    private static final double phiOne;
    private static final double phiTwo;
    private static boolean[] bestGlobal;
    private static final double VMAX = 4.0;

    static {
        phiOne = random.nextDouble() * VMAX;
        phiTwo = VMAX - phiOne;
    }

    private boolean[] position;
    private boolean[] bestPersonal;
    private double[] predisposition;
    private double[] pho;

    public Particle(int n) {
        position = new boolean[n];
        predisposition = new double[n];
        bestPersonal = new boolean[n];
        pho = new double[n];
        for (int i = 0; i < n; i++) {
            position[i] = random.nextBoolean();
            bestPersonal[i] = random.nextBoolean();
            predisposition[i] = 0.0;
            pho[i] = random.nextDouble();
        }
    }

    public static void initBestGlobal(int n) {
        bestGlobal = new boolean[n];
        for (int i = 0; i < n; i++) {
            bestGlobal[i] = random.nextBoolean();
        }
    }

    public void update() {
        for (int i = 0; i < position.length; i++) {
            double positionValue = position(i);
            predisposition[i] = predisposition[i] +
                    phiOne * (bestPersonal(i) - positionValue) +
                    phiTwo * (bestGlobal(i) - positionValue);
            if (predisposition[i] > VMAX) {
                predisposition[i] = VMAX;
            } else if (predisposition[i] < -VMAX) {
                predisposition[i] = -VMAX;
            }
            position[i] = (pho[i] < Sigmoid.get(predisposition[i]));
        }
    }

    private double getValue(int i, boolean[] bits) {
        return bits[i] ? 1.0 : 0.0;
    }

    private double bestPersonal(int i) {
        return getValue(i, bestPersonal);
    }

    private double bestGlobal(int i) {
        return getValue(i, Particle.bestGlobal);
    }

    private double position(int i) {
        return getValue(i, position);
    }

    public static double getValue(boolean[] bits) {
        int n = 0;
        for (int i = 0; i < bits.length; i++) {
            n = (n << 1) + (bits[i] ? 1 : 0);
        }
        return n;
    }

    public boolean[] position() {
        return position;
    }

    public boolean[] bestPersonal() {
        return bestPersonal;
    }

    public static boolean[] bestGlobal() {
        return Particle.bestGlobal;
    }

    public void setBestPersonal() {
        System.arraycopy(position, 0, bestPersonal, 0, position.length );
    }

    public static void setBestGlobal(Particle particle) {
        System.arraycopy(particle.position, 0, Particle.bestGlobal, 0, particle.position.length );
    }

}
