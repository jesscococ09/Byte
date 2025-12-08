package com.example.abyte.database.typeConverters;

import androidx.room.TypeConverter;

import com.example.abyte.database.entities.Ingredient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class HashMapTypeConverter {
    private final Gson gson = new Gson();

    @TypeConverter
    public String CustomHashMapToJson(HashMap<String, Ingredient> hashMap) {
        return gson.toJson(hashMap);
    }

    @TypeConverter
    public HashMap<String, Ingredient> JsonToCustomHashMap(String strMap) {
        Type mapType = new TypeToken<HashMap<String, Ingredient>>() {}.getType();
        return gson.fromJson(strMap, mapType);
    }
}
