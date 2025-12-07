package com.example.abyte;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abyte.database.entities.Meal;
import com.example.abyte.databinding.ActivityMealsSearchBinding;

import java.util.ArrayList;

public class MealFavoritesActivity extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_meals_favorites);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//    }


    ArrayList<Meal> mealList = new ArrayList<>();

    int[] mealImages = {
            R.drawable.spaghetti,
            R.drawable.cat,
            R.drawable.spilled
    };

    private ActivityMealsSearchBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMealsSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RecyclerView recyclerView = binding.mealSearchRecyclerView;

        setUpMealList();

        MEAL_RecyclerViewAdapter adapter = new MEAL_RecyclerViewAdapter(this, mealList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    //TODO: Implement way to retrieve info for favorite meals
    private void setUpMealList(){
        mealList.add(new Meal(2, "Pick it up Quickly", "WIP", R.drawable.spilled));
        mealList.add(new Meal(1, "Eat Me...", "WIP", R.drawable.cat));

    }
}