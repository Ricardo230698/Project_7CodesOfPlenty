package com.example.databasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class Request_Meals extends AppCompatActivity {

    private Spinner spinner_people;
    private ListView lv_by_people;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_meals);

        spinner_people = findViewById(R.id.spinner_people);
        lv_by_people = findViewById(R.id.lv_by_people);

        //Filling items for number of people
        Integer[] people = {1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapter_people = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, people);
        spinner_people.setAdapter(adapter_people);
    }

    //Button to return
    public void b_return(View v){
        Intent ret = new Intent(this, Data_View.class);
        startActivity(ret);
    }

    //Button to start searching
    public void search(View v){}
}