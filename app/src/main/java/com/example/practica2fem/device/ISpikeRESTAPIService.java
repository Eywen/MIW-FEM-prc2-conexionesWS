package com.example.practica2fem.device;

import com.example.practica2fem.pojo.historicalweather.HistoricalWatherResponse;
import com.example.practica2fem.pojo.telemetry.AuthorizationBearer;
import com.example.practica2fem.pojo.telemetry.Credentials;
import com.example.practica2fem.pojo.telemetry.Measurement;
import com.example.practica2fem.pojo.telemetry.Sensors;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

@SuppressWarnings("Unused")
public interface ISpikeRESTAPIService {
    @POST("login")
    Call<AuthorizationBearer> postAuthorizationBearer(@Body Credentials credentials);

    @GET("{deviceid}/values/timeseries")
    Call<Measurement> getLastTelemetry(@Header("Authorization") String authorization, @Path("deviceid") String deviceId, @Query("keys") String keys, @Query("useStrictDataTypes") String useStrictDataTypes);
    //https://thingsboard.cloud:443/api/plugins/telemetry/DEVICE/{{deviceId}}/values/timeseries?keys=co2&useStrictDataTypes=false

    @GET("{deviceid}/values/timeseries")
    Call<Sensors> getTelemetries(@Header("Authorization") String authorization, @Path("deviceid") String deviceId, @Query("keys") String keys, @Query("startTs") String startTs, @Query("endTs") String endTs);
    // API_BASE_GET = "https://thingsboard.cloud:443/api/plugins/telemetry/DEVICE/";
    // https://thingsboard.cloud:443/api/plugins/telemetry/DEVICE/xxxxxx/values/timeseries?keys=XXXXXXX&startTs=xxxxxx&endTs=xxxxx
    // https://thingsboard.cloud:443/api/plugins/telemetry/DEVICE/{{deviceId}}/values/timeseries?keys=co2,humidity,light,soilTemp1,soilTemp2,temperature&startTs={{iniTimestamp}}&endTs={{endTimestamp}}

    // Authorization TYPE: Bearer Token
    // Token: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdHVkZW50dXBtMjAyMkBnbWFpbC5jb20iLCJ1c2VySWQiOiI4NDg1OTU2MC00NzU2LTExZWQtOTQ1YS1lOWViYTIyYjlkZjYiLCJzY29wZXMiOlsiVEVOQU5UX0FETUlOIl0sImlzcyI6InRoaW5nc2JvYXJkLmNsb3VkIiwiaWF0IjoxNjY2Nzg0OTQ3LCJleHAiOjE2NjY4MTM3NDcsImZpcnN0TmFtZSI6IlN0dWRlbnQiLCJsYXN0TmFtZSI6IlVQTSIsImVuYWJsZWQiOnRydWUsImlzUHVibGljIjpmYWxzZSwiaXNCaWxsaW5nU2VydmljZSI6ZmFsc2UsInByaXZhY3lQb2xpY3lBY2NlcHRlZCI6dHJ1ZSwidGVybXNPZlVzZUFjY2VwdGVkIjp0cnVlLCJ0ZW5hbnRJZCI6ImUyZGQ2NTAwLTY3OGEtMTFlYi05MjJjLWY3NDAyMTlhYmNiOCIsImN1c3RvbWVySWQiOiIxMzgxNDAwMC0xZGQyLTExYjItODA4MC04MDgwODA4MDgwODAifQ.UZxOkXxHFn_GORyEv6NtHui6MFzsUtrrJLk5hNq7OHv0ob6uU9jd_uVtyQu77ombqxQ9ZV1mqU9ADcyAoOqE2w

    @GET("era5")
    Call<HistoricalWatherResponse>  getHistoricalRangeWeather(@Query("latitude") String latitude,@Query("longitude") String longitude, @Query("start_date") String start_date,
                                                              @Query("end_date") String end_date,@Query("hourly") String hourly);
    //https://archive-api.open-meteo.com/v1/era5?latitude=52.52&longitude=13.41&start_date=2022-01-01&end_date=2022-07-13&hourly=temperature_2m
}
