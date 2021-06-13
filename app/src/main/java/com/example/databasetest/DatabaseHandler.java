package com.example.databasetest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "foodStorage";
    private static final String STORAGE_TABLE = "storage";
    private static final String NAME_TABLE = "foodName";
    private static final String TYPE_TABLE = "foodType";
    private static final String GROUP_TABLE = "foodGroup";
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_EXPIRATION = "expiration";
    private static final String KEY_NAME = "name";
    private static final String KEY_SERVINGS = "servings";
    private static final String KEY_PACKAGES = "packages";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_TYPE = "mealType";
    private static final String KEY_GROUP = "foodGroup";
    private static final String KEY_CALORIES = "calories";
    private static final String KEY_SERVING_SIZE = "servingSize";

    private static final int ID = 0;
    private static final int DATE = 1;
    private static final int EXPIRATION = 2;
    private static final int NAME = 3;
    private static final int SERVINGS = 4;
    private static final int PACKAGES = 5;
    private static final int LOCATION = 6;
    private static final int TYPE = 7;
    private static final int GROUP = 8;
    private static final int CALORIES = 9;
    private static final int SERVING_SIZE = 10;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STORAGE_TABLE = "CREATE TABLE " + STORAGE_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_DATE + " TEXT,"
                + KEY_EXPIRATION + " TEXT,"
                + KEY_NAME + " TEXT,"
                + KEY_SERVINGS + " INTEGER,"
                + KEY_PACKAGES + " INTEGER,"
                + KEY_LOCATION + " LOCATION,"
                + KEY_TYPE + " TEXT,"
                + KEY_GROUP + " TEXT,"
                + KEY_CALORIES + " INTEGER,"
                + KEY_SERVING_SIZE + " TEXT" + ")";
        db.execSQL(CREATE_STORAGE_TABLE);

        String CREATE_NAME_TABLE = "CREATE TABLE " + NAME_TABLE+ "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT" + ")";
        db.execSQL(CREATE_NAME_TABLE);

        String CREATE_TYPE_TABLE = "CREATE TABLE " + TYPE_TABLE+ "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TYPE + " TEXT" + ")";
        db.execSQL(CREATE_TYPE_TABLE);

        String CREATE_GROUP_TABLE = "CREATE TABLE " + GROUP_TABLE+ "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_GROUP + " TEXT" + ")";
        db.execSQL(CREATE_GROUP_TABLE);

        populateTypeTable(db);
        populateGroupTable(db);
        populateNameTable(db);

    }

    public void populateTypeTable(SQLiteDatabase db) {
        List<String> typeList = Arrays.asList("NA", "Side", "Main Dish", "Dessert");
        ContentValues values = new ContentValues();
        for (int i = 0; i < typeList.size(); i++) {
            values.put(KEY_TYPE, typeList.get(i));
            db.insert(TYPE_TABLE, null, values);
        }
    }

    public void populateGroupTable(SQLiteDatabase db) {
        List<String> groupList = Arrays.asList("Vegetable", "Grains/Starch", "Fruit", "Dairy", "Meat/Beans", "Oils/Sweets", "Other");
        ContentValues values = new ContentValues();
        for (int i = 0; i < groupList.size(); i++) {
            values.put(KEY_GROUP, groupList.get(i));
            db.insert(GROUP_TABLE, null, values);
        }
    }

    public void populateNameTable(SQLiteDatabase db) {
        List<String> nameList = Arrays.asList("Apples", "Carrots", "Chicken", "Potatoes", "Green Beans", "Green Peas", "Field Peas", "Lima Beans");
        ContentValues values = new ContentValues();
        for (int i = 0; i < nameList.size(); i++) {
            values.put(KEY_NAME, nameList.get(i));
            db.insert(NAME_TABLE, null, values);
        }
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + STORAGE_TABLE);

        // Create tables again
        onCreate(db);
    }

    // code to add the new storage record
    void addStorage(Storage storage) {
        SQLiteDatabase db = this.getWritableDatabase();

        int typeKeyId = getTypeKeyId(storage.getMealType(), db);
        int groupKeyId = getGroupKeyId(storage.getFoodGroup(), db);
        int nameKeyId  = getNameKeyId(storage.getName(), db);
        long msDate = 0;
        try {
            Date date = sdf.parse(storage.getDate());
            msDate = date.getTime();
            String rDate = sdf.format(msDate);
            Log.d("Format: ", "date = " + rDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, msDate); // Storage Date
        values.put(KEY_EXPIRATION, storage.getExpiration()); // Storage Expiration
        values.put(KEY_NAME, nameKeyId); // Storage Name Key_ID
        values.put(KEY_SERVINGS, storage.getServings()); // Storage Servings
        values.put(KEY_PACKAGES, storage.getPackages()); // Storage Packages
        values.put(KEY_LOCATION, storage.getLocation()); // Storage Location
        values.put(KEY_TYPE, typeKeyId); // Storage MealType Key_ID
        values.put(KEY_GROUP, groupKeyId); // Storage FoodGroup Key_ID
        values.put(KEY_CALORIES, storage.getCalories()); // Storage Calories
        values.put(KEY_SERVING_SIZE, storage.getServingSize()); // Storage ServingSize

        // Inserting Row
        db.insert(STORAGE_TABLE, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get a single storage record
    Storage getStorage(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(STORAGE_TABLE, new String[] { KEY_ID, KEY_DATE,
                        KEY_EXPIRATION, KEY_NAME, KEY_SERVINGS, KEY_PACKAGES, KEY_LOCATION,
                        KEY_TYPE, KEY_GROUP, KEY_CALORIES, KEY_SERVING_SIZE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Date date = new Date(Long.parseLong(cursor.getString(DATE)));
        String storageDate = sdf.format(date);

        Storage storage = new Storage(Integer.parseInt(cursor.getString(ID)),
                storageDate, cursor.getString(EXPIRATION),
                getName(cursor.getString(NAME), db), Integer.parseInt(cursor.getString(SERVINGS)),
                Integer.parseInt(cursor.getString(PACKAGES)), cursor.getString(LOCATION),
                getType(cursor.getString(TYPE), db), getGroup(cursor.getString(GROUP), db),
                Integer.parseInt(cursor.getString(CALORIES)), cursor.getString(SERVING_SIZE));

        cursor.close();
        db.close();

        return storage;
    }

    int getNameKeyId(String name, SQLiteDatabase db) {
        int value = 0;
        Cursor cursor = db.query(NAME_TABLE, new String[] { KEY_ID, KEY_NAME }, KEY_NAME + "=?",
                new String[] { name }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            value = Integer.parseInt(cursor.getString(ID));
            cursor.close();
        }
        return value;
    }

    String getName(String id, SQLiteDatabase db) {
        String value = "";
        Cursor cursor = db.query(NAME_TABLE, new String[] { KEY_ID, KEY_NAME }, KEY_ID + "=?",
                new String[] { id }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            value = cursor.getString(1);
            cursor.close();
        }
        return value;
    }

    List<String> getNameList() {
        List<String> nameList = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + NAME_TABLE;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String value = cursor.getString(1);
                nameList.add(value);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return nameList;
    }

    int getTypeKeyId(String name, SQLiteDatabase db) {
        int value = 0;
        Cursor cursor = db.query(TYPE_TABLE, new String[] { KEY_ID, KEY_TYPE }, KEY_TYPE + "=?",
                new String[] { name }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            value = Integer.parseInt(cursor.getString(ID));
            cursor.close();
        }
        return value;
    }

    String getType(String id, SQLiteDatabase db) {
        String value = "";
        Cursor cursor = db.query(TYPE_TABLE, new String[] { KEY_ID, KEY_TYPE }, KEY_ID + "=?",
                new String[] { id }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            value = cursor.getString(1);
            cursor.close();
        }
        return value;
    }

    List<String> getTypeList() {
        List<String> typeList = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TYPE_TABLE;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String value = cursor.getString(1);
                typeList.add(value);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return typeList;
    }

    int getGroupKeyId(String name, SQLiteDatabase db) {
        int value = 0;
        Cursor cursor = db.query(GROUP_TABLE, new String[] { KEY_ID, KEY_GROUP }, KEY_GROUP + "=?",
                new String[] { name }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            value = Integer.parseInt(cursor.getString(ID));
            cursor.close();
        }
        return value;
    }

    String getGroup(String id, SQLiteDatabase db) {
        String value = "";
        Cursor cursor = db.query(GROUP_TABLE, new String[]{KEY_ID, KEY_GROUP}, KEY_ID + "=?",
                new String[]{id}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            value = cursor.getString(1);
            cursor.close();
        }
        return value;
    }

    List<String> getGroupList() {
        List<String> groupList = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + GROUP_TABLE;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String value = cursor.getString(1);
                groupList.add(value);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return groupList;
    }

    // code to get all storage records in a list
    public List<Storage> getAllStorage() {
        List<Storage> storageList = new ArrayList<Storage>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + STORAGE_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Storage storage = new Storage();
                storage.setID(Integer.parseInt(cursor.getString(ID)));
                Date date = new Date(Long.parseLong(cursor.getString(DATE)));
                //String storageDate = sdf.format(date);
                storage.setDate(sdf.format(date));
                storage.setExpiration(cursor.getString(EXPIRATION));
                //storage.setName(cursor.getString(NAME));
                storage.setName(getName(cursor.getString(NAME), db));
                storage.setServings(Integer.parseInt(cursor.getString(SERVINGS)));
                storage.setPackages(Integer.parseInt(cursor.getString(PACKAGES)));
                storage.setLocation(cursor.getString(LOCATION));
                //storage.setMealType(cursor.getString(TYPE));
                storage.setMealType(getType(cursor.getString(TYPE), db));
                //storage.setFoodGroup(cursor.getString(GROUP));
                storage.setFoodGroup(getGroup(cursor.getString(GROUP), db));
                storage.setCalories(Integer.parseInt(cursor.getString(CALORIES)));
                storage.setServingSize(cursor.getString(SERVING_SIZE));
                // Adding storage to list
                storageList.add(storage);
            } while (cursor.moveToNext());
        }

        // close the query
        db.close();
        cursor.close();

        // return storage list
        return storageList;
    }

    // code to get all storage records between begin and end dates in a list
    public List<Storage> getStorageDates(String startDate, String stopDate) {
        List<Storage> storageList = new ArrayList<Storage>();
        long bt = 0;
        long et = 0;

        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date beginDate = sdf.parse(startDate);
            bt = beginDate.getTime();
            Date endDate = sdf.parse(stopDate);
            et = endDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Select All Query
        String selectQuery = "SELECT  * FROM " + STORAGE_TABLE + " WHERE date BETWEEN " + bt + " AND " + et;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null );

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Storage storage = new Storage();
                storage.setID(Integer.parseInt(cursor.getString(ID)));
                Date date = new Date(Long.parseLong(cursor.getString(DATE)));
                //String storageDate = sdf.format(date);
                storage.setDate(sdf.format(date));
                storage.setExpiration(cursor.getString(EXPIRATION));
                //storage.setName(cursor.getString(NAME));
                storage.setName(getName(cursor.getString(NAME), db));
                storage.setServings(Integer.parseInt(cursor.getString(SERVINGS)));
                storage.setPackages(Integer.parseInt(cursor.getString(PACKAGES)));
                storage.setLocation(cursor.getString(LOCATION));
                //storage.setMealType(cursor.getString(TYPE));
                storage.setMealType(getType(cursor.getString(TYPE), db));
                //storage.setFoodGroup(cursor.getString(GROUP));
                storage.setFoodGroup(getGroup(cursor.getString(GROUP), db));
                storage.setCalories(Integer.parseInt(cursor.getString(CALORIES)));
                storage.setServingSize(cursor.getString(SERVING_SIZE));
                // Adding storage to list
                storageList.add(storage);
            } while (cursor.moveToNext());
        }

        // close the query
        db.close();
        cursor.close();

        // return storage list
        return storageList;
    }

    // code to update the single storage entry
    public int updateStorage(Storage storage) {
        SQLiteDatabase db = this.getWritableDatabase();

        String id = String.valueOf(storage.getID());
        int typeKeyId = getTypeKeyId(storage.getMealType(), db);
        int groupKeyId = getGroupKeyId(storage.getFoodGroup(), db);
        int nameKeyId  = getNameKeyId(storage.getName(), db);
        long msDate = 0;
        try {
            Date date = sdf.parse(storage.getDate());
            msDate = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, msDate); // Storage Date
        values.put(KEY_EXPIRATION, storage.getExpiration()); // Storage Expiration
        values.put(KEY_NAME, nameKeyId); // Storage Name
        values.put(KEY_SERVINGS, storage.getServings()); // Storage Servings
        values.put(KEY_PACKAGES, storage.getPackages()); // Storage Packages
        values.put(KEY_LOCATION, storage.getLocation()); // Storage Location
        values.put(KEY_TYPE, typeKeyId); // Storage MealType
        values.put(KEY_GROUP, groupKeyId); // Storage FoodGroup
        values.put(KEY_CALORIES, storage.getCalories()); // Storage Calories
        values.put(KEY_SERVING_SIZE, storage.getServingSize()); // Storage ServingSize

        // updating row
        int status =  db.update(STORAGE_TABLE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(storage.getID()) });

        return status;
    }

    // Deleting single storage entry
    public void deleteStorage(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(STORAGE_TABLE, KEY_ID + " = ?",
                new String[] { String.valueOf(id)});
        db.close();
    }

    // Getting storage record Count
    public int getStorageCount() {
        String countQuery = "SELECT  * FROM " + STORAGE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        db.close();
        cursor.close();
        return count;
    }

}