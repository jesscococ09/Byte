package com.example.abyte;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.abyte.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        //EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

         */
        binding.byteCreateMealsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bing to create_meals.xml
                Toast.makeText(MainActivity.this,"first button",Toast.LENGTH_SHORT).show();
            }
        });
        binding.byteFindMealsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bing to find_meals.xml
                Toast.makeText(MainActivity.this,"second button",Toast.LENGTH_SHORT).show();
            }
        });
        binding.byteSavedMealsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bing to saved_meals.xml
                Toast.makeText(MainActivity.this,"third button",Toast.LENGTH_SHORT).show();
            }
        });
        binding.byteThemesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bing to themes.xml
                Toast.makeText(MainActivity.this,"fourth button",Toast.LENGTH_SHORT).show();
            }
        });
        binding.byteSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bing to setting.xlm
                Toast.makeText(MainActivity.this,"setting button",Toast.LENGTH_SHORT).show();
            }
        });
    }
}