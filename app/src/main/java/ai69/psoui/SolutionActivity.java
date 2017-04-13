package ai69.psoui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.HashMap;


public class SolutionActivity extends AppCompatActivity {

    private double time = 0.0;
    private double iter = 0.0;
    private double succ = 0.0;
    /**
     * Division
     */
    private double data = 0.0;
    private double WLAN = 0.0;
    private double userServices = 0.0;
    private HashMap<String, Double> ListOfServices = new HashMap<String, Double>();

    private int numberOfServices = MainActivity.servicenames.size();
    private ArrayList<String> theServices = MainActivity.servicenames;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution);
        //get intent from previous activity
        Intent intent = getIntent();
        double[] results = intent.getDoubleArrayExtra(ParticleActivity.EXTRA_MESSAGE);
        time = results[0];
        iter = results[1];
        succ = results[2];
//---------------------------------------------------------------------------------------
        data = results[3];
        WLAN = results[4];
//---------------------------------------------------------------------------------------

        for (int i = 0; i <= numberOfServices; i++) {
            ListOfServices.put(theServices.get(i), 0.0);
            //get each element the user enters and assign it a value of 0.0
        }
        for (int r = 5; r <= numberOfServices; r++) { //for each value in the result[] array
            for (int s = 0; s <= numberOfServices; s++) { //for each number of services
                userServices = ListOfServices.get(theServices.get(s)); //get the service from the hashmap
                userServices = results[r]; //add the service to the results array
            }
        }
//----------------------------------------------------------------------------------------
            setResults(succ, iter, time, data, WLAN, userServices);
            closeIntent(); //return button
        }

    public void setResults(double succ, double iterations, double tim,
                           double data, double WLAN, double userServices) {
        EditText time = (EditText) findViewById(R.id.timeSpent);
        EditText iter = (EditText) findViewById(R.id.iterations);
        EditText success = (EditText) findViewById(R.id.success);
        EditText best = (EditText) findViewById(R.id.best);

        time.setText(String.valueOf(tim));
        iter.setText(String.valueOf(iterations));
        success.setText(String.valueOf(succ));
        best.setText("4G[" + data + "] " + "WLAN[" + WLAN + "] " + "Your Services[" + userServices + "] ");

    }
//------------------------------------------------------------------------------------------
    public void closeIntent() { //RETURN BUTTON
        Button b = (Button) findViewById(R.id.button2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

            ;
        });
    }
}

