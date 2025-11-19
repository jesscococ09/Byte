package com.example.abyte.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
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

    @Delete
    void delete(Meal meal);

    @Query("SELECT * FROM " + ByteDatabase.MEAL_TABLE + " ORDER BY mealId")
    LiveData<List<Meal>> getAllMeals();

    @Query("DELETE FROM " + ByteDatabase.MEAL_TABLE + " ORDER BY mealName")
    void deleteAll();

    @Query("SELECT * FROM " + ByteDatabase.MEAL_TABLE + " WHERE mealName == :mealName")
    LiveData<Meal> getMealByName(String mealName);

    @Query("SELECT * FROM " + ByteDatabase.MEAL_TABLE + " WHERE mealId == :mealId")
    LiveData<Meal> getMealById(int mealId);

}
