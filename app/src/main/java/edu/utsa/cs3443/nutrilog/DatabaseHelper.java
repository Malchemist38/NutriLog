package edu.utsa.cs3443.nutrilog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "fitnessTracker.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_FOOD = "food";
    private static final String TABLE_EXERCISE = "exercise";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_FOOD + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, CALORIES INTEGER)");
        db.execSQL("CREATE TABLE " + TABLE_EXERCISE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, CALORIES_BURNED INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISE);
        onCreate(db);
    }

    public boolean addFood(String name, int calories) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME", name);
        values.put("CALORIES", calories);
        long result = db.insert(TABLE_FOOD, null, values);
        return result != -1;
    }

    public boolean addExercise(String name, int caloriesBurned) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME", name);
        values.put("CALORIES_BURNED", caloriesBurned);
        long result = db.insert(TABLE_EXERCISE, null, values);
        return result != -1;
    }

    public int getTotalCalories() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(CALORIES) FROM " + TABLE_FOOD, null);
        int totalCalories = 0;
        if (cursor.moveToFirst()) {
            totalCalories = cursor.getInt(0);
        }
        cursor.close();
        return totalCalories;
    }

    public int getTotalCaloriesBurned() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(CALORIES_BURNED) FROM " + TABLE_EXERCISE, null);
        int totalCaloriesBurned = 0;
        if (cursor.moveToFirst()) {
            totalCaloriesBurned = cursor.getInt(0);
        }
        cursor.close();
        return totalCaloriesBurned;
    }
}
