package com.example.abyte.database.typeConverters;

import androidx.room.TypeConverter;

import com.example.abyte.database.entities.Ingredient;
import com.google.gson.Gson;

public class IngredientTypeConverter {
    private final Gson gson = new Gson();
    @TypeConverter
    public String IngredientToJson(Ingredient ingredient) {
        return gson.toJson(ingredient);
    }

    @TypeConverter
    public Ingredient JsonToIngredient(String strIngredient) {
        return gson.fromJson(strIngredient, Ingredient.class);
    }
}
