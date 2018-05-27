package com.example.dmayorca.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class forecastActivity extends AppCompatActivity {

    private ExecutorService queue = Executors.newSingleThreadExecutor();
    private final static String KEY = "c5e38341e97fbe92c59e788f901144a7";
    private final static String DOMAIN = "https://api.openweathermap.org/data/2.5/forecast";
    private final static String IMGDOMAIN = "https://openweathermap.org/img/w/";
    private String localPath = "";
    //1JSONObject root_current = null;
    //1JSONArray history=null;

    //Instancias de clases
    private TextView lblCurrent;
    private TextView lblMin;
    private TextView lblMax;
    private TextView lblWeather;
    private TextView lblSpeed;
    private TextView lblDeg;
    private Spinner spinner;
    private Spinner horario;
    private Button btnCompass;
    private Button btnStatistical;
    private Button btnMaps;

    // Variables
    private double lon = 0;
    private double lat = 0;
    private int index_city = 0;
    private boolean find_city = false;
    private int index_horario;
    private float speed = 0;
    private float deg = 0;
    private float temp = 0;
    private float tempMin = 0;
    private float tempMax = 0;

    //List<String> listSearch = new ArrayList<String>();
    //List<String> listTime = new ArrayList<String>();

    DecimalFormat formateador = new DecimalFormat("#.##");
    ArrayAdapter<String> adapterSearch;
    ArrayAdapter<String> adapterTime;
    GlobalVars globalVariable;

    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_forecast);

       globalVariable  = (GlobalVars) getApplicationContext();


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

        localPath = getApplicationContext().getFilesDir().getAbsolutePath();
        adapterSearch = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, globalVariable.listSearch);
        adapterTime = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, globalVariable.listTime);

        // get values id
        lblCurrent = findViewById(R.id.lblCurrent);
        lblMin = findViewById(R.id.lblMin);
        lblMax = findViewById(R.id.lblMax);
        lblWeather = findViewById(R.id.lblWeather);
        lblSpeed = findViewById(R.id.lblSpeed);
        lblDeg = findViewById(R.id.lblDeg);
        spinner = findViewById(R.id.spinner);
        horario = findViewById(R.id.horario);
        btnCompass = findViewById(R.id.btnCompass);
        btnStatistical = findViewById(R.id.btnStatistical);
        btnMaps = findViewById(R.id.btnMaps);

        horario.setAdapter(adapterTime);
        adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String query = intent.getStringExtra(HomeActivity.EXTRA_MESSAGE);
        //TextView viewCity = findViewById(R.id.viewCity);

        //viewCity.setText(query);
        search(query,false);
        adapterTime.notifyDataSetChanged();

        horario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                index_horario = horario.getSelectedItemPosition();
                search("", true);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });


        btnCompass.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), angleActivity.class);
                startActivityForResult(intent, 0);

            }});

        btnStatistical.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), statisticalActivity.class);
                startActivityForResult(intent, 0);

            }
        });

        btnMaps.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), MapsActivity.class);
                startActivityForResult(intent, 0);

            }});


    }



    public void search(String queryTemp, boolean typeFind) {


        final String query=queryTemp;
        final boolean _typeFind = typeFind;

        Runnable thread = new Runnable() {
            @Override
            public void run() {

                if(_typeFind == false)
                {
                    String strUrl = DOMAIN + "?q=" + query + "&appid=" + KEY + "&units=metric&lang=es";
                    globalVariable.validateHistory(query);

                    //1int index_json = getIndex();
                    //1boolean find_json = getStateFind();

                    int typeFind = 0;
                    URL url = null;
                    index_horario = 1;


                    try {
                        url = new URL(strUrl);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    try {

                        if (globalVariable.getExistInHistory()==true) {

                            globalVariable.setCurrentWithDataHistoryIndex(globalVariable.getIndexHistory());
                            //1root_current = history.getJSONObject(index_json);
                        } else {
                            CAFData remoteData = null;
                            remoteData = CAFData.dataWithContentsOfURL(url);
                            globalVariable.setDataRootCurrent(new JSONObject(remoteData.toText()));
                            //1root_current = new JSONObject(remoteData.toText());
                            globalVariable.listSearch.add(query);

                            //1history.put(root_current);
                            globalVariable.setDataHistoryIndex();
                            int numer_new = globalVariable.getNumberDataHistory();
                            Log.d("", "run: ");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                JSONObject elemnetlist=null;
                JSONObject wind =null;
                JSONObject main =null;

                try {

                    globalVariable.getListNamesTimesHistory("dt_txt");
                    globalVariable.getListMeassureHistory("");


                    JSONArray listobject =  globalVariable.root_current.getJSONArray("list");

                    elemnetlist = listobject.getJSONObject(index_horario);
                    wind = elemnetlist.getJSONObject("wind");
                    main = elemnetlist.getJSONObject("main");

                    speed = (float)wind.getDouble("speed");
                    deg = (float)wind.getDouble("deg");
                    temp = (float)main.getDouble("temp");
                    tempMin = (float)main.getDouble("temp_min");
                    tempMax = (float)main.getDouble("temp_max");

                    int number = globalVariable.getNumberDataHistory();
                    Log.d("","");


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lblCurrent.setText(String.valueOf(temp));
                        lblMin.setText(String.valueOf(tempMin));
                        lblMax.setText(String.valueOf(tempMax));
                        lblDeg.setText(String.valueOf(deg));
                        lblSpeed.setText(String.valueOf(speed));
                    }
                });
            }
        };
        queue.execute(thread);

    }

    public int getIndex() {
        return index_city;
    }

    public boolean getStateFind() {
        return find_city;
    }

    public void extractInfoJson(JSONObject object){


    }

    /*public  void getListNamesTimes(JSONObject object, String field) throws JSONException {


        final int numberDates = object.getInt("cnt");
        JSONArray listJSON=  object.getJSONArray("list");
        JSONObject elemnetJSON = null;

        for (int i = 0; i < numberDates; i++)
        {

            elemnetJSON = listJSON.getJSONObject(i);

            String name = elemnetJSON.getString(field);
            constant.listTime.add(name);

        }


    }*/


    /*public void validateExistField(String strCity, String field) {

        // compare data web with local data
        int length_history = history.length();
        String name_city = "";

        boolean find_=false;
        int index_ = 0;

        JSONObject value = null;
        //JSONObject root_current =null;


        for (int i = 0; i < length_history; i++) {


            try {

                value = history.getJSONObject(i);
                JSONObject listValues=  value.getJSONObject("city");

                name_city = listValues.getString(field);
                Log.d(field, name_city.toString());

                String name_city_m = name_city.toUpperCase();
                String queryTmp_m = strCity.toUpperCase();

                if(name_city_m.equals(queryTmp_m))
                {
                    find_= true;
                    index_=i;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        this.find_city = find_;
        this.index_city = index_;
    }*/



}
