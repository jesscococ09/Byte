package com.example.abyte.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.abyte.database.ByteDatabase;
import com.example.abyte.database.entities.Meal;

import java.util.List;

@Dao
public interface MealDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Meal... meal);

    @Query("Select * from " + ByteDatabase.MEAL_TABLE + " ORDER BY mealName")
    LiveData<List<Meal>> getAllMeals();

    @Query("SELECT * from " + ByteDatabase.MEAL_TABLE + " WHERE mealName == :mealName")
    LiveData<Meal> getMealByName(String mealName);

    @Query("SELECT * from " + ByteDatabase.MEAL_TABLE + " WHERE mealId == :mealId")
    LiveData<Meal> getMealById(int mealId);

    @Query("DELETE from " + ByteDatabase.MEAL_TABLE)
    void deleteAll();

    @Query("DELETE FROM " + ByteDatabase.MEAL_TABLE + " WHERE mealName == :mealName")
    void deleteMealByName(String mealName);

    @Query("DELETE FROM " + ByteDatabase.MEAL_TABLE + " WHERE mealId == :mealId")
    void deleteMealById(int mealId);
}
