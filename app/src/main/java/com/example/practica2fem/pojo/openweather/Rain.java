package com.example.practica2fem.pojo.openweather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//import com.fasterxml.jackson.annotation.JsonProperty;
public class Rain{
    //@JsonProperty("1h")
    @SerializedName("_1h")
    @Expose
    public double _1h;

    public double get_1h() {
        return _1h;
    }

    public void set_1h(double _1h) {
        this._1h = _1h;
    }
}
