package com.example.databasetest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHandler db = new DatabaseHandler(this);

        // Inserting Storage Records
        Log.d("Insert: ", "Inserting ..");
        //db.addStorage(new Storage("2021-05-20", "18MO", "Carrots", 24, 6, "Rack:1, Shelf:A", "Side", "Vegetable", 70, "1/2cup"));

        db.addStorage(new Storage("2021-05-20", "18MO", "Carrots", 24, 6, "Rack:1, Shelf:A", "Side", "Vegetable", 70, "1/2cup"));
        db.addStorage(new Storage("2021-05-19", "12MO", "Potatoes", 24, 6, "Rack:1, Shelf:B", "Side", "Grains/Starch", 90, "1/2cup"));
        db.addStorage(new Storage("2021-05-18", "6MO", "Chicken", 12, 6, "Rack:2, Shelf:A", "Main Dish", "Meat/Beans", 120, "6oz"));
        db.addStorage(new Storage("2021-05-17", "6MO", "Apples", 30, 5, "Rack:2, Shelf:B", "Dessert", "Fruit", 100, "1/2cup"));
        db.addStorage(new Storage("2021-06-20", "18MO", "Carrots", 24, 6, "Rack:1, Shelf:A", "Side", "Vegetable", 70, "1/2cup"));
        db.addStorage(new Storage("2021-06-19", "12MO", "Potatoes", 24, 6, "Rack:1, Shelf:B", "Side", "Grains/Starch", 90, "1/2cup"));
        db.addStorage(new Storage("2021-06-18", "6MO", "Chicken", 12, 6, "Rack:2, Shelf:A", "Main Dish", "Meat/Beans", 120, "6oz"));
        db.addStorage(new Storage("2021-06-17", "6MO", "Apples", 30, 5, "Rack:2, Shelf:B", "Dessert", "Fruit", 100, "1/2cup"));


        // get list method tests
        List<String> groupList = db.getGroupList();
        Collections.sort(groupList);
        List<String> typeList = db.getTypeList();
        Collections.sort(typeList);
        List<String> nameList = db.getNameList();
        Collections.sort(nameList);

        // get count of records
        int count1 = db.getStorageCount();
        // delete record test
        db.deleteStorage(2);
        // get count of records
        int count2 = db.getStorageCount();

        // get a storage record
        Storage storage2 = db.getStorage(3);
        // reduce the number of packages in the record
        storage2.setPackages(5);
        // Save the record
        db.updateStorage(storage2);
        Storage storage3 = db.getStorage(3);

        // Get Storage Records between dates
        String startDate = "2021-05-20";
        String stopDate = "2021-06-19";
        List<Storage> storageBetweenList = db.getStorageDates(startDate, stopDate);




        // Reading all Storage Records
        Log.d("Reading: ", "Reading all storage information..");
        List<Storage> storageList = db.getAllStorage();

        for (Storage sl : storageList) {
            String log = "Id: " + sl.getID() + " ,Date: " + sl.getDate() + " ,Expiration: " + sl.getExpiration() +
                    " ,Name: " + sl.getName() + " ,Servings: " + sl.getServings() + " ,Packages: " + sl.getPackages() +
                    " ,Location: " + sl.getLocation() +  " ,Meal Type: " + sl.getMealType() +  " ,Food Group: " + sl.getFoodGroup() +
                    " ,Calories: " + sl.getCalories() + " ,Serving Size: " +sl.getServingSize();
            // Writing storage information to the log
            Log.d("Storage:", log);
        }

        db.close();
    }

    //Creating new activity to entry new data
    public void data_entry(View v){
        Intent entry_data = new Intent(this, Data_Entry.class);
        startActivity(entry_data);
    }

    //Creating new activity to view data
    public void data_view(View v){
        Intent view_data = new Intent(this, Data_View.class);
        startActivity(view_data);
    }

    //Creating new activity to edit data
    public void data_edit(View v){
        Intent edit_data = new Intent(this, Data_Edit.class);
        startActivity(edit_data);
    }
}