package com.example.abyte.APIs;

import com.example.abyte.APIs.models.CategoryResponse;
import com.example.abyte.APIs.models.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealsApi {
    //API: https://www.themealdb.com/api.php

    @GET("filter.php")
    Call<MealResponse> filterByIngredient(@Query("i") String ingredient);

    @GET("lookup.php")
    Call<MealResponse> getMealById(@Query("i") String mealId);

    @GET("list.php?c=list")
    Call<CategoryResponse> getAllCategories();

    @GET("filter.php")
    Call<MealResponse> filterByCategory(@Query("c") String category);

    @GET("random.php")
    Call<MealResponse> getRandomMeal();

}
