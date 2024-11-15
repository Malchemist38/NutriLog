package edu.utsa.cs3443.nutrilog;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CalorieTrackerActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private EditText foodName, foodCalories;
    private Button addButton;
    private TextView dailyCalories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_tracker);

        db = new DatabaseHelper(this);
        foodName = findViewById(R.id.foodName);
        foodCalories = findViewById(R.id.foodCalories);
        addButton = findViewById(R.id.addButton);
        dailyCalories = findViewById(R.id.dailyCalories);

        addButton.setOnClickListener(v -> {
            String name = foodName.getText().toString();
            int calories = Integer.parseInt(foodCalories.getText().toString());
            db.addFood(name, calories);
            updateCalorieCount();
        });

        updateCalorieCount();
    }

    private void updateCalorieCount() {
        int totalCalories = db.getTotalCalories();
        dailyCalories.setText("Calories Today: " + totalCalories);
    }
}
