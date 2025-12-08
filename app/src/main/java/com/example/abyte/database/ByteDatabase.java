package com.example.abyte.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.abyte.MainActivity;
import com.example.abyte.database.daos.MealDAO;
import com.example.abyte.database.daos.SettingDAO;
import com.example.abyte.database.daos.ThemeDAO;
import com.example.abyte.database.daos.UserDAO;
import com.example.abyte.database.entities.Ingredient;
import com.example.abyte.database.entities.Meal;
import com.example.abyte.database.entities.Setting;
import com.example.abyte.database.entities.Theme;
import com.example.abyte.database.entities.User;
import com.example.abyte.database.typeConverters.ArrayTypeConverter;
import com.example.abyte.database.typeConverters.HashMapTypeConverter;
import com.example.abyte.database.typeConverters.IngredientTypeConverter;
import com.example.abyte.database.typeConverters.LocalDateTypeConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@TypeConverters({LocalDateTypeConverter.class, IngredientTypeConverter.class, HashMapTypeConverter.class, ArrayTypeConverter.class})
@Database(entities = {User.class, Meal.class}, version = 1,exportSchema = false)
//add Setting.class, Theme.class
@TypeConverters(LocalDateTypeConverter.class)
@Database(entities = {User.class}, version = 2,exportSchema = false)
//add Meal.class, Setting.class, Theme.class
public abstract class ByteDatabase extends RoomDatabase {
    private static final String DATABASE_NAME="Bytedatabase";
    public static final String MEAL_TABLE="mealtable";
    //public static final String SETTING_TABLE="settingtable";
    public static final String USER_TABLE="usertable";
    //public static final String THEME_TABLE="themetable";

    private  static volatile ByteDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS=4;
    public static final ExecutorService databaseWriteExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static ByteDatabase getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (ByteDatabase.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(
                            context.getApplicationContext(),
                            ByteDatabase.class,
                            DATABASE_NAME
                    )
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static final RoomDatabase.Callback addDefaultValues= new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            Log.i(MainActivity.TAG,"DATABASE CREATED!");
            databaseWriteExecutor.execute(()->{
                UserDAO dao= INSTANCE.userDAO();
                dao.deleteALL();
                User admin=new User("admin1","admin1","admin");
                admin.setAdmin(true);
                dao.insert(admin);
                User testUser1=new User("testuser1","testuser1","testing");
                dao.insert(testUser1);
            });
        }
    };

    public abstract UserDAO userDAO();
    //public abstract MealDAO mealDAO();
    //public abstract SettingDAO settingDAO();
    //public abstract ThemeDAO themeDAO();

}
