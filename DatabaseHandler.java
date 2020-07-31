package com.example.sensormenuapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SensorApplicationDatabase";
    private static final String TABLE_ACCELEROMETER = "accelerometer_Data";
    private static final String TABLE_GYROSCOPE = "gyroscope_Data";

    private static final String KEY_ID = "id";
    private static final String KEY_VALUE1 = "value1";
    private static final String KEY_VALUE2 = "value2";
    private static final String KEY_VALUE3 = "value3";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SENSORS_TABLE = "CREATE TABLE " + TABLE_ACCELEROMETER + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_VALUE1 + " TEXT,"
                + KEY_VALUE2 + " TEXT," + KEY_VALUE3 + " TEXT )";
        String CREATE_SENSORS2_TABLE = "CREATE TABLE " + TABLE_GYROSCOPE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_VALUE1 + " TEXT,"
                + KEY_VALUE2 + " TEXT," + KEY_VALUE3 + " TEXT )";

        db.execSQL(CREATE_SENSORS_TABLE);
        db.execSQL(CREATE_SENSORS2_TABLE);
    }

    void addAccelerometer(Accelerometer accelerometer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_VALUE1, accelerometer.getValue1()); // Contact Name
        values.put(KEY_VALUE2, accelerometer.getValue2()); // Contact Phone
        values.put(KEY_VALUE3, accelerometer.getValue3()); // Contact Phone


        // Inserting Row
        db.insert(TABLE_ACCELEROMETER, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    void addGyroscope(Gyroscope gyroscope) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_VALUE1, gyroscope.getValue1()); // Contact Name
        values.put(KEY_VALUE2, gyroscope.getValue2()); // Contact Phone
        values.put(KEY_VALUE3, gyroscope.getValue3()); // Contact Phone


        // Inserting Row
        db.insert(TABLE_GYROSCOPE, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    Accelerometer getAcclerometer(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ACCELEROMETER, new String[]{KEY_ID,
                        KEY_VALUE1, KEY_VALUE2, KEY_VALUE3}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Accelerometer accelerometer = new Accelerometer(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));
        // return Task
        return accelerometer;
    }

    Gyroscope getGyroscope(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_GYROSCOPE, new String[]{KEY_ID,
                        KEY_VALUE1, KEY_VALUE2, KEY_VALUE3}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Gyroscope gyroscope = new Gyroscope(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));
        // return Task
        return gyroscope;
    }

    // code to get all contacts in a list view
    public List<Accelerometer> getAllAccelerometers() {
        List<Accelerometer> taskList = new ArrayList<Accelerometer>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ACCELEROMETER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Accelerometer task = new Accelerometer();
                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setValue1(cursor.getString(1));
                task.setValue2(cursor.getString(2));
                task.setValue3(cursor.getString(3));
                // Adding contact to list
                taskList.add(task);
            } while (cursor.moveToNext());
        }

        // return contact list
        return taskList;
    }

    public List<Gyroscope> getAllGyroscopes() {
        List<Gyroscope> taskList = new ArrayList<Gyroscope>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_GYROSCOPE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Gyroscope task = new Gyroscope();
                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setValue1(cursor.getString(1));
                task.setValue2(cursor.getString(2));
                task.setValue3(cursor.getString(3));
                // Adding contact to list
                taskList.add(task);
            } while (cursor.moveToNext());
        }

        // return contact list
        return taskList;
    }
    public  void deleteAccelerometers(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_ACCELEROMETER,"1=1",null);
        db.close();
    }
    public  void deleteGyroscopes(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_GYROSCOPE,"1=1",null);
        db.close();
    }
//    public void deleteContact(Task task) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
//                new String[] { String.valueOf(task.getId()) });
//        db.close();
//    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
//    public void updateTask(Task t){
//        ContentValues values = new ContentValues();
//        values.put(KEY_TITLE,t.getTitle());
//        values.put(KEY_DESCRIPTION,t.getDescription());
//        values.put(KEY_DATE,t.getDate());
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.update(TABLE_CONTACTS,values, KEY_ID+" ="+t.getId(),null);
//        db.close();
//    }
//}
