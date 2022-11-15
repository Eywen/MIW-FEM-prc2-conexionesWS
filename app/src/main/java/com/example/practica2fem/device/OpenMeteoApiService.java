package com.example.practica2fem.device;

import com.example.practica2fem.pojo.geocodingResponse.GeocodingCityResponse;
import com.example.practica2fem.pojo.historicalweather.HistoricalWatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenMeteoApiService {

    @GET("era5")
    Call<HistoricalWatherResponse> getHistoricalRangeWeather(@Query("latitude") String latitude, @Query("longitude") String longitude,
                                                             @Query("start_date") String start_date,
                                                             @Query("end_date") String end_date, @Query("hourly") String hourly);
    //https://archive-api.open-meteo.com/v1/era5?latitude=52.52&longitude=13.41&start_date=2022-01-01&end_date=2022-07-13&hourly=temperature_2m


}
