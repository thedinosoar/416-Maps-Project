package com.example.gps_maps;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;

public class LocationList {
    private String title;
    private List<Waypoint> waypoints;

    LocationList(String ttl){
        title = ttl;
        waypoints = new ArrayList<Waypoint>();
    }

    void setTitle(String ttl){
        title = ttl;
    }
    String getTitle() {return title;};
    List<Waypoint> getList() {return waypoints;};
    void add(Waypoint waypoint){waypoints.add(waypoint);};

    List<Location> getLocations(){
        List<Location> locations = new ArrayList<>();
        for(Waypoint wp: waypoints){
            locations.add(wp.getLocation());
        }
        return locations;
    }
}
