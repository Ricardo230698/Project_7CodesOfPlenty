package com.example.databasetest;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.Calendar;

public class Data_Entry extends AppCompatActivity implements View.OnClickListener{

    private int day, month, year;
    private EditText et_name, et_date;
    private Spinner spinner_expiration, spinner_food, spinner_servings, spinner_packages, spinner_meal_type, spinner_food_group;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);

        et_date = findViewById(R.id.et_date);
        et_date.setOnClickListener(this);
        et_name = findViewById(R.id.et_name);
        spinner_food = findViewById(R.id.spinner_food);
        spinner_expiration = findViewById(R.id.spinner_expiration);
        spinner_servings = findViewById(R.id.spinner_servings);
        spinner_packages = findViewById(R.id.spinner_packages);
        spinner_meal_type = findViewById(R.id.spinner_meal_type);
        spinner_food_group = findViewById(R.id.spinner_food_group);

        //Filling items for dates of expiration
        String[] expiration = {"6-months", "7-months", "8-months", "9-months", "10-months", "11-months", "12-months", "13-months", "14-months",
                "15-months", "16-months", "17-months", "18-months", "19-months", "20-months", "21-months", "22-months", "23-months", "24-months"};
        ArrayAdapter <String> adapter_expiration = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, expiration);
        spinner_expiration.setAdapter(adapter_expiration);

        //Filling items for food already saved
        String[] food = {"Carrots", "Potatoes", "Chicken", "Apples"};
        ArrayAdapter <String> adapter_food = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, food);
        spinner_food.setAdapter(adapter_food);

        //Filling items for number of servings
        Integer[] servings = {1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter <Integer> adapter_servings = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, servings);
        spinner_servings.setAdapter(adapter_servings);

        //Filling items for number of packages
        Integer[] packages = {1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter <Integer> adapter_packages = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, packages);
        spinner_packages.setAdapter(adapter_packages);

        //Filling items for types of meal
        String[] meal_type = {"N/A", "Main Dish", "Side Dish", "Dessert"};
        ArrayAdapter <String> adapter_meal_type = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, meal_type);
        spinner_meal_type.setAdapter(adapter_meal_type);

        //Filling items for groups of food
        String[] food_group = {"Vegetable, Grains", "Starch, Fruit, Dairy, Meat", "Beans, Oils", "Sweets"};
        ArrayAdapter <String> adapter_food_group = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, food_group);
        spinner_food_group.setAdapter(adapter_food_group);
    }

    //Button to save the entry data
    public void save_data(View v){
        //Depending on whether the user wants to enter a new meal in the history, the correct option will be selected
        String food = "";
        if(et_name.getVisibility() == View.VISIBLE){
            food = et_name.getText().toString();
        } else {
            food = spinner_food.getSelectedItem().toString();
        }

        Toast.makeText(this,"Date: " + et_date.getText().toString() + "\nExpiration: " +
                spinner_expiration.getSelectedItem().toString() +
                "\nName of the food: " + food + "\nServings: " + spinner_servings.getSelectedItem().toString() +
                "\nPackages: " + spinner_packages.getSelectedItem().toString() +
                "\nMeal Type: " + spinner_meal_type.getSelectedItem().toString() + "\nFood Group: " +
                spinner_food_group.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    //Methods to either edit spinner entry or select another box for manual edit.
    //-----------------------------------
    public void exist(View v){
        //et_name.setEnabled(false);
        //spinner_food.setEnabled(true);
        spinner_food.setVisibility(View.VISIBLE);
        et_name.setVisibility(View.GONE);
        spinner_food.requestFocus();
    }

    //-----------------------------------
    public void try_new(View v){
        //spinner_food.setEnabled(false);
        //et_name.setEnabled(true);
        spinner_food.setVisibility(View.GONE);
        et_name.setVisibility(View.VISIBLE);
        et_name.requestFocus();
    }

    //Button to return
    public void b_return(View v){
        Intent ret = new Intent(this, MainActivity.class);
        startActivity(ret);
    }


    //Date Picker
    @Override
    public void onClick(View v) {
        if (v==et_date){
            final Calendar c = Calendar.getInstance();
            day = c.get(Calendar.DAY_OF_MONTH);
            month = c.get(Calendar.MONTH);
            year = c.get(Calendar.YEAR);

            DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    et_date.setText(dayOfMonth+"/"+(month + 1)+"/"+year);
                }
            }, day, month, year);
            datePicker.show();
        }
    }
}