package com.example.practica2fem.pojo.geocodingResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeocodingData {
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("latitude")
    @Expose
    public double latitude;
    @SerializedName("longitude")
    @Expose
    public double longitude;
    @SerializedName("elevation")
    @Expose
    public double elevation;
    @SerializedName("feature_code")
    @Expose
    public String feature_code;
    @SerializedName("country_code")
    @Expose
    public String country_code;
    @SerializedName("admin1_id")
    @Expose
    public int admin1_id;
    @SerializedName("admin2_id")
    @Expose
    public int admin2_id;
    @SerializedName("timezone")
    @Expose
    public String timezone;
    @SerializedName("population")
    @Expose
    public int population;
    @SerializedName("country_id")
    @Expose
    public int country_id;
    @SerializedName("country")
    @Expose
    public String country;
    @SerializedName("admin1")
    @Expose
    public String admin1;
    @SerializedName("admin2")
    @Expose
    public String admin2;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getElevation() {
        return elevation;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    public String getFeature_code() {
        return feature_code;
    }

    public void setFeature_code(String feature_code) {
        this.feature_code = feature_code;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public int getAdmin1_id() {
        return admin1_id;
    }

    public void setAdmin1_id(int admin1_id) {
        this.admin1_id = admin1_id;
    }

    public int getAdmin2_id() {
        return admin2_id;
    }

    public void setAdmin2_id(int admin2_id) {
        this.admin2_id = admin2_id;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAdmin1() {
        return admin1;
    }

    public void setAdmin1(String admin1) {
        this.admin1 = admin1;
    }

    public String getAdmin2() {
        return admin2;
    }

    public void setAdmin2(String admin2) {
        this.admin2 = admin2;
    }
}
