package com.example.gps_maps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class editList extends AppCompatActivity {

    Button btn_cancel_list, btn_delete_list, btn_save_list;
    EditText et_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list);

        btn_cancel_list = findViewById(R.id.btn_cancel_list);
        btn_delete_list = findViewById(R.id.btn_delete_list);
        btn_save_list = findViewById(R.id.btn_save_list);
        et_name = findViewById(R.id.et_title_list);
        MyApplication myApplication = (MyApplication)getApplicationContext();

        if(myApplication.getCurrentLocationList() != null){
            et_name.setText(myApplication.getCurrentLocationList().getTitle());
        }

        btn_cancel_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(editList.this, lists.class);
                startActivity(i);
            }
        });

        btn_delete_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myApplication.getCurrentLocationList() != null){
                    myApplication.getMyList().remove(myApplication.getCurrentLocationList());
                }
                Intent i = new Intent(editList.this, lists.class);
                startActivity(i);
            }
        });

        btn_save_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_name.getText().toString().length() > 0) {
                    if(myApplication.getCurrentLocationList() != null){
                        myApplication.getCurrentLocationList().setTitle(et_name.getText().toString());
                    } else{
                        LocationList locationList;
                        locationList = new LocationList(et_name.getText().toString());
                        myApplication.getMyList().add(locationList);
                    }
                }
                Intent i = new Intent(editList.this, lists.class);
                startActivity(i);
            }
        });
    }
}