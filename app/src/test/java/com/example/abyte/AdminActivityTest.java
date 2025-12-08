package com.example.abyte;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import androidx.fragment.app.Fragment;

import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class AdminActivityTest{
    //adminActivity is a Fragment subclass
    @Test
    public void adminExtendsFragment(){
        assertTrue(Fragment.class.isAssignableFrom(AdminActivity.class));
    }
    @Test
    public void hasOnCreateView() throws NoSuchMethodException{
        Method onCreateView = AdminActivity.class.getDeclaredMethod("onCreateView",
                android.view.LayoutInflater.class, android.view.ViewGroup.class,
                android.os.Bundle.class);
        assertNotNull(onCreateView);
        //Return andorid.view.View
        assertEquals(android.view.View.class, onCreateView.getReturnType());
        //Public
        assertTrue(Modifier.isPublic(onCreateView.getModifiers()));
    }
    @Test
    public void privateOpenFrag() throws NoSuchMethodException{
        Method openFragment = AdminActivity.class.getDeclaredMethod(
                "openFragment", Fragment.class);
    }
}