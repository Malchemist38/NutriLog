package edu.utsa.cs3443.nutrilog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "fitnessTracker.db";
    private static final int DATABASE_VERSION = 2; // Incremented version for the new table

    private static final String TABLE_FOOD = "food";
    private static final String TABLE_EXERCISE = "exercise";
    private static final String TABLE_GOALS = "daily_goals";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE food (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, CALORIES INTEGER, DATE TEXT)");
        db.execSQL("CREATE TABLE exercise (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, CALORIES_BURNED INTEGER, DATE TEXT)");
        db.execSQL("CREATE TABLE daily_goals (DATE TEXT PRIMARY KEY, GOAL INTEGER, ACTUAL INTEGER, GOAL_MET INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE food ADD COLUMN DATE TEXT");
            db.execSQL("ALTER TABLE exercise ADD COLUMN DATE TEXT");
        }
    }


    public boolean addFood(String name, int calories) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME", name);
        values.put("CALORIES", calories);
        values.put("DATE", new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date())); // Add current date
        long result = db.insert("food", null, values);
        return result != -1;
    }


    public boolean addExercise(String name, int caloriesBurned) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME", name);
        values.put("CALORIES_BURNED", caloriesBurned);
        values.put("DATE", new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date())); // Add current date
        long result = db.insert("exercise", null, values);
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

    public boolean setUserGoals(int calorieGoal, int exerciseGoal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("CALORIE_GOAL", calorieGoal);
        values.put("EXERCISE_GOAL", exerciseGoal);

        long result = db.insertWithOnConflict("user_goals", null, values, SQLiteDatabase.CONFLICT_REPLACE);
        return result != -1;
    }

    public int getCaloriesForDate(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(CALORIES) FROM food WHERE DATE = ?", new String[]{date});
        int totalCalories = 0;
        if (cursor != null && cursor.moveToFirst()) {
            totalCalories = cursor.getInt(0);
            cursor.close();
        }
        return totalCalories; // Return 0 if no data is found
    }

    public int getCaloriesBurnedForDate(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(CALORIES_BURNED) FROM exercise WHERE DATE = ?", new String[]{date});
        int totalCaloriesBurned = 0;
        if (cursor != null && cursor.moveToFirst()) {
            totalCaloriesBurned = cursor.getInt(0);
            cursor.close();
        }
        return totalCaloriesBurned; // Return 0 if no data is found
    }



    public int[] getUserGoals() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT CALORIE_GOAL, EXERCISE_GOAL FROM user_goals ORDER BY ID DESC LIMIT 1", null);

        int[] goals = new int[2];
        if (cursor.moveToFirst()) {
            goals[0] = cursor.getInt(0); // Calorie Goal
            goals[1] = cursor.getInt(1); // Exercise Goal
        }
        cursor.close();
        return goals;
    }

    public HashMap<String, Boolean> getGoalConformance() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DATE, GOAL_MET FROM " + TABLE_GOALS, null);

        HashMap<String, Boolean> goalConformance = new HashMap<>();
        while (cursor.moveToNext()) {
            String date = cursor.getString(0);
            boolean goalMet = cursor.getInt(1) == 1;
            goalConformance.put(date, goalMet);
        }
        cursor.close();
        return goalConformance;
    }

    public void clearDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_FOOD);
        db.execSQL("DELETE FROM " + TABLE_EXERCISE);
        db.execSQL("DELETE FROM " + TABLE_GOALS);
    }

}
