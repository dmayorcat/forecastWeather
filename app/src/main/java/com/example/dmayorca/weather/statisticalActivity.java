package com.example.dmayorca.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

public class statisticalActivity extends AppCompatActivity {

    public BarChart barChart;
    public BarData theData;
    public BarDataSet barDataset;

    public ArrayList<BarEntry> barEntries = new ArrayList<>();
    public ArrayList<String> theDates = new ArrayList<>();
    GlobalVars globalVariable;
    public int typeData =1;
    Spinner spinnerVal =null;
    ArrayAdapter<String> adapterData;


    String[] stringData = {"Temperature","Humidity","Pressure"};

    public statisticalActivity() {
        barDataset = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_statistical);

        adapterData = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stringData);
        spinnerVal = findViewById(R.id.spinnerVal);
        spinnerVal.setAdapter(adapterData);
        adapterData.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        globalVariable  = (GlobalVars) getApplicationContext();
        barChart = findViewById(R.id.bargraph);
        updateDataBarchar();

        spinnerVal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               //typeData = Integer.parseInt(spinnerVal.getSelectedItem().toString());
              // updateDataBarchar();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

void updateDataBarchar() {

    if (globalVariable.getNumberDataStatisticalHistory() > 3) {
        for (int i = 0; i < 4; i++) {

            if(typeData == 1) {
                Float valueData = globalVariable.listTemperature.get(i);
                barEntries.add(new BarEntry(i, valueData));
                barDataset = new BarDataSet(barEntries, "Temperature[°C]");
            }
            else if(typeData ==2) {
                Float valueData = globalVariable.listHumidity.get(i);
                barEntries.add(new BarEntry(i, valueData));
                barDataset = new BarDataSet(barEntries, "Humidity[%]");
            }
            else if(typeData == 3) {
                Float valueData = globalVariable.listPressure.get(i);
                barEntries.add(new BarEntry(i, valueData));
                barDataset = new BarDataSet(barEntries, "Pressure[kpa]");
            }
        }
    }



    BarData theData = new BarData(barDataset);
    theData.setBarWidth(0.9f); // set custom bar width

    barChart.setData(theData);
    barChart.setFitBars(true);
    barChart.setScaleEnabled(true);
}



}
