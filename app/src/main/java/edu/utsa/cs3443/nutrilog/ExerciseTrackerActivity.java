package edu.utsa.cs3443.nutrilog;

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

        updateBurnedCalories();
    }

    private void updateBurnedCalories() {
        int totalCaloriesBurned = db.getTotalCaloriesBurned();
        totalBurnedCalories.setText("Calories Burned Today: " + totalCaloriesBurned);
    }
}

