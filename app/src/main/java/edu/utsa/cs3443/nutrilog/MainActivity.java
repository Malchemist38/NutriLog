package edu.utsa.cs3443.nutrilog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnCalorieTracker, btnExerciseTracker, btnProgressTracker, btnUpdateGoals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCalorieTracker = findViewById(R.id.btnCalorieTracker);
        btnExerciseTracker = findViewById(R.id.btnExerciseTracker);
        btnProgressTracker = findViewById(R.id.btnProgressTracker);
        btnUpdateGoals = findViewById(R.id.btnUpdateGoals);

        btnCalorieTracker.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CalorieTrackerActivity.class)));
        btnExerciseTracker.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ExerciseTrackerActivity.class)));
        btnProgressTracker.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ProgressTrackerActivity.class)));
        btnUpdateGoals.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, UpdateGoalsActivity.class)));
    }
}
