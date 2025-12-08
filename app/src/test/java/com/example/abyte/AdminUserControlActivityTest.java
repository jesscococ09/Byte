package com.example.abyte;

import androidx.fragment.app.Fragment;
import org.junit.Test;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AdminUserControlActivityTest {
    // AdminUserControlActivity is a Fragment subclass
    @Test
    public void adminUserControl_extendsFragment() {
        assertTrue(Fragment.class.isAssignableFrom(AdminUserControlActivity.class));
    }
    // Class should be public and non-abstract
    @Test
    public void adminUserControl_isPublicAndConcrete() {
        int modifiers = AdminUserControlActivity.class.getModifiers();
        assertTrue("AdminUserControlActivity should be public", Modifier.isPublic(modifiers));
        assertTrue("AdminUserControlActivity should not be abstract", !Modifier.isAbstract(modifiers));
    }
    // Fragment must have a public no-arg constructor
    @Test
    public void adminUserControl_hasPublicNoArgConstructor() throws NoSuchMethodException {
        Constructor<AdminUserControlActivity> constructor = AdminUserControlActivity.class.getConstructor();
        assertNotNull(constructor);
        assertTrue("Constructor should be public",
                Modifier.isPublic(constructor.getModifiers()));
    }
    @Test
    public void adminUserControl_hasOnCreateView() throws NoSuchMethodException {
        Method onCreateView = AdminUserControlActivity.class.getDeclaredMethod(
                "onCreateView",
                LayoutInflater.class,
                ViewGroup.class,
                Bundle.class
        );
        assertNotNull(onCreateView);
        // Should return android.view.View
        assertEquals(View.class, onCreateView.getReturnType());
        // Be public
        assertTrue(Modifier.isPublic(onCreateView.getModifiers()));
    }
    //onCreateView should be an instance method
    @Test
    public void adminUserControl_onCreateViewIsInstanceMethod() throws NoSuchMethodException {
        Method onCreateView = AdminUserControlActivity.class.getDeclaredMethod(
                "onCreateView", LayoutInflater.class, ViewGroup.class, Bundle.class
        );
        int modifiers = onCreateView.getModifiers();
        assertTrue("onCreateView should not be static", !Modifier.isStatic(modifiers));
        assertTrue("onCreateView should not be final", !Modifier.isFinal(modifiers));
    }
    @Test
    public void adminUserControl_hasOnViewCreated() throws NoSuchMethodException {
        // Checks if onViewCreated(View v, Bundle b) exists
        Method onViewCreated = AdminUserControlActivity.class.getDeclaredMethod(
                "onViewCreated",
                View.class,
                Bundle.class
        );
        assertNotNull(onViewCreated);
        // Return void
        assertEquals(void.class, onViewCreated.getReturnType());
        // Should be public
        assertTrue(Modifier.isPublic(onViewCreated.getModifiers()));
    }
    @Test
    public void adminUserControl_hasPrivateOpenFragment() throws NoSuchMethodException {
        //If openFragment(Fragment) exists
        Method openFragment = AdminUserControlActivity.class.getDeclaredMethod(
                "openFragment", Fragment.class
        );
        assertNotNull(openFragment);
        //Return void
        assertEquals(void.class, openFragment.getReturnType());
        //Private
        assertTrue("openFragment should be private",
                Modifier.isPrivate(openFragment.getModifiers()));
        //Not static
        assertTrue("openFragment should not be static",
                !Modifier.isStatic(openFragment.getModifiers()));
    }
    // Ensure openFragment has exactly one Fragment parameter
    @Test
    public void adminUserControl_openFragmentHasSingleFragmentParameter() throws NoSuchMethodException {
        Method openFragment = AdminUserControlActivity.class.getDeclaredMethod(
                "openFragment", Fragment.class
        );
        Class<?>[] params = openFragment.getParameterTypes();
        assertEquals("openFragment should have exactly one parameter", 1, params.length);
        assertEquals("Parameter should be a Fragment", Fragment.class, params[0]);
    }
}