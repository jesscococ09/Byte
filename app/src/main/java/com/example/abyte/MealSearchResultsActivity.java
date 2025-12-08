package com.example.abyte;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abyte.database.daos.MealDAO;
import com.example.abyte.database.entities.Meal;
import com.example.abyte.database.repositories.MealRepository;
import com.example.abyte.databinding.ActivityMealsSearchResultsBinding;

import java.util.ArrayList;
import java.util.List;

public class MealSearchResultsActivity extends AppCompatActivity {
    private ActivityMealsSearchResultsBinding binding;
//    private MealRepositoryNOTUSE repository; //TODO: Implement Repository



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        ActivityMealsSearchResultsBinding binding;
        MealRepository repository;
        MEAL_RecyclerViewAdapter adapter;
        ArrayList<Meal> mealList;

        super.onCreate(savedInstanceState);
        binding = ActivityMealsSearchResultsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mealList = new ArrayList<>();
        repository = MealRepository.getRepository(getApplication());
        RecyclerView recyclerView = binding.mealSearchRecyclerView;

        Intent intent = getIntent();
        String ingredient = intent.getStringExtra("ingredient");

        adapter = new MEAL_RecyclerViewAdapter(this, mealList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (ingredient != null && !ingredient.isEmpty()) {
            // This is the correct way to get data from LiveData
            repository.getMealsByIngredient(ingredient).observe(this, new Observer<List<Meal>>() {
                @Override
                public void onChanged(List<Meal> newMeals) {
                    // This code runs when the database query is complete.
                    if (newMeals != null) {
                        mealList.clear(); // Clear old results
                        mealList.addAll(newMeals); // Add the new results
                        adapter.notifyDataSetChanged(); // Tell the adapter to refresh the UI

                        if (newMeals.isEmpty()) {
                            Toast.makeText(MealSearchResultsActivity.this, "No meals found", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        } else {
            Toast.makeText(this, "No ingredient specified", Toast.LENGTH_SHORT).show();
        }


        binding.mealSearchResultsBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MealSearchResultsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}
