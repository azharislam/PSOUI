package android_tests;

public class TestFactory { //the user interface calls this class when implementing a test

    private int iterations;
    private int noParticles;

    public TestFactory(int iterations, int noParticles) {
        this.iterations = iterations;
        this.noParticles = noParticles;
    }

    public Test getTest(String test) {

        return new CustomUseCase(iterations, noParticles);
    }

}


