package com.example.practica2fem.models.citiedatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.practica2fem.models.telemetrydatabase.TelemetriaEntity;

@Entity(tableName = CityEntity.TABLA)
public class CityEntity {

    static public final String TABLA = "CITY";

    @PrimaryKey()
    public int id;
    public String name;
    public double latitude;
    public double longitude;
    public double elevation;
    public String feature_code;
    public String country_code;
    public int admin1_id;
    public int admin2_id;
    public String timezone;
    public int population;
    public int country_id;
    public String country;
    public String admin1;
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
