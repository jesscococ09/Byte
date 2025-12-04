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
     Flag that represents missingness, used {@link #availableMeals} to denote "do-able-ility".
     */
    private final int NOT_AVAILABILE = 0;

    /**
     Flag that represents partial completeness, used {@link #availableMeals} to denote "do-able-ility".
     */
    private final int PARTIAL = 1;

    /**
     Flag that represents full completeness, used {@link #availableMeals} to denote "do-able-ility".
     */
    private final int FULL = 2;

    /**
     * Holds the list of selected meals to check for "complete-ability", loaded by the constructor
     */
    private List<Meal> mealList;

    /**
    Holds ingredients that user has access to, loaded by the constructor
     */
    private HashMap<String, Ingredient> availableIngredients;
    /**
    Organizes meals pulled from {@link com.example.abyte.database.daos.MealDAO} by the levels of "do-able-ility".
     Used to provide results for the UI.
     <br> Filled by {@link #checkCompleteness(String)}
     <br> Keys - Uses number flags from above to denote completeness.
     <br> Values - Holds lists of meal names, used to reference the checked ingredient values in {@link #checkedMeals}
     */
    private HashMap<Integer, List<String>> availableMeals;

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
        availableMeals = new HashMap<>();
        availableMeals.put(FULL, new ArrayList<>());
        availableMeals.put(PARTIAL, new ArrayList<>());
        availableMeals.put(NOT_AVAILABILE, new ArrayList<>());
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
     * <br> Uses the compared meal and then sorts it into one of 3 catagories, not possible, partially do-able, and fully do-able using {@link #checkCompleteness(String)}
     * @param meal The meal to be compared the {@link #availableIngredients}
     */
    public void search(Meal meal){
        HashMap<Integer, List<Ingredient>> map = compare(meal);
        checkedMeals.put(meal.getMealName(),map);
        checkCompleteness(meal.getMealName());

    }

    /**
     * Compares an {@link Meal} ingredient list to the preloaded {@link #availableIngredients}
     * and assigns them a value in a HashMap that represents the availability of each ingredient
     * @param meal The meal that is to be compared to the available ingredients
     * @return A Hashmap organizing which ingredients are missing
     */
    public HashMap<Integer, List<Ingredient>> compare(Meal meal) {
        HashMap<Integer, List<Ingredient>> ingredMap = new HashMap<>();
        ingredMap.put(-1, new ArrayList<Ingredient>());
        ingredMap.put(0, new ArrayList<Ingredient>());
        ingredMap.put(1, new ArrayList<Ingredient>());
        List<Ingredient> ingList = meal.getIngredientList();

        //Goes through meal requirements and categorizes ingredients on a scale
        for (Ingredient i : ingList) {

            //If ingredient is not available, mark as missing, otherwise check if theres enough
            if (availableIngredients.containsKey(i.getName())){
                double userIngWeight = availableIngredients.get(i.getName()).getAmount();
                double currIngWeight = i.getAmount();

                //If user doesn't have enough of the ingredient, mark it as partial
                if ((Double.compare(userIngWeight, currIngWeight) < 0)){
                    ingredMap.get(0).add(i);
                } else { //If it reached this point, its all good
                    ingredMap.get(1).add(i);
                }
            } else {
                ingredMap.get(-1).add(i);
            }
        }
        return ingredMap;
    }

    /**
     * Checks a meal that was compared to the {@link #availableIngredients}, flags meal based on whether or not it is missing ingredients
     * <br> - If a main ingredient is missing, marked as uncompletable
     * <br> - If there are any main ingredients partially available and or small aspects missing, mark as partially completable
     * <br> - If all ingredients are present, mark as ready to make
     * @param mealName The name of the meal that has previously been compared and ready for categorizing
     */
    public boolean checkCompleteness(String mealName){
        if(checkedMeals == null){
            System.out.println("ERROR AT CHECK COMPLETENESS: The checkedMeals Hashmap is null, there was problem instantiated search class");
            return false;
        } else if (checkedMeals.isEmpty()) {
            System.out.println("ERROR AT CHECK COMPLETENESS: The checkedMeals Hashmap is empty, problem comparing meal");
            return false;
        } else if (!checkedMeals.containsKey(mealName)){
            System.out.println("ERROR AT CHECK COMPLETENESS: The Meal Name to be checked for completeness is not presents or the name is entered incorrectly");
            return false;
        }

        List<Ingredient> currList;
        HashMap<Integer, List<Ingredient>> currMealMap = checkedMeals.get(mealName);

        if (!currMealMap.containsKey(NOT_AVAILABILE)) {
            System.out.println("ERROR AT CHECK COMPLETENESS: There is no entry for NOT_AVAILABLE");
        }
        currList = currMealMap.get(NOT_AVAILABILE);
        assert currList != null;
        if (!currList.isEmpty()) {
            //Iterates all through the missing ingredients, if a main ingredient is missing, flag recipe as not completable
            for (Ingredient cm : currList) {
                if (cm.isImportant()){
                    //TODO: Possibly remove excessive code here
                    availableMeals.get(NOT_AVAILABILE).add(mealName);
                    return true;
                }
            }
        }

        if (!currMealMap.containsKey(PARTIAL)) {
            System.out.println("ERROR AT CHECK COMPLETENESS: There is no entry for PARTIAL");
            return false;
        }
        currList = currMealMap.get(PARTIAL);
        //If there is at least 1 ingredient inside of PARTIAL, mark recipe as partially completable
        if (!currList.isEmpty()) {
            //TODO: Possibly remove excessive code here
            availableMeals.get(PARTIAL).add(mealName);
            return true;
        }

        //If none of the previous flags were tripped, then it must be fully completable
        availableMeals.get(FULL).add(mealName);
        return true;
    }

    public List<Meal> getMealList() {
        return mealList;
    }

    public HashMap<String, Ingredient> getAvailableIngredients() {
        return availableIngredients;
    }

    public HashMap<Integer, List<String>> getAvailableMeals() {
        return availableMeals;
    }

    public HashMap<String, HashMap<Integer, List<Ingredient>>> getCheckedMeals() {
        return checkedMeals;
    }
}
