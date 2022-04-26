package com.example.gps_maps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    // Updates intervals
    public static final int DEFAULT_UPDATE_INTERVAL = 30;
    public static final int FAST_UPDATE_INTERVAL = 5;
    private static final int PERMISSION_FINE_LOCATION = 99;


    TextView tv_lat, tv_lon, tv_altitude, tv_accuracy, tv_address, tv_speed, tv_updates, tv_sensor,tv_waypointCounts;;
    Button btn_continue, btn_newWayPoint;
    List<TextView> gps_tvs= new ArrayList<>();
    Switch sw_locationsupdates, sw_gps;

    // WayPoint
    Location currentLocation;
    List<Location> savedLocations;
    List<LocationList> myList;

    // Google API for location services
    FusedLocationProviderClient fusedLocationProviderClient;
    boolean updateOn = false;
    LocationRequest locationRequest;
    LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gps_tvs.add(tv_lat = findViewById(R.id.tv_lat));
        gps_tvs.add(tv_lon = findViewById(R.id.tv_lon));
        gps_tvs.add(tv_altitude = findViewById(R.id.tv_altitude));
        gps_tvs.add(tv_accuracy = findViewById(R.id.tv_accuracy));
        gps_tvs.add(tv_address = findViewById(R.id.tv_address));
        gps_tvs.add(tv_speed = findViewById(R.id.tv_speed));
        gps_tvs.add(tv_updates = findViewById(R.id.tv_updates));

//        tv_waypointCounts = findViewById(R.id.tv_waypointCounts);
        tv_sensor = findViewById(R.id.tv_sensor);
        sw_locationsupdates = findViewById(R.id.sw_locationsupdates);
        sw_gps = findViewById(R.id.sw_gps);
        btn_continue = findViewById(R.id.btn_continue);
//        btn_newWayPoint = findViewById(R.id.btn_newWayPoint);

        // Variable initialization

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_FINE_LOCATION);
        }

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                UpdateUIValues(locationResult.getLastLocation());
            }
        };

//        btn_newWayPoint.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MyApplication myApplication = (MyApplication)getApplicationContext();
//                savedLocations = myApplication.getMyLocations();
//                savedLocations.add(currentLocation);
//            }
//        });

        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000 * DEFAULT_UPDATE_INTERVAL);
        locationRequest.setFastestInterval(1000 * FAST_UPDATE_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        btn_continue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this, lists.class);
                    startActivity(i);
            }
        });

        sw_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sw_gps.isChecked()) {
                    locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                    tv_sensor.setText("Using Towers + WIFI");
                } else {
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    tv_sensor.setText("Using GPS sensors");
                }
            }
        });

        sw_locationsupdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetLocationUpdates(sw_locationsupdates.isChecked());
            }
        });

    }

    ///
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_FINE_LOCATION){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                UpdateGPS();
            } else {
                Toast.makeText(this, "ERROR: Permission was not granted for this app", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void UpdateGPS() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    UpdateUIValues(location);
                    currentLocation = location;
                }
            });
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_FINE_LOCATION);
            }
        }
    }

    private void UpdateUIValues(Location location) {
        tv_lat.setText(String.valueOf(location.getLatitude()));
        tv_lon.setText(String.valueOf(location.getLongitude()));
        tv_accuracy.setText(String.valueOf(location.getAccuracy()));
        tv_altitude.setText(location.hasAltitude() ? String.valueOf(location.getAccuracy()) : "N/A");
        tv_speed.setText(location.hasSpeed() ? String.valueOf(location.getSpeed()) : "N/A");
        tv_address.setText(GetAddress(location));
        MyApplication myApplication = (MyApplication)getApplicationContext();
        savedLocations = myApplication.getMyLocations();
    }

    private String GetAddress(Location location){
        String addr;
        try{
            addr = new Geocoder(MainActivity.this).getFromLocation(location.getLatitude(), location.getLongitude(), 1).get(0).getAddressLine(0);
        }
        catch(Exception E){
            addr = "Exception: Unknown Address";
        }
        return addr;
    }

    @SuppressLint("MissingPermission")
    private void SetLocationUpdates(boolean status) {
        if (status) {
            tv_updates.setText("Location is being tracked");
            if(!CheckPermissions()) return;
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
            UpdateGPS();
            return;
        }
        for(TextView tv: gps_tvs) { tv.setText("Location updates Disabled"); };
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    private boolean CheckPermissions(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_FINE_LOCATION);
            }
            return false;
        }
        return true;
    }


}