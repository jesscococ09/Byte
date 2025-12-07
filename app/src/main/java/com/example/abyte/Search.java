/**
 * This class is incomplete... but
 * The function of this class is to iterate through the user's list of ingredients and
 *  store information relating to the selected meals to comb through
 * @author Paul Conner
 * @since 0.1
 */

package com.example.abyte;

import com.example.abyte.database.entities.Ingredient;
import com.example.abyte.database.entities.Meal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Search {
    /**
     Flag that represents missingness, used in both {@link #catagorizedMeals} and {@link #compare(Meal)} to denote whether a meal/ingredient is do-able/present.
     */
    public static final int MISSING = 0;

    /**
     Flag that represents partial completeness, used in both {@link #catagorizedMeals} and {@link #compare(Meal)} to denote whether a meal/ingredient is do-able/present.
     */
    public static final int PARTIAL = 1;

    /**
     Flag that represents full completeness, used in both {@link #catagorizedMeals} and {@link #compare(Meal)} to denote whether a meal/ingredient is do-able/present.
     */
    public static final int FULL = 2;

    /**
     * Holds the list of selected meals to be searched, loaded through constructor
     */
    private List<Meal> mealList;

    /**
    Holds ingredients that user has access to, loaded by the constructor
     */
    private HashMap<String, Ingredient> availableIngredients;

    /**
    Organizes meals pulled from {@link com.example.abyte.database.daos.MealDAO} by the levels of "do-able-ility".
     Used to provide results for the UI.
     <br> Filled by {@link #categorizeMeal(String)}
     <br> Keys - Uses number flags from above to denote completeness.
     <br> Values - Holds lists of meal names, used to reference the checked ingredient values in {@link #checkedMeals}
     */
    private HashMap<Integer, List<String>> catagorizedMeals;

    /**
     * Holds information relating to each meal that was compared with {@link #compare(Meal)}
     * <br> Keys - Uses meal names to access the ingredient comparisons
     * <br> Values - Hashmaps that are contain info about which ingredients are present or missing from each meal, 
     */
    private HashMap<String, HashMap<Integer, List<Ingredient>>> checkedMeals;


    /**
     * Constructs a Search class
     * @param mealList This is the selected list of meals that wish to be compared to the ingredient list
     * @param availableIngredients This is the list of ingredients the user has at their disposal
     */
    public Search(List<Meal> mealList, HashMap<String, Ingredient> availableIngredients){
        this.mealList = mealList;
        this.availableIngredients = availableIngredients;
        catagorizedMeals = new HashMap<>();
        catagorizedMeals.put(FULL, new ArrayList<>());
        catagorizedMeals.put(PARTIAL, new ArrayList<>());
        catagorizedMeals.put(MISSING, new ArrayList<>());
        checkedMeals = new HashMap<>();
    }

    /**
     * Iterates through the {@link #mealList} and calls {@link #search} for each meal present
     */
    public void searchAll() {
        for (Meal meal: mealList) {
            search(meal);
        }
    }

    /**
     * Takes a {@link Meal} and saves relevant information to be accessed through the meals activity
     * <br> Compares a meal using {@link #compare(Meal)} to store information of which ingredients are present for the selected meal
     * <br> Uses the compared meal and then sorts it into one of 3 catagories, not possible, partially do-able, and fully do-able using {@link #categorizeMeal(String)}
     * @param meal The meal to be compared the {@link #availableIngredients}
     */
    public void search(Meal meal){
        HashMap<Integer, List<Ingredient>> map = compare(meal);
        checkedMeals.put(meal.getMealName(),map);
        categorizeMeal(meal.getMealName());

    }

    /**
     * Compares an {@link Meal} ingredient list to the preloaded {@link #availableIngredients}
     * and assigns them a value in a HashMap that represents the availability of each ingredient
     * @param meal The meal that is to be compared to the available ingredients
     * @return A Hashmap organizing which ingredients are missing
     */
    public HashMap<Integer, List<Ingredient>> compare(Meal meal) {
        if (meal == null) {
            System.err.println("ERROR AT COMPARE: Meal is null, problem inserting meal");
            return null;
        }
        List<Ingredient> missingIngredientList = new ArrayList<>();
        List<Ingredient> partialIngredientList = new ArrayList<>();
        List<Ingredient> fullIngredientList = new ArrayList<>();

        Ingredient[] ingredientArray = meal.getIngredientArray();

        //Goes through meal requirements and categorizes ingredients on a scale
        for (Ingredient i : ingredientArray) {
            boolean isImportant = i.isImportant();

            //If ingredient is not in user's collection, mark as missing, otherwise check if theres enough
            if (!availableIngredients.containsKey(i.getName())) {
                missingIngredientList.add(i);
                continue;
            }

            double userIngWeight = availableIngredients.get(i.getName()).getAmount();
            double currIngWeight = i.getAmount();
            int compare = Double.compare(userIngWeight, currIngWeight);
            if(userIngWeight == 0.0 && isImportant) {
                missingIngredientList.add(i);
            } else if (compare < 0){ //If user doesn't have enough of the ingredient, mark it as partial
                partialIngredientList.add(i);
            } else { //If it reached this point, its all good
                fullIngredientList.add(i);
            }
        }

        HashMap<Integer, List<Ingredient>> ingredMap = new HashMap<>();
        ingredMap.put(Search.MISSING, missingIngredientList);
        ingredMap.put(Search.PARTIAL, partialIngredientList);
        ingredMap.put(Search.FULL, fullIngredientList);
        return ingredMap;
    }

    /**
     * Checks a meal that was compared to the {@link #catagorizedMeals}, flags meal based on whether or not it is missing ingredients
     * <br> - If a main ingredient is missing, marked as uncompletable
     * <br> - If there are any main ingredients partially available and or small aspects missing, mark as partially completable
     * <br> - If all ingredients are present, mark as ready to make
     * @param mealName The name of the meal that has previously been compared and ready for categorizing
     * @return Returns false if there was an error mid program and meal name was not added to {@link #catagorizedMeals}, otherwise true
     */
    public boolean categorizeMeal(String mealName){
        if(checkedMeals == null){
            System.err.println("ERROR AT CATEGORIZE MEAL: The categorizedMeals Hashmap is null, there was problem instantiated search class");
            return false;
        } else if (checkedMeals.isEmpty()) {
            System.err.println("ERROR AT CATEGORIZE MEAL: The categorizedMeals Hashmap is empty, problem comparing meal");
            return false;
        } else if (!checkedMeals.containsKey(mealName)){
            System.err.println("ERROR AT CATEGORIZE MEAL: The Meal Name \"" + mealName + "\" is not in the categorizedMeals Hashmap");
            return false;
        }

        List<Ingredient> currList;
        HashMap<Integer, List<Ingredient>> currMealMap = checkedMeals.get(mealName);

        if (!currMealMap.containsKey(MISSING)) {
            System.err.println("ERROR AT CATEGORIZE MEAL: There is no entry for MISSING");
            return false;
        } else if (!currMealMap.containsKey(PARTIAL)) {
            System.err.println("ERROR AT CATEGORIZE MEAL: There is no entry for PARTIAL");
            return false;
        } if (!currMealMap.containsKey(FULL)) {
            System.err.println("ERROR AT CATEGORIZE MEAL: There is no entry for FULL");
            return false;
        }


        System.out.println("Categorizing meal " + mealName + ": " + currMealMap.toString());
        currList = currMealMap.get(MISSING);
        assert currList != null;
        for (Ingredient i : currList) {
            if (i.isImportant()) {
                catagorizedMeals.get(MISSING).add(mealName);
                return true;
            } else {
                catagorizedMeals.get(PARTIAL).add(mealName);
                return true;
            }
        }

        currList = currMealMap.get(PARTIAL);
        assert currList != null;
        if (!currList.isEmpty()) {
            catagorizedMeals.get(PARTIAL).add(mealName);
            return true;
        }

        catagorizedMeals.get(FULL).add(mealName);
        return true;
    }

    public List<Meal> getMealList() {
        return mealList;
    }

    public HashMap<String, Ingredient> getAvailableIngredients() {
        return availableIngredients;
    }

    public HashMap<Integer, List<String>> getCategorizedMeals() {
        return catagorizedMeals;
    }

    public HashMap<String, HashMap<Integer, List<Ingredient>>> getCheckedMeals() {
        return checkedMeals;
    }
}
