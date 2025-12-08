package com.example.abyte;

import android.content.Context;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatActivity;

import com.example.abyte.database.entities.Setting;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        float scale = Setting.getTextScale(newBase);

        if (Math.abs(scale - 1.0f) < 0.01f) {

            super.attachBaseContext(newBase);
            return;
        }


        Configuration configuration = newBase.getResources().getConfiguration();
        Configuration newConfig = new Configuration(configuration);
        newConfig.fontScale = scale;

        Context context = newBase.createConfigurationContext(newConfig);
        super.attachBaseContext(context);
    }
}
