package com.example.practica2fem.pojo.openweather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coord{
    @SerializedName("lon")
    @Expose
    public double lon;
    @SerializedName("lat")
    @Expose
    public double lat;

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
