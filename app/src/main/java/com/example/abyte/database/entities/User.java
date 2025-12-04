package com.example.abyte.database.entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.abyte.database.ByteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Entity(tableName = ByteDatabase.USER_TABLE)
public class User {
    @PrimaryKey(autoGenerate = true)
    private int userId;
    private String username;
    private String password;
    private boolean isAdmin;

    //TODO NOT FINAL** Needs testing
    //Intentionally is not included in hashcode and equals methods
    private HashMap<String, Ingredient> availableIngredients;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        isAdmin=false;
        availableIngredients = new HashMap<>();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId && isAdmin == user.isAdmin && Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, password, isAdmin);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public HashMap<String, Ingredient> getAvailableIngredients() {
        return availableIngredients;
    }

    public void setAvailableIngredients(HashMap<String, Ingredient> newIngrList){
        availableIngredients = newIngrList;
    }
}

