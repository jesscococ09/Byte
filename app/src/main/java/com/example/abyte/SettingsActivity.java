package com.example.abyte;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import com.example.abyte.database.entities.Setting;
import com.example.abyte.database.entities.User;

public class SettingsActivity extends BaseActivity {

    private SharedPreferences Prefs;
    private Button btnReturn;

    private static final float SCALE_SMALL = 0.9f;
    private static final float SCALE_NORMAL = 1.0f;
    private static final float SCALE_LARGE = 1.2f;

    private RadioGroup rgTextSize;
    private RadioButton rbSmall;
    private RadioButton rbNormal;
    private RadioButton rbLarge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        String themeName = prefs.getString("app_theme", "AppTheme");
        int themeResId = getResources().getIdentifier(themeName, "style", getPackageName());
        setTheme(themeResId);

        SharedPreferences prefs2 = getSharedPreferences("prefs2", MODE_PRIVATE);
        boolean themeOff = prefs2.getBoolean("theme_off", true);
        String themeName2 = themeOff ? "ThemeWhite" : prefs2.getString("app_theme", "AppTheme");
        int themeResId2 = getResources().getIdentifier(themeName2, "style", getPackageName());
        setTheme(themeResId2);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setContentView(R.layout.activity_settings);
        setContentView(R.layout.activity_settings);


        Switch themeSwitch = findViewById(R.id.theme_switch);
        themeSwitch.setChecked(!themeOff);

        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs2.edit().putBoolean("theme_off", !isChecked).apply();
            recreate();

           // Intent intent = new Intent(this, MainActivity.class);
            // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            //startActivity(intent);

        });

        rgTextSize = findViewById(R.id.rgTextSize);
        rbSmall = findViewById(R.id.rbSmall);
        rbNormal = findViewById(R.id.rbNormal);
        rbLarge = findViewById(R.id.rbLarge);

        float currentScale = Setting.getTextScale(this);
        if (currentScale <= 0.95f) {
            rbSmall.setChecked(true);
        } else if (currentScale >= 1.15f) {
            rbLarge.setChecked(true);
        } else {
            rbNormal.setChecked(true);
        }

        rgTextSize.setOnCheckedChangeListener((group, checkedId) -> {
            float newScale;

            if (checkedId == R.id.rbSmall) {
                newScale = SCALE_SMALL;
            } else if (checkedId == R.id.rbLarge) {
                newScale = SCALE_LARGE;
            } else {
                newScale = SCALE_NORMAL;
            }

            Setting.setTextScale(SettingsActivity.this, newScale);

            recreate();


          //  Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
           // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
           // startActivity(intent);
           // finish();
        });




        btnReturn = findViewById(R.id.btn_return);
        if (btnReturn == null) throw new RuntimeException("btnReturn not found in activity_settings.xml");
        btnReturn.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

    }



}
