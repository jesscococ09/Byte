package com.example.abyte;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Shadows;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)

public class ThemeActivityTest {

   @Test
    public void testSelectPinkTheme_SavesToPrefs() {

        try (ActivityScenario<ThemeActivity> scenario = ActivityScenario.launch(ThemeActivity.class)) {
            // Click the Pink theme button
            onView(withId(R.id.btnPink)).perform(click());

            // Get the context and SharedPreferences
            Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
            SharedPreferences prefs = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);


            assertEquals("ThemePink", prefs.getString("app_theme", null));
        }
    }

    @Test
    public void testSelectBabyBlueTheme_SavesToPrefs() {
        try (ActivityScenario<ThemeActivity> scenario = ActivityScenario.launch(ThemeActivity.class)) {
            scenario.onActivity(activity -> {
                activity.findViewById(R.id.btnBabyBlue).performClick();

                SharedPreferences prefs =
                        activity.getSharedPreferences("Settings", Context.MODE_PRIVATE);

                assertEquals("ThemeBabyBlue", prefs.getString("app_theme", null));
            });
        }
    }


    @Test
    public void testSelectLimeTheme_SavesToPrefs() {
        try (ActivityScenario<ThemeActivity> scenario =
                     ActivityScenario.launch(ThemeActivity.class)) {

            scenario.onActivity(activity -> {

                activity.findViewById(R.id.btnLime).performClick();


                SharedPreferences prefs =
                        activity.getSharedPreferences("Settings", Context.MODE_PRIVATE);

                assertEquals("Lime", prefs.getString("app_theme", null));
            });
        }
    }

    @Test
    public void testSelectOrangeTheme_SavesToPrefs() {
        try (ActivityScenario<ThemeActivity> scenario =
                     ActivityScenario.launch(ThemeActivity.class)) {

            scenario.onActivity(activity -> {

                activity.findViewById(R.id.btnOrange).performClick();

                SharedPreferences prefs =
                        activity.getSharedPreferences("Settings", Context.MODE_PRIVATE);


                assertEquals("ThemeOrange", prefs.getString("app_theme", null));
            });
        }
    }

    @Test
    public void testSelectCyanTheme_SavesToPrefs() {
        try (ActivityScenario<ThemeActivity> scenario =
                     ActivityScenario.launch(ThemeActivity.class)) {

            scenario.onActivity(activity -> {

                activity.findViewById(R.id.btnCyan).performClick();


                SharedPreferences prefs =
                        activity.getSharedPreferences("Settings", Context.MODE_PRIVATE);


                assertEquals("Cyan", prefs.getString("app_theme", null));
            });
        }
    }

    @Test
    public void testSelectMagentaTheme_SavesToPrefs() {
        try (ActivityScenario<ThemeActivity> scenario =
                     ActivityScenario.launch(ThemeActivity.class)) {

            scenario.onActivity(activity -> {

                activity.findViewById(R.id.btnMagenta).performClick();

                SharedPreferences prefs =
                        activity.getSharedPreferences("Settings", Context.MODE_PRIVATE);


                assertEquals("Magenta", prefs.getString("app_theme", null));
            });
        }
    }

    @Test
    public void btnReturnMain_navigatesToMainActivity_andFinishesThemeActivity() {
        try (ActivityScenario<ThemeActivity> scenario =
                     ActivityScenario.launch(ThemeActivity.class)) {

            scenario.onActivity(activity -> {
                Button btnReturnMain = activity.findViewById(R.id.btnReturnMain);
                assertNotNull("btnReturnMain should exist in the layout", btnReturnMain);

                // Simulate click
                btnReturnMain.performClick();

                // Check that MainActivity was started
                Intent startedIntent = Shadows.shadowOf(activity).getNextStartedActivity();
                assertNotNull("MainActivity intent should have been started", startedIntent);
                assertEquals(
                        MainActivity.class.getName(),
                        startedIntent.getComponent().getClassName()
                );

                // Check flags: FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_NEW_TASK
                int flags = startedIntent.getFlags();
                assertTrue((flags & Intent.FLAG_ACTIVITY_CLEAR_TOP) != 0);
                assertTrue((flags & Intent.FLAG_ACTIVITY_NEW_TASK) != 0);

                // Check that ThemeActivity is finishing
                assertTrue("ThemeActivity should be finishing after click", activity.isFinishing());
            });
        }


    }
}
