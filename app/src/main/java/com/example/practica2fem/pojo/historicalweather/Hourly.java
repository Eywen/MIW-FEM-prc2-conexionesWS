package com.example.practica2fem.pojo.historicalweather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Hourly{
    @SerializedName("time")
    @Expose
    public ArrayList<String> time;
    @SerializedName("temperature_2m")
    @Expose
    public ArrayList<Double> temperature_2m;

    public Map<String,Double> basicHourlyToMapHourly (){
        Map<String, Double> mapHourly =  new HashMap<>();
        for (int i = 0; i < time.size() || i < temperature_2m.size(); i ++){
                mapHourly.put(time.get(i),temperature_2m.get(i) );
        }
        return mapHourly;
    }

    public ArrayList<String> getTime() {
        return time;
    }

    public void setTime(ArrayList<String> time) {
        this.time = time;
    }

    public ArrayList<Double> getTemperature_2m() {
        return temperature_2m;
    }

    public void setTemperature_2m(ArrayList<Double> temperature_2m) {
        this.temperature_2m = temperature_2m;
    }
}
