package com.example.wifistatuscheck;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


import android.Manifest;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;


public class MainActivity extends AppCompatActivity {

    private TextView txtWifiCheck;
    private Button btnWifi, btnBluetoothCheck, btnLocationCheck, btnInternetAvailablity, btnVibratePermission, btnAccelerometer;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //txtWifiCheck = findViewById(R.id.txtWifiCheck);
        btnWifi = findViewById(R.id.btnWifi);
        btnBluetoothCheck = findViewById(R.id.btnBluetoothCheck);
        btnLocationCheck = findViewById(R.id.btnLocationCheck);
        btnInternetAvailablity = findViewById(R.id.btnInternetAvailablity);
        btnVibratePermission = findViewById(R.id.btnVibratePermission);
        btnAccelerometer = findViewById(R.id.btnAccelerometer);


        btnWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if device is connected to WiFi
                if (WifiManagerUtil.isConnectedToWifi(MainActivity.this)) {
                    showToast("Connected to WiFi");
                } else {
                    showToast("Not connected to WiFi");
                }
            }
        });

        // Check if Bluetooth Permission is Granted
        /*
        ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH): Checks if the Bluetooth permission is granted.
        PackageManager.PERMISSION_GRANTED: Constant indicating that the permission is granted.*/
        btnBluetoothCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if Bluetooth Permission is Granted
                boolean isBluetoothPermissionGranted = ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED;

                // Show Toast based on Permission Status
                if (isBluetoothPermissionGranted) {
                    Toast.makeText(MainActivity.this, "Bluetooth Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Bluetooth Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Check if Location Permission is Granted
        btnLocationCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if Location Permission is Granted
                boolean isLocationPermissionGranted = ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

                // Show Toast based on Permission Status
                if (isLocationPermissionGranted) {
                    Toast.makeText(MainActivity.this, "Location Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Location Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Internet Availablity
        btnInternetAvailablity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    Toast.makeText(MainActivity.this, "Internet Connection Available", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //
        btnVibratePermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                if (vibrator.hasVibrator()) {
                    Toast.makeText(MainActivity.this, "Vibrator Available", Toast.LENGTH_SHORT).show();
                    // Vibrate for 500 milliseconds
                    vibrator.vibrate(500);
                } else {
                    Toast.makeText(MainActivity.this, "Vibrator Not Available", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // check Accelerometer
        btnAccelerometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the SensorManager service
                SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

                // Check if the accelerometer is available
                if (sensorManager != null) {
                    Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
                    if (accelerometer != null) {
                        Toast.makeText(MainActivity.this, "TYPE_GYROSCOPE Available", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "TYPE_GYROSCOPE Not Available", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
//   closed
    }


    // Method to show toast message
    private void showToast(String message){
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }


   // method for network avilability
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
