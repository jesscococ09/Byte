// com/example/abyte/database/entities/Setting.java
package com.example.abyte.database.entities;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.abyte.database.ByteDatabase;
import com.example.abyte.database.daos.SettingDAO;

@Entity(tableName = ByteDatabase.SETTING_TABLE)
public class Setting {

    private static final String KEY_TEXT_SCALE = "text_scale";
    private static final float DEFAULT_TEXT_SCALE = 1.0f;

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "Key")
    public String key;

    @ColumnInfo(name = "value")
    public String value;

    public Setting(String key, String value) {
        this.key = key;
        this.value = value;
    }


    public Setting() { }

    public static float getTextScale(Context context) {
        try {
            SettingDAO dao = ByteDatabase.getInstance(context).settingDAO();
            Setting setting = dao.getSettingByKey(KEY_TEXT_SCALE);
            if (setting == null || setting.value == null) return DEFAULT_TEXT_SCALE;
            return Float.parseFloat(setting.value);
        } catch (Exception e) {
            // Log if you want
            e.printStackTrace();
            return DEFAULT_TEXT_SCALE;
        }
    }

    public static void setTextScale(Context context, float scale) {
        SettingDAO dao = ByteDatabase.getInstance(context).settingDAO();
        Setting existing = dao.getSettingByKey(KEY_TEXT_SCALE);

        if (existing == null) {
            existing = new Setting(KEY_TEXT_SCALE, Float.toString(scale));
            dao.insert(existing);
        } else {
            existing.value = Float.toString(scale);
            dao.update(existing);
        }
    }
}
