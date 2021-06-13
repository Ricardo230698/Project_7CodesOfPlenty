package com.example.databasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Storage_by_Name extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_by_name);
    }

    //Button to return
    public void b_return(View v){
        Intent ret = new Intent(this, Data_View.class);
        startActivity(ret);
    }

    //Button to start searching
    public void search(View v){}
}