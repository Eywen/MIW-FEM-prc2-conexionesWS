package com.example.practica2fem.device;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class  ClimateChangeApiAdapter {

    private static OpenMeteoApiService API_SERVICE_OPEN_METEO_HISTOTY;
    private static final String API_BASE_OPEN_METEO_HISTORY = "https://archive-api.open-meteo.com/v1/";

    /**
     * Devolvemos un acceso a la interface OpenMeteoApiService que contiene los métodos de los servicios que se van a consumir
     */
    public static OpenMeteoApiService getApiServiceOpenMeteo() {
        //Creación de interceptor  y asignación de nivel de log
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Asociación del interceptor a las peticiones
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor);

        if (API_SERVICE_OPEN_METEO_HISTOTY == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_OPEN_METEO_HISTORY)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client.build()) // <-- set log level
                    .build();
            API_SERVICE_OPEN_METEO_HISTOTY = retrofit.create(OpenMeteoApiService.class);
        }
        return API_SERVICE_OPEN_METEO_HISTOTY;
    }
}
