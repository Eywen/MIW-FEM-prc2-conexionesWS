package com.example.practica2fem.pojo.openweather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wind{
    @SerializedName("speed")
    @Expose
    public double speed;
    @SerializedName("deg")
    @Expose
    public int deg;
    @SerializedName("gust")
    @Expose
    public double gust;

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public double getGust() {
        return gust;
    }

    public void setGust(double gust) {
        this.gust = gust;
    }
}
