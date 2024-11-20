/**
 * Activity for updating user's daily goals.
 * Allows users to set calorie and exercise goals, save them to the database, and navigate back
 * to the main menu.
 */

package edu.utsa.cs3443.nutrilog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateGoalsActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private EditText inputCalorieGoal, inputExerciseGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_goals);

        db = new DatabaseHelper(this);
        inputCalorieGoal = findViewById(R.id.inputCalorieGoal);
        inputExerciseGoal = findViewById(R.id.inputExerciseGoal);

        Button btnSaveGoals = findViewById(R.id.btnSaveGoals);
        Button btnBackToHome = findViewById(R.id.btnBackToHome);

        // Save button logic
        btnSaveGoals.setOnClickListener(v -> {
            String calorieGoalStr = inputCalorieGoal.getText().toString();
            String exerciseGoalStr = inputExerciseGoal.getText().toString();

            if (!calorieGoalStr.isEmpty() && !exerciseGoalStr.isEmpty()) {
                int calorieGoal = Integer.parseInt(calorieGoalStr);
                int exerciseGoal = Integer.parseInt(exerciseGoalStr);

                // Save to database
                boolean success = db.setUserGoals(calorieGoal, exerciseGoal);
                if (success) {
                    Toast.makeText(this, "Goals updated successfully!", Toast.LENGTH_SHORT).show();
                    finish(); // Return to the previous screen
                } else {
                    Toast.makeText(this, "Failed to update goals.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please enter valid values for both goals.", Toast.LENGTH_SHORT).show();
            }
        });

        // Back to Home button logic
        btnBackToHome.setOnClickListener(v -> {
            Intent intent = new Intent(UpdateGoalsActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
