package com.example.practica2fem;

import static android.app.PendingIntent.getActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.practica2fem.device.ClimateChangeApiAdapter;
import com.example.practica2fem.device.ISpikeRESTAPIService;
import com.example.practica2fem.models.citiedatabase.CityEntity;
import com.example.practica2fem.models.citiedatabase.CityViewModel;
import com.example.practica2fem.models.telemetrydatabase.TelemetriaEntity;
import com.example.practica2fem.models.telemetrydatabase.TelemetriaViewModel;
import com.example.practica2fem.pojo.geocodingResponse.GeocodingCityResponse;
import com.example.practica2fem.pojo.geocodingResponse.GeocodingData;
import com.example.practica2fem.pojo.historicalweather.HistoricalWatherResponse;
import com.example.practica2fem.pojo.openweather.OpenWeatherResponse;
import com.example.practica2fem.pojo.telemetry.AuthorizationBearer;
import com.example.practica2fem.pojo.telemetry.Co2;
import com.example.practica2fem.pojo.telemetry.Humidity;
import com.example.practica2fem.pojo.telemetry.Light;
import com.example.practica2fem.pojo.telemetry.Measurement;
import com.example.practica2fem.pojo.telemetry.Sensors;
import com.example.practica2fem.pojo.telemetry.SoilTemp1;
import com.example.practica2fem.pojo.telemetry.SoilTemp2;
import com.example.practica2fem.pojo.telemetry.Temperature;
import com.example.practica2fem.pojo.telemetry.Credentials;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String API_LOGIN_POST_TELEMETRY = "https://thingsboard.cloud/api/auth/"; // Base url to obtain token
    private static final String API_BASE_GET_TELEMETRY = "https://thingsboard.cloud:443/api/plugins/telemetry/DEVICE/"; // Base url to obtain data
    private static final String API_TOKEN_TELEMETRY = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdHVkZW50dXBtMjAyMkBnbWFpbC5jb20iLCJ1c2VySWQiOiI4NDg1OTU2MC00NzU2LTExZWQtOTQ1YS1lOWViYTIyYjlkZjYiLCJzY29wZXMiOlsiVEVOQU5UX0FETUlOIl0sImlzcyI6InRoaW5nc2JvYXJkLmNsb3VkIiwiaWF0IjoxNjY4OTMyMTkwLCJleHAiOjE2Njg5NjA5OTAsImZpcnN0TmFtZSI6IlN0dWRlbnQiLCJsYXN0TmFtZSI6IlVQTSIsImVuYWJsZWQiOnRydWUsImlzUHVibGljIjpmYWxzZSwiaXNCaWxsaW5nU2VydmljZSI6ZmFsc2UsInByaXZhY3lQb2xpY3lBY2NlcHRlZCI6dHJ1ZSwidGVybXNPZlVzZUFjY2VwdGVkIjp0cnVlLCJ0ZW5hbnRJZCI6ImUyZGQ2NTAwLTY3OGEtMTFlYi05MjJjLWY3NDAyMTlhYmNiOCIsImN1c3RvbWVySWQiOiIxMzgxNDAwMC0xZGQyLTExYjItODA4MC04MDgwODA4MDgwODAifQ.4jn1wUovgP4GCmC7HS02XGqeiHkCXrKAEqEKISBrPw_lcjlyjgEDRqbmRJhLmrL86ZxRfNkr54FZpHM8r2Ko8A";
    private static final String BEARER_TOKEN_TELEMETRY = "Bearer " + API_TOKEN_TELEMETRY;
    private static final String BEARER = "Bearer " ;
    private static final String DEVICE_ID_TELEMETRY = "cf87adf0-dc76-11ec-b1ed-e5d3f0ce866e";
    private static final String USER_THB = "studentupm2022@gmail.com";
    private static final String PASS_THB = "student";
    private static final String API_BASE_HISTORICAL_WEATHER = "https://archive-api.open-meteo.com/v1/";
    private static final String API_BASE_ACTUAL_WEATHER = "https://api.openweathermap.org/data/2.5/";
    private static final String API_BASE_GEOCODING_OPEN_METEO = "https://geocoding-api.open-meteo.com/v1/";


    protected final String LOG_TAG = "MiW";
    private String cityName = "Madrid";
    private String dateHistoricalWeather = "2000-01-01";


    DatePickerDialog picker;
    EditText eText;
    Button btnGet;
    TextView tvw;
    /*SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    Date actualDate = new Date();
    sActualWeatherDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(new Date());*/

    String sActualWeatherDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(Calendar.getInstance().getTime());
