package com.example.abyte.database.repositories;
import android.content.Context;
import androidx.room.Room;

import com.example.abyte.database.ByteDatabase;
import com.example.abyte.database.daos.SettingDAO;
import com.example.abyte.database.entities.Setting;

public class SettingsRepository {
    private final SettingDAO settingDao;

    public SettingsRepository(Context context) {
        ByteDatabase db = Room.databaseBuilder(context.getApplicationContext(),
                ByteDatabase.class, "settings-db").allowMainThreadQueries().build();
        settingDao = db.settingDAO();
    }

    public void setSetting(String key, String value) {
        Setting setting = new Setting();
        setting.key = key;
        setting.value = value;
        settingDao.insert(setting);
    }

    public String getSetting(String key, String defaultValue) {
        Setting setting = settingDao.getSettingByKey(key);
        return setting != null ? setting.value : defaultValue;
    }
}
