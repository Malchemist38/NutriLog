/**
 * Activity for tracking calories burned through exercise.
 * Allows users to log exercises with calories burned, view total calories burned for the day,
 * and navigate back to the main menu.
 */

package edu.utsa.cs3443.nutrilog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ExerciseTrackerActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private EditText exerciseName, exerciseCalories;
    private Button addExerciseButton;
    private TextView totalBurnedCalories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_tracker);

        db = new DatabaseHelper(this);
        exerciseName = findViewById(R.id.exerciseName);
        exerciseCalories = findViewById(R.id.exerciseCalories);
        addExerciseButton = findViewById(R.id.addExerciseButton);
        totalBurnedCalories = findViewById(R.id.totalBurnedCalories);

        addExerciseButton.setOnClickListener(v -> {
            String name = exerciseName.getText().toString();
            int caloriesBurned = Integer.parseInt(exerciseCalories.getText().toString());
            db.addExercise(name, caloriesBurned);
            updateBurnedCalories();
        });

        Button btnBackToHome = findViewById(R.id.btnBackToHome);

        // Set the click listener
        btnBackToHome.setOnClickListener(v -> {
            // Navigate back to the MainActivity
            Intent intent = new Intent(ExerciseTrackerActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // Close the current activity
        });

        updateBurnedCalories();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateBurnedCalories();
    }

    private void updateBurnedCalories() {
        int totalCaloriesBurned = db.getTotalCaloriesBurned();
        totalBurnedCalories.setText("Calories Burned Today: " + totalCaloriesBurned);
    }
}

