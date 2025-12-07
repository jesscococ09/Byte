package com.example.abyte;

import com.example.abyte.database.entities.Ingredient;
import com.example.abyte.database.entities.Meal;

import java.util.HashMap;

public class ExampleMeals {
    public static Meal spaghettiWithSauce = new Meal(
            1,
            "Spaghetti w/ Tomato Sauce",
            new Ingredient[]{
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
                    new Ingredient("Spaghetti", "oz", 12.0, true)},
            "https://www.allrecipes.com/recipe/11715/easy-spaghetti-with-tomato-sauce/",
            R.drawable.spaghetti,
            0,
            false);

    public static Meal porkchop = new Meal(
            2,
            "Pan Fried PorkChop",
            new Ingredient[] {
                    new Ingredient("Pork Chop", "pieces", 7.0, true),
                    new Ingredient("All-Purpose Flour", "cups", 1.0, true),
                    new Ingredient("Seasoned Salt", "tsp", 1.0, false),
                    new Ingredient("Black Pepper", "tsp", 1.0, false),
                    new Ingredient("Cayenne Pepper", "to taste", 0.0, false),
                    new Ingredient("Canola Oil", "cups", 0.5, false),
                    new Ingredient("Butter", "tbsp", 1.0, false),
                    new Ingredient("Extra Salt and Pepper", "to taste", 0.0, false)},
            "https://www.thepioneerwoman.com/food-cooking/recipes/a9893/simple-pan-fried-pork-chops/",
            R.drawable.porkchop,
            0,
            false);

    public static Meal joke1 = new Meal(
            3,
            "Carrot Cat",
            new Ingredient[] {
                    new Ingredient("Cat", "thing", 1, true),
                    new Ingredient("Carrot", "thing", 1.0, true)},
            "None",
            R.drawable.cat,
            0,
            false);

    public static Meal joke2 = new Meal(
            3,
            "Carrot Cat",
            new Ingredient[] {
                    new Ingredient("Food", "thing", 1, true)},
            "None",
            R.drawable.spilled,
            0,
            false);
}
