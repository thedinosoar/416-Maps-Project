package com.example.gps_maps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class lists extends AppCompatActivity {

    Button btn_locations, btn_back, btn_addlist;

    ListView lv_lists;
    TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

        btn_locations = findViewById(R.id.btn_locations);
        btn_back = findViewById(R.id.btn_back);
        btn_addlist = findViewById(R.id.btn_addlist);

        lv_lists = (ListView) findViewById(R.id.lv_lists);
        tv_title = findViewById(R.id.tv_lists);

        MyApplication myApplication = (MyApplication)getApplicationContext();
        List<LocationList> savedLists = myApplication.getMyList();
        List<String> listNames = new ArrayList<>();
        for(LocationList ll:myApplication.getMyList()) listNames.add(ll.getTitle());


        if(savedLists.size() == 0) myApplication.setCurrentLocationList(null);

        lv_lists.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listNames));

        lv_lists.setOnItemClickListener((adapter, view, i, l) -> {

            if(myApplication.getCurrentLocationList()!=null&&myApplication.getCurrentLocationList().getTitle() == lv_lists.getItemAtPosition(i)){
                Intent v = new Intent(lists.this, editList.class);
                startActivity(v);
            }

            myApplication.setCurrentLocationList(myApplication.getMyList().get(i));
//                adapter.dismiss();
        });

        btn_back.setOnClickListener(view -> {
            Intent i = new Intent(lists.this, MainActivity.class);
            startActivity(i);
        });

        btn_locations.setOnClickListener(view -> {
            if(myApplication.getCurrentLocationList() != null){
                Intent i = new Intent(lists.this, waypoints.class);
                startActivity(i);
            }
        });

        btn_addlist.setOnClickListener(view -> {
            myApplication.setCurrentLocationList(null);
            Intent i = new Intent(lists.this, editList.class);
            startActivity(i);
        });
    }
}