package bpso;

/**
 * Created by Dazsta on 25/10/2015.
 */


public class BinaryPso implements BinaryPsoInt {

    private Particle[] particles;
    private int numParticles;
    private int numDimensions;
    private double solution = Double.MAX_VALUE;
    private int solIterations;
    private boolean found;

    /**
     * dimensions number applications/services
     * Maybe read how many are on the device....
     * Add a new test see how it does
     */
    public BinaryPso(int numParticles, int numDimensions) {
        this.numParticles = numParticles;
        this.numDimensions = numDimensions;
        initParticles();
    }

    public void initParticles() {
        particles = new Particle[numParticles];
        for (int i = 0; i < numParticles; i++) {
            particles[i] = new Particle(numDimensions);
        }
        Particle.initBestGlobal(numDimensions);
    }

    @Override
    public void optimize(int iterations, Goodness goodness, boolean maximize) {
        double minimizeSign = maximize ? -1. : 1.;
        solIterations = iterations;
        found = false;
        for (int counter = 0; counter < iterations; counter++) {
            for (int i = 0; i < particles.length; i++) {
                double particleGoodness = goodness.getGoodness(particles[i].position());

                if (minimizeSign * particleGoodness < minimizeSign * goodness.getGoodness(particles[i].bestPersonal())) {
                    particles[i].setBestPersonal();
                }
                if (minimizeSign * particleGoodness < minimizeSign * goodness.getGoodness(Particle.bestGlobal())) {
                    Particle.setBestGlobal(particles[i]);
                    if ( Math.abs(particleGoodness - solution) < 0.001 ) {
                        solIterations = i + 1;
                        found = true;
                        return;
                    }
                }
                particles[i].update();
            }
        }
    }

    @Override
    public void setSolution(double solution) {
        this.solution = solution;
    }

    @Override
    public int getSolIterations() {
        return solIterations;
    }

    @Override
    public boolean getFound() {
        return found;
    }
}
