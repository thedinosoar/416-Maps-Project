package com.example.gps_maps;

import android.location.Location;

public class Waypoint {
    private String title;
    private Location location;

    Waypoint(){
        title = "null";
        location = new Location("newLocation");
    }

    Waypoint(String t, Location l){
        title = t;
        location = l;
    }



    void setTitle(String ttl){
        title = ttl;
    }
    String getTitle() {return title;};
    Location getLocation() {return location;};
    void setLatLon(double lat, double lon){location.setLatitude(lat); location.setLongitude(lon);}
}
