package com.example.abyte;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.abyte.database.entities.Ingredient;
import com.example.abyte.database.entities.Meal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SearchTest {
    HashMap<String, Ingredient> userIngrMap;
    Ingredient[] ingredientArr1;
    Ingredient[] ingredientArr2;

    Ingredient[] realIngrArr;

    Meal exampleMeal1;
    Meal exampleMeal2;

    Meal realMeal;

    List<Meal> mealList;

    Search search;

    @Before
    public void setUp() {
        userIngrMap = new HashMap<>();
        realIngrArr = new Ingredient[] {
                new Ingredient("Ground Beef", "lbs", 1.0, true),
                new Ingredient("Tomato", "cups", 2.5, true),
                new Ingredient("Tomato Paste", "oz", 6, false),
                new Ingredient("Mushroom", "oz", 4.5, false),
                new Ingredient("Onion", "tbs", 2.0, false),
                new Ingredient("Salt", "tsp", 1.0, false),
                new Ingredient("Dried Oregano", "tsp", 1.0, false),
                new Ingredient("White Sugar", "tsp", 0.75, false),
                new Ingredient("Black Pepper", "tsp", 0.25, false),
                new Ingredient("Garlic Powder", "tsp", 0.125, false),
                new Ingredient("Spaghetti", "oz", 12.0, true)
        };

        exampleMeal1 = new Meal(1, "Meal1", ingredientArr1, "", 0, false);
        exampleMeal2 = new Meal(2, "Meal2", ingredientArr2, "", 0, false);
        realMeal = new Meal(3, "Spaghetti w/ Tomato Sauce", realIngrArr, "https://www.allrecipes.com/recipe/11715/easy-spaghetti-with-tomato-sauce/", 0, false);

        mealList = new ArrayList<>();
        mealList.add(exampleMeal1);
        mealList.add(exampleMeal2);
        mealList.add(realMeal);
        search = new Search(mealList, userIngrMap);
    }

    @After
    public void tearDown() {
        userIngrMap = null;
        realIngrArr = null;
        ingredientArr1 = null;
        ingredientArr2 = null;

        realMeal = null;
        exampleMeal1 = null;
        exampleMeal2 = null;
        mealList = null;

        search = null;
    }

    @Test
    public void constructor_and_getter_test() {
        assertNotNull(search.getMealList());
        assertNotNull(search.getAvailableIngredients());
        assertNotNull(search.getAvailableMeals());
        assertNotNull(search.getCheckedMeals());

        assertTrue(search.getAvailableMeals().containsKey(Search.MISSING));
        assertTrue(search.getAvailableMeals().containsKey(Search.PARTIAL));
        assertTrue(search.getAvailableMeals().containsKey(Search.FULL));

        assertNotNull(search.getAvailableMeals().get(Search.MISSING));
        assertNotNull(search.getAvailableMeals().get(Search.PARTIAL));
        assertNotNull(search.getAvailableMeals().get(Search.FULL));
    }

    @Test
    public void basic_compare_test() {
        Ingredient testIngredient = new Ingredient("Chicken", "lbs", 1.0, true);
        ingredientArr1 = new Ingredient[] {testIngredient};
        exampleMeal1 = new Meal(1, "Meal1", ingredientArr1, "", 0, false);
        userIngrMap.put("Chicken", new Ingredient("Chicken", "lbs", 1.0, true));
        HashMap<Integer, List<Ingredient>> testOutput = search.compare(exampleMeal1);

        assertNotNull(testOutput);
        assertNotNull(testOutput.get(Search.FULL));
        assertNotNull(testOutput.get(Search.FULL).getFirst());
        assertEquals(testIngredient, testOutput.get(Search.FULL).getFirst());
    }

    @Test
    public void complex_compare_test(){
        List<Ingredient> userIngrList = new ArrayList<>();
        userIngrList.add(new Ingredient("A", "lbs", -13, true));
        userIngrList.add(new Ingredient("B", "lbs", 0, true));
        userIngrList.add(new Ingredient("C", "lbs", 4, true));
        userIngrList.add(new Ingredient("D", "lbs", 12, true));
        userIngrList.add(new Ingredient("E", "lbs", 15, true));
        userIngrList.add(new Ingredient("F", "lbs", -3, false));
        userIngrList.add(new Ingredient("G", "lbs", 0, false));
        userIngrList.add(new Ingredient("H", "lbs", 3, false));
        userIngrList.add(new Ingredient("I", "lbs", 17, false));
        userIngrList.add(new Ingredient("J", "lbs", 19, true));

        ingredientArr1 = new Ingredient[] {
                new Ingredient("A", "lbs", 9, true),
                new Ingredient("B", "lbs", 10, true),
                new Ingredient("C", "lbs", 11, true),
                new Ingredient("D", "lbs", 12, true),
                new Ingredient("E", "lbs", 13, true),
                new Ingredient("F", "lbs", 14, false),
                new Ingredient("G", "lbs", 15, false),
                new Ingredient("H", "lbs", 16, false),
                new Ingredient("I", "lbs", 17, false),
                new Ingredient("J", "lbs", 18, false),
                new Ingredient("K", "lbs", 19, true),
        };

        exampleMeal1 = new Meal(1, "Lower Than Meal", ingredientArr1, "Missing", 0, false);
        for (int i = 0; i < userIngrList.size(); i++) {
            userIngrMap.put(userIngrList.get(i).getName(), userIngrList.get(i));
        }

        List<Ingredient> expectedMissingList = new ArrayList<>();
        expectedMissingList.add(ingredientArr1[0]);
        expectedMissingList.add(ingredientArr1[1]);
        expectedMissingList.add(ingredientArr1[10]);

        List<Ingredient> expectedPartialList = new ArrayList<>();
        expectedPartialList.add(ingredientArr1[2]);
        expectedPartialList.add(ingredientArr1[5]);
        expectedPartialList.add(ingredientArr1[6]);
        expectedPartialList.add(ingredientArr1[7]);


        List<Ingredient> expectedFullList = new ArrayList<>();
        expectedFullList.add(ingredientArr1[3]);
        expectedFullList.add(ingredientArr1[4]);
        expectedFullList.add(ingredientArr1[8]);
        expectedFullList.add(ingredientArr1[9]);

        HashMap<Integer, List<Ingredient>> actualMap = search.compare(exampleMeal1);

        System.out.print("Complex Compare: Doing null Test...");
        assertNotNull(actualMap);
        assertNotNull(actualMap.get(Search.MISSING));
        assertNotNull(actualMap.get(Search.PARTIAL));
        assertNotNull(actualMap.get(Search.FULL));
        System.out.println(" Done");

        System.out.print("Complex Compare: Doing Individual List Comparison...");
        System.out.print("Not Avail...");
        assertEquals(expectedMissingList, actualMap.get(Search.MISSING));
        System.out.print("Partial...");
        assertEquals(expectedPartialList, actualMap.get(Search.PARTIAL));
        System.out.print("Full...");
        assertEquals(expectedFullList, actualMap.get(Search.FULL));
        System.out.println(" Done");

        HashMap<Integer, List<Ingredient>> expectedMap = new HashMap<>();
        expectedMap.put(Search.MISSING, expectedMissingList);
        expectedMap.put(Search.PARTIAL, expectedPartialList);
        expectedMap.put(Search.FULL, expectedFullList);
        System.out.println(" Done");

        System.out.print("Complex Compare: Doing Direct Map Check");
        assertEquals(expectedMap, actualMap);
        System.out.println(" Done");
    }
}
