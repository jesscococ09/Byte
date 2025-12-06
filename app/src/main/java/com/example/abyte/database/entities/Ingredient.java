package com.example.abyte.database.entities;

import java.util.Objects;

public class Ingredient {
    //    static final String[] POSSIBLE_MEASUREMENTS = {"oz", "lbs", "tsp", "tbs", "cups"};
//    static final String[] POSSIBLE_INGREDIENTS = {"chicken", "beef", };
    private String name;

    /**
    Holds type of measurement ingredient is measured in (Ex: oz, lbs, cups, tbs, etc.)
    Used in tandom to amount to give representation of how much of the ingredient is available/required
     */
    private String weight;
    /**
    Hold the numbered amount of the ingredient
    Used in tandom to weight to give give representation of how much is available/required
     */
    private double amount;

    /**
     * Represents if the ingredient can be missed in its recipe, case by case basis
     * EX: Chicken in most recipes is a main part of the recipe, but you can substitute spices
     */
    private boolean isImportant;

    public Ingredient(String name, String weight, double amount, boolean isImportant) {
        this.name = name;
        this.weight = weight;
        if(amount < 0)
            this.amount = 0;
        else
            this.amount = amount;
        this.isImportant = isImportant;
    }

    /*
    Does not include numbered amount, used for make ingredient lookup more lenient
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Ingredient that = (Ingredient) o;
        return Objects.equals(weight, that.weight) && Objects.equals(name, that.name); //@TODO Revist
    }

    /*
    Does not include numbered amount, used for make ingredient lookup more lenient
     */
    @Override
    public int hashCode() {
        int result = Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(weight);
        //result = 31 * result + Boolean.hashCode(amount); @TODO Revist
        return result;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if (amount < 0)
            this.amount = 0;
        else
            this.amount = amount;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public void setImportant(boolean important) {
        isImportant = important;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", weight='" + weight + '\'' +
                ", amount=" + amount +
                ", isImportant=" + isImportant +
                '}';
    }
}
