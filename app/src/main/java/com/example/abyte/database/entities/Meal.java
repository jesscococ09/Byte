package com.example.abyte.database.entities;

import android.util.Log;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.abyte.MainActivity;
import com.example.abyte.database.ByteDatabase;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = ByteDatabase.MEAL_TABLE)
public class Meal {
    @PrimaryKey(autoGenerate = true)
    private int mealId;
    private String mealName;
    private String instructions;
    private String ingredient1;
    private String ingredient2;
    private String ingredient3;
    private String ingredient4;
    private String ingredient5;
    private String ingredient6;
    private String ingredient7;
    private String ingredient8;
    private String ingredient9;
    private String ingredient10;

    public Meal(int mealId,
                String mealName,
                String instructions,
                String ingredient1,
                String ingredient2,
                String ingredient3,
                String ingredient4,
                String ingredient5,
                String ingredient6,
                String ingredient7,
                String ingredient8,
                String ingredient9,
                String ingredient10) {
        this.mealId = mealId;
        this.mealName = mealName;
        this.instructions = instructions;
        this.ingredient1 = ingredient1;
        this.ingredient2 = ingredient2;
        this.ingredient3 = ingredient3;
        this.ingredient4 = ingredient4;
        this.ingredient5 = ingredient5;
        this.ingredient6 = ingredient6;
        this.ingredient7 = ingredient7;
        this.ingredient8 = ingredient8;
        this.ingredient9 = ingredient9;
        this.ingredient10 = ingredient10;
    }

    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getIngredient1() {
        return ingredient1;
    }

    public void setIngredient1(String ingredient1) {
        this.ingredient1 = ingredient1;
    }

    public String getIngredient2() {
        return ingredient2;
    }

    public void setIngredient2(String ingredient2) {
        this.ingredient2 = ingredient2;
    }

    public String getIngredient3() {
        return ingredient3;
    }

    public void setIngredient3(String ingredient3) {
        this.ingredient3 = ingredient3;
    }

    public String getIngredient4() {
        return ingredient4;
    }

    public void setIngredient4(String ingredient4) {
        this.ingredient4 = ingredient4;
    }

    public String getIngredient5() {
        return ingredient5;
    }

    public void setIngredient5(String ingredient5) {
        this.ingredient5 = ingredient5;
    }

    public String getIngredient6() {
        return ingredient6;
    }

    public void setIngredient6(String ingredient6) {
        this.ingredient6 = ingredient6;
    }

    public String getIngredient7() {
        return ingredient7;
    }

    public void setIngredient7(String ingredient7) {
        this.ingredient7 = ingredient7;
    }

    public String getIngredient8() {
        return ingredient8;
    }

    public void setIngredient8(String ingredient8) {
        this.ingredient8 = ingredient8;
    }

    public String getIngredient9() {
        return ingredient9;
    }

    public void setIngredient9(String ingredient9) {
        this.ingredient9 = ingredient9;
    }

    public String getIngredient10() {
        return ingredient10;
    }

    public void setIngredient10(String ingredient10) {
        this.ingredient10 = ingredient10;
    }
}
