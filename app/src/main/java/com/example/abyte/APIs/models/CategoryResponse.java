package com.example.abyte.APIs.models;

import java.util.List;

public class CategoryResponse {
    public List<Category> meals;

    public List<Category> getCategories() {
        return meals;
    }

    public void setMeals(List<Category> categories ){
        this.meals = categories;
    }
}
