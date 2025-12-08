package com.example.abyte;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import org.junit.Test;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class AdminActivityTest {
    //AdminActivity is a Fragment subclass
    @Test
    public void adminExtendsFragment() {
        assertTrue(Fragment.class.isAssignableFrom(AdminActivity.class));
    }
    //AdminActivity is public and non-abstract
    @Test
    public void classIsPublicAndConcrete() {
        int modifiers = AdminActivity.class.getModifiers();
        assertTrue("AdminActivity should be public", Modifier.isPublic(modifiers));
        assertTrue("AdminActivity should not be abstract", !Modifier.isAbstract(modifiers));
    }
    @Test
    public void hasPublicNoArgConstructor() throws NoSuchMethodException {
        Constructor<AdminActivity> constructor = AdminActivity.class.getConstructor();
        assertNotNull(constructor);
        assertTrue("Constructor should be public", Modifier.isPublic(constructor.getModifiers()));
    }

    @Test
    public void hasOnCreateView() throws NoSuchMethodException {
        Method onCreateView = AdminActivity.class.getDeclaredMethod(
                "onCreateView",
                LayoutInflater.class,
                ViewGroup.class,
                Bundle.class
        );
        assertNotNull(onCreateView);
        //Return android.view.View
        assertEquals(View.class, onCreateView.getReturnType());

        //Public
        assertTrue(Modifier.isPublic(onCreateView.getModifiers()));
    }
    // Check onViewCreated exists and has the expected signature
    @Test
    public void hasOnViewCreated() throws NoSuchMethodException {
        Method onViewCreated = AdminActivity.class.getDeclaredMethod(
                "onViewCreated",
                View.class,
                Bundle.class
        );
        assertNotNull(onViewCreated);
        // Return type is void
        assertEquals(void.class, onViewCreated.getReturnType());
        // Public
        assertTrue(Modifier.isPublic(onViewCreated.getModifiers()));
    }
    // onViewCreated should be an instance method, not static or final
    @Test
    public void onViewCreatedIsInstanceMethod() throws NoSuchMethodException {
        Method onViewCreated = AdminActivity.class.getDeclaredMethod(
                "onViewCreated",
                View.class,
                Bundle.class
        );
        int modifiers = onViewCreated.getModifiers();
        assertTrue("onViewCreated should not be static", !Modifier.isStatic(modifiers));
        assertTrue("onViewCreated should not be final", !Modifier.isFinal(modifiers));
    }
    @Test
    public void privateOpenFrag() throws NoSuchMethodException {
        Method openFragment = AdminActivity.class.getDeclaredMethod(
                "openFragment",
                Fragment.class
        );
        assertNotNull(openFragment);
        // Return type is void
        assertEquals(void.class, openFragment.getReturnType());
        // Should be private
        assertTrue("openFragment should be private",
                Modifier.isPrivate(openFragment.getModifiers()));
        // Should not be static
        assertTrue("openFragment should not be static",
                !Modifier.isStatic(openFragment.getModifiers()));
    }
    //Ensuring there is only one openFragment method with Fragment parameter
    @Test
    public void openFragmentHasSingleFragmentParameter() throws NoSuchMethodException {
        Method openFragment = AdminActivity.class.getDeclaredMethod(
                "openFragment",
                Fragment.class
        );
        Class<?>[] params = openFragment.getParameterTypes();
        assertEquals("openFragment should have exactly one parameter", 1, params.length);
        assertEquals("Parameter should be a Fragment", Fragment.class, params[0]);
    }
}