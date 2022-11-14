package com.example.practica2fem.pojo.openweather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActualWeather {
    @SerializedName("temp")
    @Expose
    public double temp;
    @SerializedName("feels_like")
    @Expose
    public double feels_like;
    @SerializedName("temp_min")
    @Expose
    public double temp_min;
    @SerializedName("temp_max")
    @Expose
    public double temp_max;
    @SerializedName("pressure")
    @Expose
    public int pressure;
    @SerializedName("humidity")
    @Expose
    public int humidity;
    @SerializedName("sea_level")
    @Expose
    public int sea_level;
    @SerializedName("grnd_level")
    @Expose
    public int grnd_level;

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(double feels_like) {
        this.feels_like = feels_like;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getSea_level() {
        return sea_level;
    }

    public void setSea_level(int sea_level) {
        this.sea_level = sea_level;
    }

    public int getGrnd_level() {
        return grnd_level;
    }

    public void setGrnd_level(int grnd_level) {
        this.grnd_level = grnd_level;
    }
}
