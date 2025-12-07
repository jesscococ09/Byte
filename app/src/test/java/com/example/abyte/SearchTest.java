package com.example.abyte;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.abyte.database.entities.Ingredient;
import com.example.abyte.database.entities.Meal;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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

    Ingredient[] realIngrArr;

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

        realMeal = new Meal(3, "Spaghetti w/ Tomato Sauce", realIngrArr, "https://www.allrecipes.com/recipe/11715/easy-spaghetti-with-tomato-sauce/", 0, false);

        mealList = new ArrayList<>();
        mealList.add(realMeal);
        search = new Search(mealList, userIngrMap);
    }

    @After
    public void tearDown() {
        userIngrMap = null;
        realIngrArr = null;

        realMeal = null;
        mealList = null;

        search = null;
    }

    @Test
    public void constructor_and_getter_test() {
        assertNotNull(search.getMealList());
        assertNotNull(search.getAvailableIngredients());
        assertNotNull(search.getCategorizedMeals());
        assertNotNull(search.getCheckedMeals());

        assertTrue(search.getCategorizedMeals().containsKey(Search.MISSING));
        assertTrue(search.getCategorizedMeals().containsKey(Search.PARTIAL));
        assertTrue(search.getCategorizedMeals().containsKey(Search.FULL));

        assertNotNull(search.getCategorizedMeals().get(Search.MISSING));
        assertNotNull(search.getCategorizedMeals().get(Search.PARTIAL));
        assertNotNull(search.getCategorizedMeals().get(Search.FULL));
    }

    @Test
    public void basic_compare_test() {
        Ingredient testIngredient = new Ingredient("Chicken", "lbs", 1.0, true);
        Meal exampleMeal = new Meal(1, "Meal1", new Ingredient[] {testIngredient}, "", 0, false);
        userIngrMap.put("Chicken", new Ingredient("Chicken", "lbs", 1.0, true));
        HashMap<Integer, List<Ingredient>> testOutput = search.compare(exampleMeal);

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

        Ingredient[] ingredientArr = new Ingredient[] {
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

        Meal exampleMeal = new Meal(1, "Lower Than Meal", ingredientArr, "Missing", 0, false);
        for (int i = 0; i < userIngrList.size(); i++) {
            userIngrMap.put(userIngrList.get(i).getName(), userIngrList.get(i));
        }

        List<Ingredient> expectedMissingList = new ArrayList<>();
        expectedMissingList.add(ingredientArr[0]);
        expectedMissingList.add(ingredientArr[1]);
        expectedMissingList.add(ingredientArr[10]);

        List<Ingredient> expectedPartialList = new ArrayList<>();
        expectedPartialList.add(ingredientArr[2]);
        expectedPartialList.add(ingredientArr[5]);
        expectedPartialList.add(ingredientArr[6]);
        expectedPartialList.add(ingredientArr[7]);


        List<Ingredient> expectedFullList = new ArrayList<>();
        expectedFullList.add(ingredientArr[3]);
        expectedFullList.add(ingredientArr[4]);
        expectedFullList.add(ingredientArr[8]);
        expectedFullList.add(ingredientArr[9]);

        HashMap<Integer, List<Ingredient>> actualMap = search.compare(exampleMeal);

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

    @Test
    public void categorize_meal_error_test() {
        System.out.print("Categorize Meal Test: Setting up Error Case testing...");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setErr(new PrintStream(out));
        System.out.println(" Done");


        System.out.print("Categorize Meal Test: Testing Empty checkedMeals Error Case...");
        assertFalse(search.categorizeMeal(realMeal.getMealName()));
        assertEquals("ERROR AT CATEGORIZE MEAL: The categorizedMeals Hashmap is empty, problem comparing meal" + System.lineSeparator(), out.toString());
        System.out.println(" Done");
        out.reset();

        for (Ingredient i: realMeal.getIngredientArray()) {
            userIngrMap.put(i.getName(), i);
        }

        System.out.print("Categorize Meal Test: Method Properly running check...");
        search.getCheckedMeals().put(realMeal.getMealName(),search.compare(realMeal));
        System.out.print(" checked meal categorized...");
        assertTrue(search.categorizeMeal(realMeal.getMealName()));
        System.out.println(" Done");

        System.out.print("Categorize Meal Test: Doing Direct Map Check...");
        assertNotNull(search.getCategorizedMeals().get(Search.FULL));
        assertEquals(realMeal.getMealName(),search.getCategorizedMeals().get(Search.FULL).getFirst());
        System.out.println(" Done");

        System.out.print("Categorize Meal Test: Testing Wrong Meal Name Error Case...");
        assertFalse(search.categorizeMeal("Wrong Meal Name"));
        assertEquals("ERROR AT CATEGORIZE MEAL: The Meal Name \"Wrong Meal Name\" is not in the categorizedMeals Hashmap" + System.lineSeparator(), out.toString());
        System.out.println(" Done");
    }

    @Test
    public void categorize_meal_categorization_test() {
        System.out.print("Categorize Meal Categorization Test: Setting up...");
        for (Ingredient i: realMeal.getIngredientArray()) {
            userIngrMap.put(i.getName(), i);
        }
        Ingredient[] ingredientArr1 = new Ingredient[]{
                new Ingredient("Ground Beef", "lbs", 1.0, true),
                new Ingredient("Tomato", "cups", 2.5, true),
                new Ingredient("Pomegranate Molasses", "oz", 39, false),
        };
        Ingredient[] ingredientArr2 = new Ingredient[]{
                new Ingredient("Unobtainium", "lbs", 3, true),
                new Ingredient("Tomato", "cups", 2.5, true),
                new Ingredient("Tomato Paste", "oz", 6, false)
        };
        Meal exampleMeal1 = new Meal(1, "Meal1", ingredientArr1, "", 0, false);
        assertNotNull(exampleMeal1);
        search.getMealList().add(exampleMeal1);
        Meal exampleMeal2 = new Meal(2, "Meal2", ingredientArr2, "", 0, false);
        assertNotNull(exampleMeal2);
        search.getMealList().add(exampleMeal2);
        System.out.println(" Done");

        System.out.println("Meal List: " + search.getMealList().toString());

        search.getCheckedMeals().put(realMeal.getMealName(),search.compare(realMeal));
        search.getCheckedMeals().put(exampleMeal1.getMealName(),search.compare(exampleMeal1));
        search.getCheckedMeals().put(exampleMeal2.getMealName(),search.compare(exampleMeal2));

        assertTrue(search.categorizeMeal(exampleMeal1.getMealName()));
        assertTrue(search.categorizeMeal(exampleMeal2.getMealName()));
        assertTrue(search.categorizeMeal(realMeal.getMealName()));
        assertNotNull(search.getCategorizedMeals().get(Search.FULL));
        assertNotNull(search.getCategorizedMeals().get(Search.PARTIAL));
        assertNotNull(search.getCategorizedMeals().get(Search.MISSING));
        System.out.println(search.getCategorizedMeals().toString());

        System.out.print("Categorize Meal Categorization Test: Checking sizes...");
        System.out.print(" checking full size...");
        assertEquals(1, search.getCategorizedMeals().get(Search.FULL).size());
        System.out.print(" checking partial size...");
        assertEquals(1, search.getCategorizedMeals().get(Search.PARTIAL).size());
        System.out.print(" checking missing size...");
        assertEquals(1, search.getCategorizedMeals().get(Search.MISSING).size());

    }
}
