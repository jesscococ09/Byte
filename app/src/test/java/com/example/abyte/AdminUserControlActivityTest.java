package com.example.abyte;

import androidx.fragment.app.Fragment;
import org.junit.Test;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AdminUserControlActivityTest{
    @Test
    public void adminUserControl_extendsFragment(){
        assertTrue(Fragment.class.isAssignableFrom(AdminUserControlActivity.class));
    }
    @Test
    public void adminUserControl_hasOnCreateView() throws NoSuchMethodException{
        Method onCreateView = AdminUserControlActivity.class.getDeclaredMethod(
                "onCreateView",
                android.view.LayoutInflater.class,
                android.view.ViewGroup.class,
                android.os.Bundle.class
        );
        assertNotNull(onCreateView);
        //Should return android.view.View
        assertEquals(android.view.View.class, onCreateView.getReturnType());
        //Be public
        assertTrue(Modifier.isPublic(onCreateView.getModifiers()));
    }
    @Test
    public void adminUserControl_hasOnViewCreated() throws NoSuchMethodException{
        //Checks if onViewCreated(View v, Bundle b) exists
        Method onViewCreated = AdminUserControlActivity.class.getDeclaredMethod(
                "onViewCreated", android.view.View.class, android.os.Bundle.class
        );
        assertNotNull(onViewCreated);
        //Return void
        assertEquals(void.class, onViewCreated.getReturnType());
        //Should be public
        assertTrue(Modifier.isPublic(onViewCreated.getModifiers()));
    }
    @Test
    public void adminUserControl_hasPrivateOpenFragment() throws NoSuchMethodException{
        //If openFragment(Fragment) such as AdminAct_Bugs exists
        Method openFragment = AdminUserControlActivity.class.getDeclaredMethod(
                "openFragment", Fragment.class
        );
        assertNotNull(openFragment);
        //Retrun void
        assertEquals(void.class, openFragment.getReturnType());
        //Private
        assertTrue(Modifier.isPrivate(openFragment.getModifiers()));
    }
}