/**
 * Activity for viewing and tracking progress.
 * Displays total calories consumed and burned, and provides a calendar for viewing past data
 * and checking goal conformance.
 */

package edu.utsa.cs3443.nutrilog;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class ProgressTrackerActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private TextView summaryCalories, summaryBurned;
    private CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_tracker);

        db = new DatabaseHelper(this);
        summaryCalories = findViewById(R.id.summaryCalories);
        summaryBurned = findViewById(R.id.summaryBurned);
        calendarView = findViewById(R.id.calendarView);

        Button btnBackToHome = findViewById(R.id.btnBackToHome);
        Button btnClearData = findViewById(R.id.btnClearData);

        btnBackToHome.setOnClickListener(v -> {
            Intent intent = new Intent(ProgressTrackerActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        btnClearData.setOnClickListener(v -> {
            db.clearDatabase();
            Toast.makeText(this, "All data cleared!", Toast.LENGTH_SHORT).show();
            updateSummary(); // Refresh to show cleared data
        });

        updateSummary();

        setupCalendar();
    }

    private void updateSummary() {
        int totalCalories = db.getTotalCalories();
        int totalBurnedCalories = db.getTotalCaloriesBurned();

        summaryCalories.setText("Total Calories Consumed: " + totalCalories);
        summaryBurned.setText("Total Calories Burned: " + totalBurnedCalories);
    }

    private void setupCalendar() {
        HashMap<String, Boolean> goalConformance = db.getGoalConformance();

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String dateKey = String.format(Locale.getDefault(), "%d-%02d-%02d", year, month + 1, dayOfMonth);
            Boolean goalMet = goalConformance.get(dateKey);

            if (goalMet != null) {
                if (goalMet) {
                    summaryCalories.setText("Goal Met on " + dateKey);
                    summaryCalories.setTextColor(Color.GREEN);
                } else {
                    summaryCalories.setText("Goal Missed on " + dateKey);
                    summaryCalories.setTextColor(Color.RED);
                }
            } else {
                summaryCalories.setText("No data for " + dateKey);
                summaryCalories.setTextColor(Color.BLACK);
            }
            loadSummaryForDate(dateKey);
        });
    }
    /*private void setupCalendar() {
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String dateKey = String.format(Locale.getDefault(), "%d-%02d-%02d", year, month + 1, dayOfMonth);
            loadSummaryForDate(dateKey);
        });
    }*/

    private void loadSummaryForDate(String dateKey) {
        if (isToday(dateKey)) {
            updateSummary(); // Reload current day's data
        } else {
            int caloriesConsumed = db.getCaloriesForDate(dateKey);
            int caloriesBurned = db.getCaloriesBurnedForDate(dateKey);

            // Handle cases where no data exists for the selected date
            if (caloriesConsumed == 0 && caloriesBurned == 0) {
                summaryCalories.setText("No data available for " + dateKey);
                summaryBurned.setText("");
            } else {
                summaryCalories.setText("Calories Consumed on " + dateKey + ": " + caloriesConsumed);
                summaryBurned.setText("Calories Burned on " + dateKey + ": " + caloriesBurned);
            }
        }
    }


    private boolean isToday(String dateKey) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date()).equals(dateKey);
    }
}
