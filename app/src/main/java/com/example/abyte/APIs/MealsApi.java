package com.example.abyte.APIs;

import com.example.abyte.APIs.models.CategoryResponse;
import com.example.abyte.APIs.models.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealsApi {
    //API: https://www.themealdb.com/api.php

    //Filter by main ingredient
    @GET("filter.php")
    Call<MealResponse> filterByIngredient(@Query("i") String ingredient);

    //Lookup full meal details by id
    @GET("lookup.php")
    Call<MealResponse> getMealById(@Query("i") String mealId);

    //List all Categories
    @GET("list.php?c=list")
    Call<CategoryResponse> getAllCategories();

    //Filter by Category
    @GET("filter.php")
    Call<MealResponse> filterByCategory(@Query("c") String category);

    //Lookup a single random meal
    @GET("random.php")
    Call<MealResponse> getRandomMeal();

    //premium

    //Filter by multi-ingredient
    @GET("filter.php")
    Call<MealResponse> filterByIngredients(@Query("i") String ingredients);

    //Latest Meals
    @GET("latest.php")
    Call<MealResponse> getLatestMeals();

    //Lookup a selection of 10 random meals
    @GET("randomselection.php")
    Call<MealResponse> getRandomSelection();

    //used to get the meal amount for stats
    @GET("search.php")
    Call<MealResponse> searchMeals(@Query("s") String query);
}
