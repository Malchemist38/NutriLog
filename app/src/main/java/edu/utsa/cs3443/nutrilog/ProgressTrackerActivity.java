package edu.utsa.cs3443.nutrilog;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProgressTrackerActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private TextView summaryCalories, summaryBurned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_tracker);

        db = new DatabaseHelper(this);
        summaryCalories = findViewById(R.id.summaryCalories);
        summaryBurned = findViewById(R.id.summaryBurned);

        updateSummary();
    }

    private void updateSummary() {
        int totalCalories = db.getTotalCalories();
        int totalBurnedCalories = db.getTotalCaloriesBurned();

        summaryCalories.setText("Total Calories Consumed: " + totalCalories);
        summaryBurned.setText("Total Calories Burned: " + totalBurnedCalories);
    }
}
