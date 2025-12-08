package com.example.abyte.database.entities;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.abyte.database.ByteDatabase;

//@Entity(tableName = ByteDatabase.SETTING_TABLE)
public class Setting {
    /*
    @PrimaryKey(autoGenerate = true)
    private int settingId;
    //Use databaseDesign


     */

    private static final String PREF_NAME = "app_prefs";
    private static final String KEY_TEXT_SCALE = "text_scale";
    private static final float DEFAULT_SCALE = 1.0f;

    public static float getTextScale(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getFloat(KEY_TEXT_SCALE, DEFAULT_SCALE);
    }

    public static void setTextScale(Context context, float scale) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putFloat(KEY_TEXT_SCALE, scale).apply();
    }
}

