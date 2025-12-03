package com.example.abyte.database.entities;

import java.util.Objects;

public class Ingredient {
    //    static final String[] POSSIBLE_MEASUREMENTS = {"oz", "lbs", "tsp", "tbs", "cups"};
//    static final String[] POSSIBLE_INGREDIENTS = {"chicken", "beef", };
    private String name;

    /*
    Holds type of measurement ingredient is measured in (Ex: oz, lbs, cups, tbs, etc.)
    Used in tandom to amount to give representation of how much of the ingredient is available/required
     */
    private String weight;
    /*
    Hold the numbered amount of the ingredient
    Used in tandom to weight to give give representation of how much is available/required
     */
    private double amount;

    public Ingredient(String name, String weight, double amount) {
        this.name = name;
        this.weight = weight;
        this.amount = amount;
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

    public double isAmount() {
        return amount;
    }

    public void setAmount(double amount) {
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
}
