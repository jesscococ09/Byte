package com.example.abyte.APIs;

import com.example.abyte.APIs.models.Favorite;
import com.example.abyte.APIs.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SupabaseApi {
    @GET("recipes")
    Call<List<Recipe>> getRecipes(@Query("select") String select,@Query("count") String count);

    @POST("recipes")
    Call<Recipe> addRecipe(@Body Recipe recipe);

    @DELETE("recipes")
    Call<Void> deleteRecipe(@Query("id") int recipeId);

    @POST("favorites")
    Call<Favorite> addFavorite(@Body Favorite favorite);

    @GET("favorites")
    Call<List<Favorite>> getFavorites(@Query("user_id") int userId, @Query("select") String select);

    @DELETE("favorites")
    Call<Void> deleteFavorite(@Query("id") int favoriteId);
}
