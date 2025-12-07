package com.example.abyte;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;


public class ThemeActivity extends AppCompatActivity {


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        findViewById(R.id.btnWhite).setOnClickListener(v -> selectTheme("AppTheme"));
        findViewById(R.id.btnPink).setOnClickListener(v -> selectTheme("ThemePink"));
        findViewById(R.id.btnBabyBlue).setOnClickListener(v -> selectTheme("ThemeBabyBlue"));
        findViewById(R.id.btnOrange).setOnClickListener(v -> selectTheme("ThemeOrange"));
        findViewById(R.id.btnLime).setOnClickListener(v -> selectTheme("Lime"));
        findViewById(R.id.btnCyan).setOnClickListener(v -> selectTheme("Cyan"));
        findViewById(R.id.btnMagenta).setOnClickListener(v -> selectTheme("Magenta"));
    }

    private void selectTheme(String themeName) {
        SharedPreferences prefs4 = getSharedPreferences("settings", MODE_PRIVATE);
        prefs4.edit().putString("app_theme", themeName).apply();

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
//

}