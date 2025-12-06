package com.example.abyte.database.typeConverters;

import androidx.room.TypeConverter;

import com.example.abyte.database.entities.Ingredient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class ArrayTypeConverter {
    private final Gson gson = new Gson();

    @TypeConverter
    public String CustomArrayToJson(Ingredient[] ingredientArr){
        return gson.toJson(ingredientArr);
    }

    @TypeConverter
    public Ingredient[] JsonToCustomArray(String strCustArr) {
        return gson.fromJson(strCustArr, Ingredient[].class);
    }

}
