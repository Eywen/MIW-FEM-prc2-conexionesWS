package com.example.practica2fem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.practica2fem.pojo.telemetry.Co2;
import com.example.practica2fem.pojo.telemetry.Humidity;
import com.example.practica2fem.pojo.telemetry.Light;
import com.example.practica2fem.pojo.telemetry.Measurement;
import com.example.practica2fem.pojo.telemetry.Sensors;
import com.example.practica2fem.pojo.telemetry.SoilTemp1;
import com.example.practica2fem.pojo.telemetry.SoilTemp2;
import com.example.practica2fem.pojo.telemetry.Temperature;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.lifecycle.ViewModelProviders;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    static String LOG_TAG = "MIW-FEM";
    private FirebaseAuth mAuth;
    private EditText mEmailField;
    private EditText mPasswordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Fields
        mEmailField = findViewById(R.id.fieldEmail);
        mPasswordField = findViewById(R.id.fieldPassword);

        // Click listeners
        findViewById(R.id.buttonSignIn).setOnClickListener(this);
        findViewById(R.id.buttonAnonymousSignOut).setOnClickListener(this);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        this.updateUI(currentUser);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.buttonSignIn) {
            signInWithCredentials();
            goToWeatherActivity();
        } else if (i == R.id.buttonAnonymousSignOut) {
            signOut();
        }
    }

    private void goToWeatherActivity() {
        Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
        startActivity(intent);
    }

    private void updateUI(FirebaseUser user) {
        TextView uidView = findViewById(R.id.statusId);
        TextView emailView = findViewById(R.id.statusEmail);

        Switch mSwitch = findViewById(R.id.statusSwitch);
        boolean isSignedIn = (user != null);

        // Status text
        if (isSignedIn) {
            uidView.setText(R.string.signed_in);
            emailView.setText(getString(R.string.email_fmt, user.getEmail()));
            mPasswordField.setText("");
            mEmailField.setText("");
            Log.i(LOG_TAG, "signedIn: " + getString(R.string.id_fmt, user.getDisplayName()));
            // Here you should instantiate an Intent to move forward within you app
            goToWeatherActivity();
        } else {
            uidView.setText(R.string.signed_out);
            emailView.setText(null);
            Log.i(LOG_TAG, "signOut: " + getString(R.string.signed_out));
        }

        // Button visibility
        findViewById(R.id.buttonSignIn).setEnabled(!isSignedIn);
        findViewById(R.id.buttonAnonymousSignOut).setEnabled(isSignedIn);
        mSwitch.setChecked(isSignedIn);
    }

    private void signInWithCredentials() {
        if (!validateLinkForm()) {
            return;
        }

        // Get email and password from form
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        // Create EmailAuthCredential with email and password
        AuthCredential credential = EmailAuthProvider.getCredential(email, password);

        // [START signin_with_email_and_password]
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i(LOG_TAG, "signInWithCredentials:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(LOG_TAG, "signInWithCredentials:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed: " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END signin_with_email_and_password]
    }

    private boolean validateLinkForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError(getString(R.string.field_required));
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError(getString(R.string.field_required));
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }


    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

}