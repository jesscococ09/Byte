package com.example.abyte;

import androidx.appcompat.app.AppCompatActivity;
import org.junit.Test;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ThemeActivityTest{
    @Test

    public void themeActivity_extendsAppCompatActivity() {
        //This should check if ThemeActivity is an AppCompatActivity
        assertTrue(AppCompatActivity.class.isAssignableFrom(ThemeActivity.class));
    }

    @Test
    public void themeActivity_hasOnCreateWithBundle() throws NoSuchMethodException{
        //This is gonna check if the onCreate bundle exists
        Method onCreate = ThemeActivity.class.getDeclaredMethod(
                "onCreate", android.os.Bundle.class
        );
        assertNotNull(onCreate);
        //Should be void
        assertEquals(void.class, onCreate.getReturnType());
        //Should be protected
        assertTrue(Modifier.isProtected(onCreate.getModifiers()));
    }
    @Test
    public void themeActivity_hasPrivateSelectThemeWithString() throws NoSuchMethodException{
        //Checks if the selectThme(String) exists
        Method selectTheme = ThemeActivity.class.getDeclaredMethod(
                "selectTheme", String.class
        );
        assertNotNull(selectTheme);
        //Void
        assertEquals(void.class, selectTheme.getReturnType());

        //Private
        assertTrue(Modifier.isPrivate(selectTheme.getModifiers()));
    }
}