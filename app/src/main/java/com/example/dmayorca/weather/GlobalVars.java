package com.example.dmayorca.weather;

import android.app.Application;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GlobalVars extends Application {

    public JSONObject root_current = null;
    public JSONArray history=null;
    public int lengthHistory=0;
    public int lengthDataStatistical=0;
    public boolean find_city = false;
    public int index_city=0;
    public List<String> listSearch = new ArrayList<String>();
    public List<String> listTime = new ArrayList<String>();

    public List<Float> listPressure = new ArrayList<Float>();
    public List<Float> listTemperature = new ArrayList<Float>();
    public List<Float> listHumidity = new ArrayList<Float>();


    public int getValtest() {
        return valtest;
    }

    public void setValtest(int valtest) {
        this.valtest = valtest;
    }

    int valtest=0;

    private static GlobalVars instanceGlobal ;

    public static GlobalVars getInstance() {
        if (instanceGlobal == null) {
            instanceGlobal = new GlobalVars();
        }
        return instanceGlobal;
    }

    public int getNumberDataHistory() {this.lengthHistory = history.length();
        return this.lengthHistory;
    }

    public int getNumberDataStatisticalHistory() {
        this.lengthDataStatistical = this.listPressure.size();
        return this.lengthDataStatistical;
    }

    public void setCurrentWithDataHistoryIndex(int index) throws JSONException {
        this.root_current =  this.history.getJSONObject(index);
    }
    public GlobalVars() {this.root_current=new JSONObject();this.history=new JSONArray();}
    public void initValuesRootCurrent(){this.root_current=new JSONObject();}
    public void setDataRootCurrent(JSONObject d){this.root_current=d;    }
    public JSONObject getDataRootCurrent(){ return this.root_current; }
    public void initValuesHistory(){this.history=new JSONArray();}
    public void setDataHistory(JSONArray d){this.history=d;}
    public void setDataHistoryIndex(){this.history.put(this.root_current);}
    public void setIndexHistory(int d) {this.index_city = d;}
    public int getIndexHistory(){return this.index_city;}
    public void setExistInHistory(boolean d) { this.find_city = d;}
    public boolean getExistInHistory(){return this.find_city;}


    public void validateHistory(String strCity) {

        String name_city = "";
        boolean find_=false;
        int index_ = 0;
        JSONObject value = null;
        int numer_data = this.getNumberDataHistory();

        for (int i = 0; i < this.getNumberDataHistory(); i++) {
            try {

                value = this.history.getJSONObject(i);
                JSONObject listValues=  value.getJSONObject("city");
                name_city = listValues.getString("name");
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
    }


    public  void getListNamesTimesHistory(String field) throws JSONException    {


        final int numberDates = this.root_current.getInt("cnt");
        JSONArray listJSON=  this.root_current.getJSONArray("list");
        JSONObject elemnetJSON = null;
        this.listTime.clear();

        for (int i = 0; i < numberDates; i++)
        {
            elemnetJSON = listJSON.getJSONObject(i);
            String name = elemnetJSON.getString(field);
            this.listTime.add(name);
        }
    }


    public  void getListMeassureHistory(String field) throws JSONException {


        final int numberDates = this.root_current.getInt("cnt");
        JSONArray listJSON=  this.root_current.getJSONArray("list");
        JSONObject elemnetJSON = null;
        JSONObject main =null;

        this.listPressure.clear();
        this.listTemperature.clear();
        this.listHumidity.clear();

        for (int i = 0; i < numberDates; i++)
        {
            elemnetJSON = listJSON.getJSONObject(i);
            main = elemnetJSON.getJSONObject("main");

            float temp = (float)main.getDouble("temp");
            float pressure = (float)main.getDouble("pressure");
            float humidity = (float)main.getDouble("humidity");

            this.listPressure.add(temp);
            this.listTemperature.add(pressure);
            this.listHumidity.add(humidity);
        }
    }
}

