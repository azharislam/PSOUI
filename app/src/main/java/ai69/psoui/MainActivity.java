package ai69.psoui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.content.Intent;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    //declare variables
    EditText name;
    EditText data;
    EditText wlan;
    EditText utility;
    Button addservice;
    ListView lv;
    ListView lv2;
    ListView lv3;
    ListView lv4;
    public static ArrayList<String> servicenames;
    public static ArrayList<Double> costDATA;
    public static ArrayList<Double> costWLAN;
    public static ArrayList<Double> costUTILITY;
    ArrayAdapter<String> namesAdapter;
    ArrayAdapter<Double> dataAdapter;
    ArrayAdapter<Double> wlanAdapter;
    ArrayAdapter<Double> utilityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //map the components to the variables
        name = (EditText) findViewById(R.id.servicename);
        data = (EditText) findViewById(R.id.data);
        wlan = (EditText) findViewById(R.id.wlan);
        utility = (EditText) findViewById(R.id.utility);
        addservice = (Button) findViewById(R.id.addservice);
        lv = (ListView) findViewById(R.id.lv);
        lv2 = (ListView) findViewById(R.id.lv2);
        lv3 = (ListView) findViewById(R.id.lv3);
        lv4 = (ListView) findViewById(R.id.lv4);

        //create arraylists for each component
        servicenames = new ArrayList<String>();
        costDATA = new ArrayList<Double>();
        costWLAN = new ArrayList<Double>();
        costUTILITY = new ArrayList<Double>();

        //create adapters to pass on the arraylist
        namesAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, servicenames);
        dataAdapter = new ArrayAdapter<Double>(MainActivity.this, android.R.layout.simple_list_item_1, costDATA);
        wlanAdapter = new ArrayAdapter<Double>(MainActivity.this, android.R.layout.simple_list_item_1, costWLAN);
        utilityAdapter = new ArrayAdapter<Double>(MainActivity.this, android.R.layout.simple_list_item_1, costUTILITY);

        //display each arraylist in the listviews
        lv.setAdapter(namesAdapter);
        lv2.setAdapter(wlanAdapter);
        lv3.setAdapter(dataAdapter);
        lv4.setAdapter(utilityAdapter);
        namesAdapter.notifyDataSetChanged();
        dataAdapter.notifyDataSetChanged();
        wlanAdapter.notifyDataSetChanged();
        utilityAdapter.notifyDataSetChanged();
        onClickBtn();
    }

    public void onClickBtn() { //when user clicks button, the user input is added to the listview, and cleared for the next service

        addservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namesOfService = name.getText().toString(); //user input for service names
                String costOfData = data.getText().toString(); //user input for data costs
                String costOfWLAN = wlan.getText().toString(); //user input for wlan costs
                String costOfUtility = utility.getText().toString(); //user input for utility costs
                Double doubleWLAN = Double.parseDouble(costOfWLAN); //convert user input into double
                Double doubleData = Double.parseDouble(costOfData);
                Double doubleUtility = Double.parseDouble(costOfUtility);
                costDATA.add(doubleData); //add the double costs to each resource arraylist
                costWLAN.add(doubleWLAN);
                costUTILITY.add(doubleUtility);
                servicenames.add(namesOfService);


                namesAdapter.notifyDataSetChanged();
                dataAdapter.notifyDataSetChanged();
                wlanAdapter.notifyDataSetChanged();
                utilityAdapter.notifyDataSetChanged();
                name.setText(""); //empty the edit text fields when button is clicked
                wlan.setText("");
                data.setText("");
                utility.setText("");

            }
        });
    }

    public void nextButton(View view) //next button, onto the next activity
    {
        Intent intent = new Intent(MainActivity.this, ParticleActivity.class);
        startActivity(intent);
    }
}
