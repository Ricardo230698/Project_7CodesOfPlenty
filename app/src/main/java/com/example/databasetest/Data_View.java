package com.example.databasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Data_View extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_view);
    }

    //Button to search by name
    public void by_name(View v){
        Intent intent = new Intent(this, Storage_by_Name.class);
        startActivity(intent);
    }

    //Button to search by date
    public void by_date(View v){
        Intent intent = new Intent(this, Storage_by_Date.class);
        startActivity(intent);
    }

    //Button to search by Expiration
    public void by_expiration(View v){
        Intent intent = new Intent(this, Storage_by_Expiration.class);
        startActivity(intent);
    }

    //Button to request number of meals
    public void request_meals(View v){
        Intent intent = new Intent(this, Request_Meals.class);
        startActivity(intent);
    }

    //Button to return
    public void b_return(View v){
        Intent ret = new Intent(this, MainActivity.class);
        startActivity(ret);
    }
}