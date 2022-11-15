package com.example.practica2fem.pojo.geocodingResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GeocodingCityResponse {
    @SerializedName("results")
    @Expose
    public ArrayList<GeocodingData> results;
    @SerializedName("generationtime_ms")
    @Expose
    public double generationtime_ms;

    public ArrayList<GeocodingData> getResults() {
        return results;
    }

    public void setResults(ArrayList<GeocodingData> results) {
        this.results = results;
    }

    public double getGenerationtime_ms() {
        return generationtime_ms;
    }

    public void setGenerationtime_ms(double generationtime_ms) {
        this.generationtime_ms = generationtime_ms;
    }
}
