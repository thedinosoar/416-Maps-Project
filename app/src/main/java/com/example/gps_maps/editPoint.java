package com.example.gps_maps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.Debug;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class editPoint extends AppCompatActivity {

    Button btn_cancel_point, btn_delete_point, btn_save_point;
    EditText et_name_point, et_lon, et_lat;
    TextView et_title_point;



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_point);

        btn_cancel_point = findViewById(R.id.btn_cancel_point);
        btn_delete_point = findViewById(R.id.btn_delete_point);
        btn_save_point = findViewById(R.id.btn_save_point);
        et_name_point = findViewById(R.id.et_name_point);
        et_title_point = findViewById(R.id.tv_title_point);
        et_lon = findViewById(R.id.et_lon);
        et_lat = findViewById(R.id.et_lat);
        MyApplication myApplication = (MyApplication)getApplicationContext();
        Waypoint curWp = myApplication.getCurrentWaypoint();

        if(curWp != null){
            et_title_point.setText("Editing: "+curWp.getTitle());
            et_name_point.setText(curWp.getTitle());
            et_lon.setText(Double.toString(curWp.getLocation().getLongitude()));
            et_lat.setText(Double.toString(curWp.getLocation().getLatitude()));
        } else {
            et_title_point.setText("Creating a new waypoint");
        }

        btn_cancel_point.setOnClickListener(view -> {
            Intent i = new Intent(editPoint.this, waypoints.class);
            startActivity(i);
        });

        btn_delete_point.setOnClickListener(view -> {
            if(curWp != null){
                myApplication.getCurrentLocationList().getList().remove(curWp);
            }

            Intent i = new Intent(editPoint.this, waypoints.class);
            startActivity(i);
        });

        btn_save_point.setOnClickListener(view -> {
            if(et_lat.getText().length()*et_lon.getText().length()*et_name_point.getText().length() == 0) return;
            double lat = Double.parseDouble(et_lat.getText().toString());
            double lon = Double.parseDouble(et_lon.getText().toString());
            String ttl = et_name_point.getText().toString();
            Waypoint savedWaypoint;
            if(curWp != null){
                savedWaypoint = curWp;
                savedWaypoint.setTitle(ttl);
                savedWaypoint.setLatLon(lat,lon);
            }else {
                savedWaypoint = new Waypoint(ttl,new Location(ttl));
                myApplication.getCurrentLocationList().add(savedWaypoint);
            }

            if(myApplication.getCurrentLocationList().getList().size()==0) return;
            Intent i = new Intent(editPoint.this, waypoints.class);
            startActivity(i);
        });

    }
}