package com.example.abyte.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.abyte.database.ByteDatabase;
import com.example.abyte.database.entities.Meal;
import com.example.abyte.database.entities.User;

import java.util.List;

@Dao
public interface MealDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Meal... meal);

    @Delete
    void delete(Meal meal);

    @Query("Select * from " + ByteDatabase.MEAL_TABLE + " ORDER BY mealName")
    LiveData<List<Meal>> getAllMeals();

    @Query("SELECT * from " + ByteDatabase.MEAL_TABLE + " WHERE mealName == :mealName")
    LiveData<Meal> getMealByName(String mealName);

    @Query("SELECT * from " + ByteDatabase.MEAL_TABLE + " WHERE mealId == :mealId")
    LiveData<Meal> getMealById(int mealId);

    @Query("DELETE from " + ByteDatabase.MEAL_TABLE)
    void deleteAll();

    @Query("DELETE from " + ByteDatabase.MEAL_TABLE + " WHERE mealName == :mealName")
    void deleteMealByName(String mealName);


    @Query("DELETE FROM " + ByteDatabase.MEAL_TABLE + " WHERE mealId == :mealId")
    void deleteMealById(int mealId);

    @Query("SELECT * FROM " + ByteDatabase.MEAL_TABLE + " WHERE ingredient1 == :ingredient OR " +
            "ingredient2 == :ingredient OR " +
            "ingredient3 == :ingredient OR " +
            "ingredient4 == :ingredient OR " +
            "ingredient5 == :ingredient OR " +
            "ingredient6 == :ingredient OR " +
            "ingredient7 == :ingredient OR " +
            "ingredient8 == :ingredient OR " +
            "ingredient9 == :ingredient OR " +
            "ingredient10 == :ingredient")
    LiveData<List<Meal>> getMealsByIngredient(String ingredient);


}
