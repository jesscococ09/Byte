package com.example.abyte;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ThemeUnitTest {

    @Test
    public void testSelectTheme_SavesThemeToPrefs() {
        // Launch the activity
        try (ActivityScenario<ThemeActivity> scenario = ActivityScenario.launch(ThemeActivity.class)) {
            scenario.onActivity(activity -> {
                // Simulate clicking the Pink theme button
                activity.findViewById(R.id.btnPink).performClick();

                // Get SharedPreferences
                SharedPreferences prefs = activity.getSharedPreferences("settings", Context.MODE_PRIVATE);

                // Assert that the theme was saved
                assertEquals("ThemePink", prefs.getString("app_theme", null));
            });
        }
    }
}
