package com.example.abyte.database.daos;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.abyte.database.ByteDatabase;
import com.example.abyte.database.entities.Setting;


@Dao

public interface SettingDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Setting... setting);

    @Update
    void update(Setting setting);

    @Query("SELECT * FROM " + ByteDatabase.SETTING_TABLE + " WHERE `Key` = :key LIMIT 1")
    Setting getSettingByKey(String key);
}
