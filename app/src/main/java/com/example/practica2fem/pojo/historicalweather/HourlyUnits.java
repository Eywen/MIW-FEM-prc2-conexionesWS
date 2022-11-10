package com.example.practica2fem.pojo.historicalweather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HourlyUnits{
    @SerializedName("time")
    @Expose
    public String time;
    @SerializedName("temperature_2m")
    @Expose
    public String temperature_2m;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemperature_2m() {
        return temperature_2m;
    }

    public void setTemperature_2m(String temperature_2m) {
        this.temperature_2m = temperature_2m;
    }
}
