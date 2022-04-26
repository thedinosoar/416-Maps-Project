package com.example.gps_maps;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class waypoints extends AppCompatActivity {

    Button btn_map, btn_lists, btn_add_waypoint;

    ListView lv_waypoints;
    TextView tv_title_waypoint;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waypoints);

        btn_map = findViewById(R.id.btn_map);
        btn_lists = findViewById(R.id.btn_lists);
        btn_add_waypoint = findViewById(R.id.btn_add_waypoint);
        lv_waypoints = (ListView) findViewById(R.id.lv_waypoints);
        tv_title_waypoint = findViewById(R.id.tv_title_waypoint);
        MyApplication myApplication = (MyApplication)getApplicationContext();


        if(myApplication.getCurrentLocationList() != null){
            tv_title_waypoint.setText("'"+myApplication.getCurrentLocationList().getTitle()+"' Waypoints");
        }


        assert myApplication.getCurrentLocationList() != null;
        List<Location> savedLists = myApplication.getCurrentLocationList().getLocations();

        List<String> waypointNames = new ArrayList<>();
        for(Waypoint w:myApplication.getCurrentLocationList().getList()) waypointNames.add(w.getTitle());

        // Fills the waypoints up
        lv_waypoints.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, waypointNames));



        lv_waypoints.setOnItemClickListener((adapter, view, i, l) -> {

            if(myApplication.getCurrentWaypoint()!=null&&myApplication.getCurrentWaypoint().getTitle() == lv_waypoints.getItemAtPosition(i)){
                Intent v = new Intent(waypoints.this, editPoint.class);
                startActivity(v);
            }
                //TODO fix selecting waypoints
//            myApplication.setCurrentWaypoint((Waypoint) lv_waypoints.getItemAtPosition(i));
            myApplication.setCurrentWaypoint(myApplication.getCurrentLocationList().getList().get(i));
//                adapter.dismiss();
        });

        btn_add_waypoint.setOnClickListener(view -> {
            myApplication.setCurrentWaypoint(null);
            Intent i = new Intent(waypoints.this, editPoint.class);
            startActivity(i);
        });

        btn_lists.setOnClickListener(view -> {
            myApplication.setCurrentWaypoint(null);
            Intent i = new Intent(waypoints.this, lists.class);
            startActivity(i);
        });

        btn_map.setOnClickListener(view -> {
            if(myApplication.getCurrentLocationList().getLocations().size()<1) return;
            Intent i = new Intent(waypoints.this, MapsActivity.class);
            startActivity(i);
        });
    }
}
