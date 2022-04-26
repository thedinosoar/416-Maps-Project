package com.example.gps_maps;

import android.app.Application;
import android.location.Location;

import java.util.ArrayList;
import java.util.List;


public class MyApplication extends  Application{

    private static MyApplication singleton;
    private List<Location> myLocations;
    private List<LocationList> myList;
    private LocationList currentLocationList;
    private Waypoint currentWaypoint;

    // Getters and setters
    public List<Location> getMyLocations() {
        return myLocations;
    }

    public MyApplication getInstance()
    {
        return singleton;
    }

    public LocationList getCurrentLocationList() {return currentLocationList;};
    public void setCurrentLocationList(LocationList current) {singleton.currentLocationList = current;};

    public Waypoint getCurrentWaypoint() {return currentWaypoint;};
    public void setCurrentWaypoint(Waypoint current) {singleton.currentWaypoint = current;};

    public List<LocationList> getMyList() {return myList;};


    public void onCreate()
    {
        super.onCreate();
        singleton = this;
        myLocations = new ArrayList<>();
        myList = new ArrayList<>();
        currentLocationList = new LocationList("Empty");
        currentWaypoint = new Waypoint();
    }
}
