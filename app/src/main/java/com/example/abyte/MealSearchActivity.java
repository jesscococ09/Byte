package com.example.abyte;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.abyte.databinding.ActivityMealSearchBinding;
import com.example.abyte.databinding.ActivityMealsSearchResultsBinding;

public class MealSearchActivity extends AppCompatActivity {

    ActivityMealSearchBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);

        super.onCreate(savedInstanceState);
        binding = ActivityMealSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView ingredientEditText = binding.ingredientEditText;

        binding.searchBackButton.setOnClickListener(view -> {
            Intent intent = new Intent(MealSearchActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        binding.searchButton.setOnClickListener(view -> {
            Intent intent = new Intent(MealSearchActivity.this, MealSearchResultsActivity.class);
            String ingredient = ingredientEditText.getText().toString();
            String placeholder = getString(R.string.ingredient_edittext);
            if (ingredient.equals(placeholder) || ingredient.isEmpty()) {
                Toast.makeText(this, "Please enter an ingredient", Toast.LENGTH_SHORT).show();
            } else {
                intent.putExtra("ingredient", ingredient);
                startActivity(intent);
            }
        });
    }
}