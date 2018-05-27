package com.example.dmayorca.weather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class HomeActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.dmayorca.weather.MESSAGE";
    private final static String KEY = "c5e38341e97fbe92c59e788f901144a7";
    private final static String DOMAIN ="https://api.openweathermap.org/data/2.5/forecast";
    private final static String IMGDOMAIN = "https://openweathermap.org/img/w/";
    private String localPath ="";
    JSONObject root = null;

    private EditText txtSearch;
    private Button btnSearch;
    private Spinner spinner;


    ArrayAdapter<String> adapterSearch;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        GlobalVars globalVariable  = (GlobalVars) getApplicationContext();



        Thread t = new Thread() {
            public void run() {
                try {

                    while (!isInterrupted()) {

                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView tdate = findViewById(R.id.tdate);
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("EE MM \nhh:mm:ss a\n zzzz");
                                String DateString = sdf.format(date);
                                tdate.setText(DateString.toUpperCase());

                            }
                        });
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        };
        t.start();

        adapterSearch = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, globalVariable.listSearch);
        txtSearch = findViewById(R.id.txtSearch);
        btnSearch = findViewById(R.id.btnSearch);
        spinner = findViewById(R.id.spinner);

        spinner.setAdapter(adapterSearch);
        adapterSearch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View v) {

                final String query = txtSearch.getText().toString();
                Intent intent = new Intent(v.getContext(), forecastActivity.class);
                intent.putExtra(EXTRA_MESSAGE, query);
                startActivityForResult(intent, 0);
                adapterSearch.notifyDataSetChanged();
            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                txtSearch.setText(spinner.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }}


/*
    public String readJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open(localPath+"yourFile.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Cambio_Menu(item);
        return super.onOptionsItemSelected(item);
    }*/

    /*private void Cambio_Menu(MenuItem item){
        switch (item.getItemId()){
            case R.id.ithome:
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                break;

            case R.id.itWindow1:
                intent = new Intent(this, activity_main_forecast.class);
                startActivity(intent);
                break;
            case R.id.itWindow2:
                intent = new Intent(this, statisticalActivity.class);
                startActivity(intent);
                break;

            case R.id.itWindow3:
                intent = new Intent(this, angleActivity.class);
                startActivity(intent);
                break;

            case R.id.itWindow4:
                intent = new Intent(this, loginActivity.class);
                startActivity(intent);
                break;

            case R.id.itWindow5:
                intent = new Intent(this, infoActivity.class);
                startActivity(intent);
                break;

            case R.id.itWindow6:
                intent = new Intent(this, googleMapsActivity.class);
                startActivity(intent);
                break;

            case R.id.itWindow7:
                intent = new Intent(this, autorsActivity.class);
                startActivity(intent);
                break;


        }
    }

}*/