package com.example.practica2fem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.practica2fem.device.ISpikeRESTAPIService;
import com.example.practica2fem.models.TelemetriaEntity;
import com.example.practica2fem.models.TelemetriaViewModel;
import com.example.practica2fem.pojo.Co2;
import com.example.practica2fem.pojo.Humidity;
import com.example.practica2fem.pojo.Light;
import com.example.practica2fem.pojo.Measurement;
import com.example.practica2fem.pojo.Sensors;
import com.example.practica2fem.pojo.SoilTemp1;
import com.example.practica2fem.pojo.SoilTemp2;
import com.example.practica2fem.pojo.Temperature;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    static String LOG_TAG = "MIW-FEM";

    //private ISpikeRESTAPIService apiService;
    private static final String API_LOGIN_POST = "https://thingsboard.cloud/api/auth/"; // Base url to obtain token
    private static final String API_BASE_GET = "https://thingsboard.cloud:443/api/plugins/telemetry/DEVICE/"; // Base url to obtain data
    private static final String TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdHVkZW50dXBtMjAyMkBnbWFpbC5jb20iLCJ1c2VySWQiOiI4NDg1OTU2MC00NzU2LTExZWQtOTQ1YS1lOWViYTIyYjlkZjYiLCJzY29wZXMiOlsiVEVOQU5UX0FETUlOIl0sImlzcyI6InRoaW5nc2JvYXJkLmNsb3VkIiwiaWF0IjoxNjY3OTIyODQ2LCJleHAiOjE2Njc5NTE2NDYsImZpcnN0TmFtZSI6IlN0dWRlbnQiLCJsYXN0TmFtZSI6IlVQTSIsImVuYWJsZWQiOnRydWUsImlzUHVibGljIjpmYWxzZSwiaXNCaWxsaW5nU2VydmljZSI6ZmFsc2UsInByaXZhY3lQb2xpY3lBY2NlcHRlZCI6dHJ1ZSwidGVybXNPZlVzZUFjY2VwdGVkIjp0cnVlLCJ0ZW5hbnRJZCI6ImUyZGQ2NTAwLTY3OGEtMTFlYi05MjJjLWY3NDAyMTlhYmNiOCIsImN1c3RvbWVySWQiOiIxMzgxNDAwMC0xZGQyLTExYjItODA4MC04MDgwODA4MDgwODAifQ.M12uD4tg-AHgCh9V1ID8d1y6Woc3gRbm56MCxVb4KHwUjFJoD6dM5SmnyWSK3GNmE3F6fPkB-X3nl9GxDj-a7Q";
    private static final String BEARER_TOKEN = "Bearer " + TOKEN;
    private static final String DEVICE_ID = "cf87adf0-dc76-11ec-b1ed-e5d3f0ce866e";
    private static final String USER_THB = "studentupm2022@gmail.com";
    private static final String PASS_THB = "student";

    TelemetriaViewModel telemetriaViewModel;
    private String sAuthBearerToken ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getTelemetries();
    }

    private void getLastTelemetry() {
        //https://thingsboard.cloud:443/api/plugins/telemetry/DEVICE/{{deviceId}}/values/timeseries?keys=co2&useStrictDataTypes=false
        String keys = "co2,humidity,light,soilTemp1,soilTemp2,temperature";
        String useStrictDataTypes = "false";

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(API_BASE_GET)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ISpikeRESTAPIService iApi = retrofit.create(ISpikeRESTAPIService.class);
        Log.i(LOG_TAG, " request params: |"+ BEARER_TOKEN +"|"+ DEVICE_ID +"|"+keys+"|"+useStrictDataTypes);
        Call<Measurement> call = iApi.getLastTelemetry(BEARER_TOKEN, DEVICE_ID, keys, useStrictDataTypes);


        call.enqueue(new Callback<Measurement>() {
            @Override
            public void onResponse(Call<Measurement> call, Response<Measurement> response) {
                Toast.makeText(MainActivity.this, "Data posted to API", Toast.LENGTH_SHORT).show();
                Measurement lm = response.body();

                String responseString = "Response Code : " + response.code();
                Log.i(LOG_TAG, " response: "+responseString);
                Log.i(LOG_TAG, " response.body: "+lm.getCo2().get(0).getValue());
            }

            @Override
            public void onFailure(Call<Measurement> call, Throwable t) {
                Log.e(LOG_TAG, " error message: "+t.getMessage());
            }
        });

    }
    private void getPruebaConexion(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .build();

        //GitHubService service = retrofit.create(GitHubService.class);
    }

    private void getTelemetries() {

        String keys = "co2,humidity,light,soilTemp1,soilTemp2,temperature";

        // Use https://www.epochconverter.com/ to get milliseconds for a given date
        // These are some date examples:
        // String iniTimestamp = "1666735200000";// 26/10/2023
        // String iniTimestamp = "972684000000";// 28/10/2000
        // String iniTimestamp = "1666735200000";// 26/10/2023
        String iniTimestamp = "972684000000";// 28/10/2000
        String endTimestamp = "1666908000000";// 28/10/2023

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(API_BASE_GET)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ISpikeRESTAPIService iApi = retrofit.create(ISpikeRESTAPIService.class);
        Log.i(LOG_TAG, " request params: |"+ BEARER_TOKEN +"|"+ DEVICE_ID +"|"+keys+"|"+iniTimestamp+"|"+endTimestamp);
        Call<Sensors> call = iApi.getTelemetries(BEARER_TOKEN, DEVICE_ID, keys, iniTimestamp, endTimestamp);

        call.enqueue(new Callback<Sensors>() {
            @Override
            public void onResponse(Call<Sensors> call, Response<Sensors> response) {
                Toast.makeText(MainActivity.this, "Data posted to API", Toast.LENGTH_SHORT).show();
                Sensors responseFromAPI = response.body();
                String responseString = "Response Code : " + response.code();
                Log.i(LOG_TAG, " response: "+responseString);

                if (responseFromAPI == null) {
                    Log.i(LOG_TAG, " API returned empty values for sensors");
                }else{

                    List<Co2> lCo2 = responseFromAPI.getCo2();
                    List<Humidity> lHum = responseFromAPI.getHumidity();
                    List<Light> lLig = responseFromAPI.getLight();
                    List<SoilTemp1> lST1 = responseFromAPI.getSoilTemp1();
                    List<SoilTemp2> lST2 = responseFromAPI.getSoilTemp2();
                    List<Temperature> lTem = responseFromAPI.getTemperature();


                    DateFormat df = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
                    }
                    for (int i = 0; i < lCo2.size()-1; i++) {

                        Date currentDate = new Date(lCo2.get(i).getTs());
                        String sTs = df.format(currentDate);
                        TelemetriaEntity tmItem =  new TelemetriaEntity(sTs,
                                Integer.parseInt((String)lCo2.get(i).getValue()),
                                Integer.parseInt((String)lHum.get(i).getValue()),
                                Integer.parseInt((String)lLig.get(i).getValue()),
                                Integer.parseInt((String)lST1.get(i).getValue()),
                                Integer.parseInt((String)lST2.get(i).getValue()),
                                Integer.parseInt((String)lTem.get(i).getValue())
                        );
                        //telemetriaViewModel.insert(tmItem);
                        Log.i(LOG_TAG, " timestamps ["+i
                                +"] ["+String.valueOf(lCo2.get(i).getTs())+"|"+String.valueOf(lCo2.get(i).getValue())+"]"
                                +"] ["+String.valueOf(lHum.get(i).getTs())+"|"+String.valueOf(lHum.get(i).getValue())+"]"
                                +"] ["+String.valueOf(lLig.get(i).getTs())+"|"+String.valueOf(lLig.get(i).getValue())+"]"
                                +"] ["+String.valueOf(lST1.get(i).getTs())+"|"+String.valueOf(lST1.get(i).getValue())+"]"
                                +"] ["+String.valueOf(lST2.get(i).getTs())+"|"+String.valueOf(lST2.get(i).getValue())+"]"
                                +"] ["+String.valueOf(lTem.get(i).getTs())+"|"+String.valueOf(lTem.get(i).getValue())+"]");
                    }
                    Log.i(LOG_TAG, " response: "+responseFromAPI.toString());
                }
            }

            @Override
            public void onFailure(Call<Sensors> call, Throwable t) {
                Log.e(LOG_TAG, " error message: "+t.getMessage());
            }
        });

    }
}