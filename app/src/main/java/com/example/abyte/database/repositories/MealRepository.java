package com.example.abyte.database.repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.abyte.MainActivity;
import com.example.abyte.database.ByteDatabase;
import com.example.abyte.database.daos.MealDAO;
import com.example.abyte.database.entities.Meal;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MealRepository {
    private final MealDAO mealDAO;
    private static MealRepository repository;

    private MealRepository(Application application) {
        ByteDatabase db = ByteDatabase.getDatabase(application);
        this.mealDAO = db.mealDAO();
    }

    public static MealRepository getRepository(Application application) {
        if(repository != null) {
            return repository;
        }

        Future<MealRepository> future = ByteDatabase.databaseWriteExecutor.submit(
                new Callable<MealRepository>() {
                    @Override
                    public MealRepository call() throws Exception {
                        return new MealRepository(application);
                    }
                }
        );

        try {
            repository = future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i(MainActivity.TAG, "Problem getting MealRepositoryNOTUSE, thread error");
        }
        return repository;
    }

    public void insertMeal(Meal... meal) {
        ByteDatabase.databaseWriteExecutor.execute(()->{
            mealDAO.insert(meal);
        });
    }

    public LiveData<List<Meal>> getAllMeals() {
        return mealDAO.getAllMeals();
    }

    public LiveData<List<Meal>> getMealsByIngredient(String ingredient) {
        return mealDAO.getMealsByIngredient(ingredient);
    }

    public LiveData<Meal> getMealByName(String mealName) {
        return mealDAO.getMealByName(mealName);
    }

    public LiveData<Meal> getMealById(int mealID) {
        return mealDAO.getMealById(mealID);
    }

    public void deleteAll(){
        mealDAO.deleteAll();
    }

    public void deleteMealByName(String mealName) {
        mealDAO.deleteMealByName(mealName);
    }

    public void deleteMealById(int mealId) {
        mealDAO.deleteMealById(mealId);
    }
}