Integer hours = null;
    private CityViewModel cityViewModel;
    private TextView telemetryLastTemperature;
    private TextView telemetryLastDate;
    private TextView actualWeatherTemperature;
    private TextView historicalWeather;
    private TextView historicalWeatherTimeTemp;
    private TextView chooseCity;
    private TextView actualWeatherCity;
    private TextView historicalWeatherCity;
    private EditText etCityName;
    private EditText etHistoricalDate;

    TelemetriaViewModel telemetriaViewModel;
    private String sAuthBearerToken ="";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_weather);

        cityViewModel = ViewModelProviders.of(this).get(CityViewModel.class);
        actualWeatherCity = (TextView) findViewById(R.id.tvActualWeatherCity);
        actualWeatherCity.setText(cityName);
        historicalWeatherCity = (TextView) findViewById(R.id.tvHistoricalWeatherCity);
        historicalWeatherCity.setText(cityName);
        findViewById(R.id.btnFindWeather).setOnClickListener(this);



        //tvw=(TextView)findViewById(R.id.textView1);
        eText=(EditText) findViewById(R.id.editText1);
        eText.setInputType(InputType.TYPE_NULL);

/////////////////////////////////////////
        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(WeatherActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateHistoricalWeather = year  + "-" + (monthOfYear + 1) + "-" + (dayOfMonth < 10 ? "0"+dayOfMonth : dayOfMonth);
                                eText.setText( dateHistoricalWeather);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        /*btnGet=(Button)findViewById(R.id.button1);
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvw.setText("Selected Date: "+ eText.getText());
            }
        });*/



    /////////////////////////////////////////////////////////

        //TODO: PONER BOTON DE DESCONECTARSE U OPCION DE MENU
        //MAIN.signOut();
        postBearerToken();
        //TODO: HAcer observable el token
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            ZonedDateTime now = ZonedDateTime.now(ZonedDateTime.now().getZone());
            hours = now.getHour();
        }else
            hours = Calendar.getInstance().getInstance().getTime().getHours();
        //Agregar a bbdd la ciudad consultada si no existe en ella.
        CityEntity cityEntity = citiesDataPersist(cityName);
        //TODO: si citientity es null que vuelva a elejir ciudad  y no se llama
        if (null != cityEntity) {
            getHistoricalWeatherAPI(cityEntity);
            getActualWeather(cityEntity);
            getLastTelemetryAPI();
        } else {
            Log.i(LOG_TAG,"Ciudad erronea. Entre el nombre de la ciudad.");
        }
        //TODO: guardar en bbdd firebase los datos de telemetry, actual weather, historical weather, data consult

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();

       if (i == R.id.btnFindWeather) {
            etCityName = findViewById(R.id.etCity);
            etHistoricalDate = findViewById(R.id.editText1);
            cityName = etCityName.getText().toString();
           dateHistoricalWeather = etHistoricalDate.getText().toString();
            Log.i(LOG_TAG,"City to find: " + cityName);
            actualWeatherCity.setText(cityName);
            historicalWeatherCity.setText(cityName);
           historicalWeatherTimeTemp.findViewById(R.id.tvHistoricalWeatherTimeTemp);
           historicalWeatherTimeTemp.setText(dateHistoricalWeather);
            CityEntity cityEntity = citiesDataPersist(cityName);
           // citiesDataPersist(cityName);
            //getActualWeather(cityEntity);
            //getHistoricalWeatherAPI(cityEntity);
        }
    }

    private void showDatePickerDialog() {

    }

    private void postBearerToken() {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(API_LOGIN_POST_TELEMETRY)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ISpikeRESTAPIService iApi = retrofit.create(ISpikeRESTAPIService.class);
        Credentials c = new Credentials("studentupm2022@gmail.com","student");
        Call<AuthorizationBearer> call = iApi.postAuthorizationBearer(c);

        call.enqueue(new Callback<AuthorizationBearer>() {
            @Override
            public void onResponse(Call<AuthorizationBearer> call, Response<AuthorizationBearer> response) {
                //Toast.makeText(WeatherActivity.this, "Data posted to API", Toast.LENGTH_SHORT).show();
                AuthorizationBearer responseFromAPI = response.body();
                String responseString = "Response Code : " + response.code() + "\nToken : " + responseFromAPI.getToken() + "\n" + "RefreshToken : " + responseFromAPI.getRefreshToken();
                Log.i(LOG_TAG, " response: "+responseString);
                sAuthBearerToken = responseFromAPI.getToken();
                Log.i(LOG_TAG, " granted AuthBearerToken: "+sAuthBearerToken);
            }

            @Override
            public void onFailure(Call<AuthorizationBearer> call, Throwable t) {
                Log.e(LOG_TAG, " error message: "+t.getMessage());
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.opciones_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.opcAjustes: // @todo Preferencias
//                startActivity(new Intent(this, BantumiPrefs.class));
//                return true;
            /*case R.id.opcAcercaDe:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.aboutTitle)
                        .setMessage(R.string.aboutMessage)
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
                return true;*/
            case R.id.opcSalir:
                onBackPressed();
                //((MainActivity)getActivity()).signOut();
                return true;
            case R.id.opcGuardarPartida:
                return true;

        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
    private CityEntity citiesDataPersist(String cityName) {
        List<CityEntity> listcityInBBDD = cityViewModel.finByName(cityName);
        CityEntity cityEntity = null;

        if ((null == listcityInBBDD) || listcityInBBDD.isEmpty()) {
            cityEntity = getGeocodingCityAPI(cityName, true);
        } else if (listcityInBBDD.size() > 1){
            //TODO: mostrar al usuario el mensaje
            Log.i(LOG_TAG, "Hay mas de una ciudad con ese nombre, elija la que quiere: " + listcityInBBDD.toString());
        } else {
            cityEntity = listcityInBBDD.get(0);
        }

        return cityEntity;
    }

    private CityEntity getGeocodingCityAPI(String cityName, boolean newFindWeather) {

        //List<CityEntity> listcityInBBDD = cityViewModel.finByName(cityName);
        //if ((null == listcityInBBDD) || listcityInBBDD.isEmpty()) {
        //https://geocoding-api.open-meteo.com/v1/search?name=bogota&count=1
        // String service = "geocoding";
        //String cityName = "Madrid";
        String citiesResponseNumber = "1";
        final CityEntity[] cityEntityResult = {null};

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(API_BASE_GEOCODING_OPEN_METEO)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ISpikeRESTAPIService iApi = retrofit.create(ISpikeRESTAPIService.class);
        Log.i(LOG_TAG, " request params geocoding: |" + cityName + "|" + citiesResponseNumber);
        Call<GeocodingCityResponse> call = iApi.getGeocoding(cityName, citiesResponseNumber);

        call.enqueue(new Callback<GeocodingCityResponse>() {
            @Override
            public void onResponse(Call<GeocodingCityResponse> call, Response<GeocodingCityResponse> response) {
                GeocodingCityResponse responseFromAPI = response.body();
                String responseString = "Response Code : " + response.code();
                Log.i(LOG_TAG, " response GeocodingCity: " + responseString);

                if (responseFromAPI == null) {
                    Log.i(LOG_TAG, " API returned empty values for city name");
                } else {
                    GeocodingData geocodingData = responseFromAPI.getResults().get(0);
                    CityEntity cityEntity = new CityEntity();
                    cityEntity.setId(geocodingData.getId());
                    cityEntity.setName(geocodingData.getName());
                    cityEntity.setLatitude(geocodingData.getLatitude());
                    cityEntity.setLongitude(geocodingData.getLongitude());
                    cityEntity.setElevation(geocodingData.getElevation());
                    cityEntity.setFeature_code(geocodingData.getFeature_code());
                    cityEntity.setCountry_code(geocodingData.getCountry_code());
                    cityEntity.setAdmin1_id(geocodingData.getAdmin1_id());
                    cityEntity.setAdmin2_id(geocodingData.getAdmin2_id());
                    cityEntity.setTimezone(geocodingData.getTimezone());
                    cityEntity.setPopulation(geocodingData.getPopulation());
                    cityEntity.setCountry_id(geocodingData.getCountry_id());
                    cityEntity.setCountry(geocodingData.getCountry());
                    cityEntity.setAdmin1(geocodingData.getAdmin1());
                    cityEntity.setAdmin2(geocodingData.getAdmin2());
                    Log.i(LOG_TAG, " geocoding data"
                            + " [" + String.valueOf(geocodingData.getName()) + "|" + String.valueOf(geocodingData.getCountry())
                            + " | " + String.valueOf(geocodingData.getCountry_code())
                            + "] [" + String.valueOf(geocodingData.getLatitude()) + "|" + String.valueOf(geocodingData.getLongitude()) + "]");
                    //buscar el id  en la bbdd y si no existe agregarla.
                    CityEntity cityInBBDD = cityViewModel.finById(geocodingData.getId());
                    if (null == cityInBBDD){
                        cityViewModel.insert(cityEntity);
                    } else cityEntity = cityInBBDD;
                    cityEntityResult[0] = cityEntity;

                    if (newFindWeather){
                        getActualWeather(cityEntity);
                        getHistoricalWeatherAPI(cityEntity);
                    }
                }
            }

            @Override
            public void onFailure(Call<GeocodingCityResponse> call, Throwable t) {
                Log.e(LOG_TAG, " error message: " + t.getMessage());
            }
        });
        return cityEntityResult[0];
        //}
    }


    private void getLastTelemetryAPI() {
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
                .baseUrl(API_BASE_GET_TELEMETRY)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ISpikeRESTAPIService iApi = retrofit.create(ISpikeRESTAPIService.class);

            Log.i(LOG_TAG, " last telemetry sAuthBearerToken: " + sAuthBearerToken);

        Log.i(LOG_TAG, " request params: |"+ sAuthBearerToken +"|"+ DEVICE_ID_TELEMETRY +"|"+keys+"|"+useStrictDataTypes);
        Call<Measurement> call = iApi.getLastTelemetry("Bearer " + API_TOKEN_TELEMETRY, DEVICE_ID_TELEMETRY, keys, useStrictDataTypes);


        call.enqueue(new Callback<Measurement>() {
            @Override
            public void onResponse(Call<Measurement> call, Response<Measurement> response) {
                Toast.makeText(WeatherActivity.this, "Data posted to API", Toast.LENGTH_SHORT).show();
                Measurement lm = response.body();

                if (response.code() == 401) {
                    Log.i(LOG_TAG, " API returned 401 token caducado telemetry");
                }

                if (lm == null) {
                    Log.i(LOG_TAG, " API returned empty values for telemetry");
                }else {
                    String responseString = "Response Code last telemetry : " + response.code();
                    String temperature = lm.getTemperature().get(0).getValue();
                    Log.i(LOG_TAG, " response last telemetry: " + responseString);
                    Log.i(LOG_TAG, " response tempeture last telemetry: " + lm.getTemperature().get(0).getValue());
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                    Date dateLastTelemetry = new Date(lm.getTemperature().get(0).getTs());
                    Log.i(LOG_TAG, " response date tempeture last telemetry: " + dateLastTelemetry);
                    telemetryLastTemperature = (TextView) findViewById(R.id.tvATelemetryLastTemperature);
                    telemetryLastDate = (TextView) findViewById(R.id.tvATelemetryLastDate);
                    //TODO: hacerlo observable
                    telemetryLastTemperature.setText(temperature);
                    telemetryLastDate.setText(dateLastTelemetry.toString());
                }
            }

            @Override
            public void onFailure(Call<Measurement> call, Throwable t) {
                Log.e(LOG_TAG, " error message: "+t.getMessage());
            }
        });

    }

    private void getTelemetriesAPI() {

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
                .baseUrl(API_BASE_GET_TELEMETRY)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ISpikeRESTAPIService iApi = retrofit.create(ISpikeRESTAPIService.class);
        Log.i(LOG_TAG, " request params: |"+ BEARER_TOKEN_TELEMETRY +"|"+ DEVICE_ID_TELEMETRY +"|"+keys+"|"+iniTimestamp+"|"+endTimestamp);
        Call<Sensors> call = iApi.getTelemetries(BEARER_TOKEN_TELEMETRY, DEVICE_ID_TELEMETRY, keys, iniTimestamp, endTimestamp);

        call.enqueue(new Callback<Sensors>() {
            @Override
            public void onResponse(Call<Sensors> call, Response<Sensors> response) {
                Toast.makeText(WeatherActivity.this, "Data posted to API", Toast.LENGTH_SHORT).show();
                Sensors responseFromAPI = response.body();
                String responseString = "Response Code : " + response.code();
                Log.i(LOG_TAG, " response: "+responseString);

                if (responseFromAPI == null) {
                    Log.i(LOG_TAG, " API returned empty values for telemetry");
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

    private void getHistoricalWeatherAPI(CityEntity cityEntity) {
        //https://archive-api.open-meteo.com/v1/era5?latitude=52.52&longitude=13.41&start_date=2022-01-01&end_date=2022-07-13&hourly=temperature_2m

        //Map <String, String> parametersMap = new HashMap<>();
        String latitude = String.valueOf(cityEntity.getLatitude());
        String longitude = String.valueOf(cityEntity.getLongitude());
        //TODO: buscar los datos de fecha dada en pantalla por el usuario
        String starDate = "2000-01-01";
        String endDate = "2000-01-01";
        String hourly = "temperature_2m";
        /*parametersMap.put("latitude", latitude);
        parametersMap.put("longitude", longitude);
        parametersMap.put("start_date", starDate);
        parametersMap.put("end_date", endDate);
        parametersMap.put("hourly", "temperature_2m");*/

        Log.i(LOG_TAG, " request params historicalWeather: |"+ latitude +"|"+ longitude +"|"+dateHistoricalWeather+"|"+dateHistoricalWeather+"|"+hourly);
        Call<HistoricalWatherResponse> call = ClimateChangeApiAdapter.getApiServiceOpenMeteo()
                .getHistoricalRangeWeather(latitude,longitude,dateHistoricalWeather,dateHistoricalWeather,hourly);

        call.enqueue(new Callback<HistoricalWatherResponse>() {
            @Override
            public void onResponse(Call<HistoricalWatherResponse> call, Response<HistoricalWatherResponse> response) {
                HistoricalWatherResponse responseFromAPI = response.body();
                String responseString = "Response Code : " + response.code();
                Log.i(LOG_TAG, " response historicalWeather: "+responseString);

                if (responseFromAPI == null) {
                    Log.i(LOG_TAG, " API returned empty values for range`s time historical weather");
                }else {
                    Map<String, Double> mapHourly = responseFromAPI.getHourly().basicHourlyToMapHourly();
                    TreeMap<String, Double> tShortHourly = new TreeMap<>();
                    tShortHourly.putAll(mapHourly);
                    Log.i(LOG_TAG, " response historicalWeather Ciudad: "+cityEntity.getName());
                    Log.i(LOG_TAG, " response historicalWeather: "+responseString);
                    Log.i(LOG_TAG, " response historicalWeather mapHourly: "+tShortHourly);
                    String instantWather = dateHistoricalWeather + "T" + (hours < 10 ? "0"+hours : hours) + ":00";
                    Log.i(LOG_TAG, " instantWather: "+instantWather);
                    Double historicalTempDateActualWeather = mapHourly.get(instantWather);
                    Log.i(LOG_TAG, " response historicalWeather mapHourly by actualWeather: "+ historicalTempDateActualWeather);



                    historicalWeather = (TextView) findViewById(R.id.tvHistoricalWeatherValue);
                    historicalWeatherTimeTemp = (TextView) findViewById(R.id.tvHistoricalWeatherTimeTemp);
                    //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                    //Date dateLastTelemetry = new Date(tShortHourly.k    lm.getTemperature().get(0).getTs());
                    historicalWeather.setText(historicalTempDateActualWeather.toString());
                    historicalWeatherTimeTemp.setText(instantWather);
                }
            }

            @Override
            public void onFailure(Call<HistoricalWatherResponse> call, Throwable t) {
                Log.e(LOG_TAG, " error message API HistoricalWeather: "+t.getMessage());
            }
        });
    }

    private void getActualWeather(CityEntity cityEntity) {
        //https://api.openweathermap.org/data/2.5/weather?lat=44.34&lon=10.99&appid=7f632d39f8412e4d9fee1661705f8832&units=metric
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(API_BASE_ACTUAL_WEATHER)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ISpikeRESTAPIService iApi = retrofit.create(ISpikeRESTAPIService.class);
        String latitude = String.valueOf(cityEntity.getLatitude());
        String longitude = String.valueOf(cityEntity.getLongitude());
        String appidKey = "7f632d39f8412e4d9fee1661705f8832";
        String unitsTemperature = "metric";


        Log.i(LOG_TAG, " request params actualWeather: |"+ latitude +"|"+ longitude +"|"+appidKey+"|"+unitsTemperature);
        Call<OpenWeatherResponse> call = iApi.getActualWeather(latitude,longitude,appidKey,unitsTemperature);

        call.enqueue(new Callback<OpenWeatherResponse>() {
            @Override
            public void onResponse(Call<OpenWeatherResponse> call, Response<OpenWeatherResponse> response) {
                OpenWeatherResponse responseFromAPI = response.body();
                String responseString = "Response Code : " + response.code();
                Log.i(LOG_TAG, " response actualWeather: "+responseString);
                Log.i(LOG_TAG, " date actualWeather: "+sActualWeatherDate);

                if (responseFromAPI == null) {
                    Log.i(LOG_TAG, " API returned empty values for range`s time open weather");
                }else {
                    double temp = responseFromAPI.getMain().getTemp();
                    Log.i(LOG_TAG, " response OpenWeather: "+responseString);
                    Log.i(LOG_TAG, " response ActualWeather temperature: "+temp);

                    actualWeatherTemperature = (TextView) findViewById(R.id.tvActualOutsideWeatherValue);
                    //TODO: hacerlo observable
                    actualWeatherTemperature.setText(String.valueOf(temp));
                }
            }

            @Override
            public void onFailure(Call<OpenWeatherResponse> call, Throwable t) {
                Log.e(LOG_TAG, " error message: "+t.getMessage());
            }
        });
    }
}
