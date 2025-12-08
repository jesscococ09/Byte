package com.example.abyte;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.abyte.database.entities.Setting;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Shadows;

@RunWith(AndroidJUnit4.class)
public class SettingsActivityTest {

    private Context appContext;

    @Before
    public void setUp() {
        appContext = ApplicationProvider.getApplicationContext();

        // Clean prefs before each test
        appContext.getSharedPreferences("settings", Context.MODE_PRIVATE)
                .edit().clear().commit();
        appContext.getSharedPreferences("prefs2", Context.MODE_PRIVATE)
                .edit().clear().commit();

        // Reset text scale
        Setting.setTextScale(appContext, 1.0f);
    }



    @Test
    public void themeSwitch_isOff_whenThemeOffTrueInPrefs() {
        // theme_off = true → switch should be unchecked
        SharedPreferences prefs2 = appContext.getSharedPreferences("prefs2", Context.MODE_PRIVATE);
        prefs2.edit().putBoolean("theme_off", true).commit();

        try (ActivityScenario<SettingsActivity> scenario =
                     ActivityScenario.launch(SettingsActivity.class)) {

            scenario.onActivity(activity -> {
                Switch themeSwitch = activity.findViewById(R.id.theme_switch);
                assertFalse("Switch should be OFF when theme_off = true", themeSwitch.isChecked());
            });
        }
    }

    @Test
    public void themeSwitch_isOn_whenThemeOffFalseInPrefs() {

        SharedPreferences prefs2 = appContext.getSharedPreferences("prefs2", Context.MODE_PRIVATE);
        prefs2.edit().putBoolean("theme_off", false).commit();

        try (ActivityScenario<SettingsActivity> scenario =
                     ActivityScenario.launch(SettingsActivity.class)) {

            scenario.onActivity(activity -> {
                Switch themeSwitch = activity.findViewById(R.id.theme_switch);
                assertTrue("Switch should be ON when theme_off = false", themeSwitch.isChecked());
            });
        }
    }

    @Test
    public void togglingThemeSwitch_updatesThemeOffInPrefs() {
        SharedPreferences prefs2 = appContext.getSharedPreferences("prefs2", Context.MODE_PRIVATE);

        prefs2.edit().putBoolean("theme_off", true).commit();

        try (ActivityScenario<SettingsActivity> scenario =
                     ActivityScenario.launch(SettingsActivity.class)) {

            scenario.onActivity(activity -> {
                Switch themeSwitch = activity.findViewById(R.id.theme_switch);


                assertFalse(themeSwitch.isChecked());


                themeSwitch.performClick();


                SharedPreferences updated =
                        activity.getSharedPreferences("prefs2", Context.MODE_PRIVATE);
                boolean themeOff = updated.getBoolean("theme_off", true);


                assertFalse("theme_off should be false after turning switch ON", themeOff);
            });
        }
    }


    @Test
    public void initialTextSize_small_whenScaleSmall() {

        Setting.setTextScale(appContext, 0.9f);

        try (ActivityScenario<SettingsActivity> scenario =
                     ActivityScenario.launch(SettingsActivity.class)) {

            scenario.onActivity(activity -> {
                RadioButton rbSmall = activity.findViewById(R.id.rbSmall);
                RadioButton rbNormal = activity.findViewById(R.id.rbNormal);
                RadioButton rbLarge = activity.findViewById(R.id.rbLarge);

                assertTrue(rbSmall.isChecked());
                assertFalse(rbNormal.isChecked());
                assertFalse(rbLarge.isChecked());
            });
        }
    }

    @Test
    public void initialTextSize_normal_whenScaleNormal() {
        Setting.setTextScale(appContext, 1.0f);

        try (ActivityScenario<SettingsActivity> scenario =
                     ActivityScenario.launch(SettingsActivity.class)) {

            scenario.onActivity(activity -> {
                RadioButton rbSmall = activity.findViewById(R.id.rbSmall);
                RadioButton rbNormal = activity.findViewById(R.id.rbNormal);
                RadioButton rbLarge = activity.findViewById(R.id.rbLarge);

                assertFalse(rbSmall.isChecked());
                assertTrue(rbNormal.isChecked());
                assertFalse(rbLarge.isChecked());
            });
        }
    }

    @Test
    public void initialTextSize_large_whenScaleLarge() {

        Setting.setTextScale(appContext, 1.3f);

        try (ActivityScenario<SettingsActivity> scenario =
                     ActivityScenario.launch(SettingsActivity.class)) {

            scenario.onActivity(activity -> {
                RadioButton rbSmall = activity.findViewById(R.id.rbSmall);
                RadioButton rbNormal = activity.findViewById(R.id.rbNormal);
                RadioButton rbLarge = activity.findViewById(R.id.rbLarge);

                assertFalse(rbSmall.isChecked());
                assertFalse(rbNormal.isChecked());
                assertTrue(rbLarge.isChecked());
            });
        }
    }

    @Test
    public void selectingSmall_updatesTextScaleToSmall() {
        try (ActivityScenario<SettingsActivity> scenario =
                     ActivityScenario.launch(SettingsActivity.class)) {

            scenario.onActivity(activity -> {
                RadioGroup rgTextSize = activity.findViewById(R.id.rgTextSize);
                RadioButton rbSmall = activity.findViewById(R.id.rbSmall);

                //  "Small"
                rbSmall.performClick();

                float scale = Setting.getTextScale(activity);
                assertEquals(0.9f, scale, 0.001f);
            });
        }
    }

    @Test
    public void selectingLarge_updatesTextScaleToLarge() {
        try (ActivityScenario<SettingsActivity> scenario =
                     ActivityScenario.launch(SettingsActivity.class)) {

            scenario.onActivity(activity -> {
                RadioGroup rgTextSize = activity.findViewById(R.id.rgTextSize);
                RadioButton rbLarge = activity.findViewById(R.id.rbLarge);

                rbLarge.performClick();

                float scale = Setting.getTextScale(activity);
                assertEquals(1.2f, scale, 0.001f);
            });
        }
    }

    @Test
    public void selectingNormal_updatesTextScaleToNormal() {
        try (ActivityScenario<SettingsActivity> scenario =
                     ActivityScenario.launch(SettingsActivity.class)) {

            scenario.onActivity(activity -> {
                RadioGroup rgTextSize = activity.findViewById(R.id.rgTextSize);
                RadioButton rbNormal = activity.findViewById(R.id.rbNormal);

                rbNormal.performClick();

                float scale = Setting.getTextScale(activity);
                assertEquals(1.0f, scale, 0.001f);
            });
        }
    }


    @Test
    public void btnReturn_startsMainActivity_andFinishes() {
        try (ActivityScenario<SettingsActivity> scenario =
                     ActivityScenario.launch(SettingsActivity.class)) {

            scenario.onActivity(activity -> {
                Button btnReturn = activity.findViewById(R.id.btn_return);
                btnReturn.performClick();

                // MainActivity
                Intent nextIntent = Shadows.shadowOf(activity).getNextStartedActivity();
                assertEquals(MainActivity.class.getName(),
                        nextIntent.getComponent().getClassName());

                // Activity
                assertTrue(activity.isFinishing());
            });
        }
    }
}
