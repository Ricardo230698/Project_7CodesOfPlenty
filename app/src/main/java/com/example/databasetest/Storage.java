package com.example.databasetest;

import java.util.Date;

public class Storage {
    int _id;
    String _date;
    String _expiration;
    String _name;
    int _servings;
    int _packages;
    String _location;
    String _mealType;
    String _foodGroup;
    int _calories;
    String _servingSize;
    public Storage(){   }
    public Storage(int id, String date, String expiration, String name, int servings, int packages,
                   String location, String mealType, String foodGroup, int calories, String servingSize){
        this._id = id;
        this._date = date;
        this._expiration = expiration;
        this._name = name;
        this._servings = servings;
        this._packages = packages;
        this._location = location;
        this._mealType = mealType;
        this._foodGroup = foodGroup;
        this._calories = calories;
        this._servingSize = servingSize;
    }

    public Storage(String date, String expiration, String name, int servings, int packages,
                   String location, String mealType, String foodGroup, int calories, String servingSize){
        this._date = date;
        this._expiration = expiration;
        this._name = name;
        this._servings = servings;
        this._packages = packages;
        this._location  = location;
        this._mealType = mealType;
        this._foodGroup = foodGroup;
        this._calories = calories;
        this._servingSize = servingSize;
    }
    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }

    public String getDate(){
        return this._date;
    }

    public void setDate(String date){
        this._date = date;
    }

    public String getExpiration() {
        return _expiration;
    }

    public void setExpiration(String expiration) {
        this._expiration = expiration;
    }

    public String getName(){
        return this._name;
    }

    public void setName(String name){
        this._name = name;
    }

    public int getServings() {
        return _servings;
    }

    public void setServings(int servings) {
        this._servings = servings;
    }

    public int getPackages() {
        return _packages;
    }

    public void setPackages(int packages) {
        this._packages = packages;
    }

    public String getLocation () {
        return _location;
    }

    public void setLocation(String location) {
        this._location = location;
    }

    public String getMealType() {
        return _mealType;
    }

    public void setMealType(String mealType) {
        this._mealType = mealType;
    }

    public String getFoodGroup() {
        return _foodGroup;
    }

    public void setFoodGroup(String foodGroup) {
        this._foodGroup = foodGroup;
    }

    public int getCalories() {
        return _calories;
    }

    public void setCalories(int calories) {
        this._calories = calories;
    }

    public String getServingSize() {
        return _servingSize;
    }

    public void setServingSize(String servingSize) {
        this._servingSize = servingSize;
    }
}