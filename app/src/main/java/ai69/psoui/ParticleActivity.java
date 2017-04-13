package ai69.psoui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import android_tests.CustomUseCase;
import android_tests.Test;
import android_tests.TestFactory;
import bpso.BinaryPso;
import bpso.Goodness;
import test_classes.CustomService;
import test_classes.CustomServiceSelection;

public class ParticleActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "PSOUI.MESSAGE";
    private ProgressDialog pd;
    private double[] results = {-1.0, -1.0, -1.0};
    EditText particles;
    EditText iterations;
    EditText solution;
    EditText battery;
    public ArrayList<Double> costData = MainActivity.costDATA; //costs that the user enters for each resource
    public ArrayList<Double> costWlan = MainActivity.costWLAN;
    public ArrayList<Double> costUtilities = MainActivity.costUTILITY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particle);
        particles = (EditText) findViewById(R.id.particles);
        iterations = (EditText) findViewById(R.id.iterations);
        solution = (EditText) findViewById(R.id.solution);
        battery = (EditText) findViewById(R.id.battery);
        pd = null;
        runPSOButton();
    }

    @Override
    public void onPause(){

        super.onPause();
        if(pd != null)
            pd.dismiss();
    }

    public class runTests extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) { //sort this out
            results = runTest("CustomUseCase"); //i only want to run this one!!!
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {

            if (results != null && results.length > 0 && results[0] != -1) {
                loadIntent(results);
            } //otherwise it will evaluate the next logic statement results[0] != -1 with no chance of NulLPointerException
        }

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(ParticleActivity.this, "Busy", "Algorithm is currently executing");
            pd.setCancelable(true);
            pd.show();
        }
    }

    public void runPSOButton() {
        final Button runPSO = (Button) findViewById(R.id.runpso);

        runPSO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                new runTests().execute();
            }
        });
    }

    public double[] runTest(String test) {

        int noPart = Integer.parseInt(particles.getText().toString());
        int noIter = Integer.parseInt(iterations.getText().toString());
        double userSolution = Double.parseDouble(solution.getText().toString());
        double batteryLevel = Double.parseDouble(battery.getText().toString());

            TestFactory t = new TestFactory(noPart, noIter);
            BinaryPso bpso = new BinaryPso(noPart,
                    CustomService.NUM_DIMENSIONS);
            Test usersCustomTest = t.getTest(test);
            bpso.setSolution(userSolution);
        //CustomServiceSelection csc = new CustomServiceSelection(batteryLevel,costData, costWlan, costUtilities);
        CustomService csc = new CustomService(batteryLevel, costData, costWlan, costUtilities);
            csc.setBatteryCost(batteryLevel);
            usersCustomTest.setBatteryCost(batteryLevel);

        return new TestFactory(noPart, noIter).getTest(test).test();
    }

    public void loadIntent(double[] result) {
        double[] results = result;
        Intent intent = new Intent(this, SolutionActivity.class);
        intent.putExtra(EXTRA_MESSAGE, results);
        startActivity(intent);
    }

}

