package com.example.databasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class Storage_by_Expiration extends AppCompatActivity implements View.OnClickListener {

    EditText initial_date, final_date;
    private int initial_day, initial_month, initial_year, final_day, final_month, final_year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_by_expiration);
        initial_date = findViewById(R.id.et_initial_date);
        final_date = findViewById(R.id.et_final_date);
        initial_date.setOnClickListener(this);
        final_date.setOnClickListener(this);
    }

    //Button to return
    public void b_return(View v){
        Intent ret = new Intent(this, Data_View.class);
        startActivity(ret);
    }

    //Button to start searching
    public void search(View v){}


    //Date Pickers for initial and expiration dates
    @Override
    public void onClick(View v) {
        if (v==initial_date){
            final Calendar c = Calendar.getInstance();
            initial_day = c.get(Calendar.DAY_OF_MONTH);
            initial_month = c.get(Calendar.MONTH);
            initial_year = c.get(Calendar.YEAR);

            DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    initial_date.setText(dayOfMonth+"/"+(month + 1)+"/"+year);
                }
            }, initial_day, initial_month, initial_year);
            datePicker.show();
        }
        if (v==final_date){
            final Calendar c = Calendar.getInstance();
            final_day = c.get(Calendar.DAY_OF_MONTH);
            final_month = c.get(Calendar.MONTH);
            final_year = c.get(Calendar.YEAR);

            DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    final_date.setText(dayOfMonth+"/"+(month + 1)+"/"+year);
                }
            }, final_day, final_month, final_year);
            datePicker.show();
        }
    }
}