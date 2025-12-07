package com.example.abyte.database.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;

import com.example.abyte.database.ByteDatabase;

import java.util.Arrays;
import java.util.List;

@Entity(tableName = ByteDatabase.MEAL_TABLE, foreignKeys = {
        @ForeignKey(
                entity = User.class,
                parentColumns = "userId",
                childColumns = "creatorId",
                onDelete = CASCADE,
                onUpdate = CASCADE
        )},
        indices = {@Index(value = "creatorId")}
)
public class Meal {
    @PrimaryKey(autoGenerate = true)
    private int mealId;
    private String mealName;
    private Ingredient[] ingredientArray;
    //TODO: Maybe change instuctions to be a string that contains a file name(an html file)
    private String instructionsFile;
    private int imageFile;

    private int creatorId;
    private boolean isPremium;

    public Meal(int mealId, String mealName, Ingredient[] ingredientArray, String instructionsFile, int imageFile, int creatorId, boolean isPremium) {
        this.mealId = mealId;
        this.mealName = mealName;
        this.ingredientArray = ingredientArray;
        this.imageFile = imageFile;
        this.instructionsFile = instructionsFile;
        this.creatorId = creatorId;
        this.isPremium = isPremium;
    }



    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    public Ingredient[] getIngredientArray() {
        return ingredientArray;
    }

    public void setIngredientArray(Ingredient[] ingredientArray) {
        this.ingredientArray = ingredientArray;
    }

    public String getInstructionsFile() {
        return instructionsFile;
    }

    public void setInstructionsFile(String instructionsFile) {
        this.instructionsFile = instructionsFile;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean premiumBool) {
        this.isPremium = premiumBool;
    }

    public int getImageFile() {
        return imageFile;
    }

    public void setImageFile(int imageFile) {
        this.imageFile = imageFile;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "mealId=" + mealId +
                ", mealName='" + mealName + '\'' +
                ", ingredientArray=" + Arrays.toString(ingredientArray) +
                ", instructionsFile='" + instructionsFile + '\'' +
                ", creatorId=" + creatorId +
                ", isPremium=" + isPremium +
                '}';
    }
}

