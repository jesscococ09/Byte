package com.example.abyte;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abyte.database.entities.Meal;
import com.example.abyte.database.repositories.MealRepository;
import com.example.abyte.databinding.ActivityMealsSearchBinding;

import java.util.ArrayList;
import java.util.List;

public class MealSearchActivity extends AppCompatActivity {
    ArrayList<Meal> mealList = new ArrayList<>();
    private ActivityMealsSearchBinding binding;
//    private MealRepository repository; //TODO: Implement Repository

    int[] mealImages = {
            R.drawable.spaghetti,
            R.drawable.cat,
            R.drawable.spilled
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMealsSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        repository = MealRepository.getRepository(getApplication()); //TODO: Implement Repository

        RecyclerView recyclerView = binding.mealSearchRecyclerView;

        setUpMealList();

        MEAL_RecyclerViewAdapter adapter = new MEAL_RecyclerViewAdapter(this, mealList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    //TODO: Implement way to retrieve info from database
    private void setUpMealList(){
        mealList.add(ExampleMeals.spaghettiWithSauce);
        mealList.add(ExampleMeals.porkchop);
        mealList.add(ExampleMeals.joke1);
        mealList.add(ExampleMeals.joke2);
    }
}
