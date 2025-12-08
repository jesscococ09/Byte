package com.example.abyte.database.repositories;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.LiveData;
import com.example.abyte.MainActivity;
import com.example.abyte.database.ByteDatabase;
import com.example.abyte.database.daos.UserDAO;
import com.example.abyte.database.entities.User;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class UserRepository {
    private final UserDAO userDAO;
    private static UserRepository repository;

    private UserRepository(Application application){
        ByteDatabase db= ByteDatabase.getDatabase(application);
        this.userDAO=db.userDAO();


    }
    public static UserRepository getRepository(Application application){
        if(repository!=null){
            return repository;
        }
        Future<UserRepository> future= ByteDatabase.databaseWriteExecutor.submit(
                new Callable<UserRepository>() {
                    @Override
                    public UserRepository call() throws Exception {
                        return new UserRepository(application);
                    }
                }
        );
        try{
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            Log.i(MainActivity.TAG,"Problem getting UserRepository, thread error");
        }
        return null;
    }
    public void insertUser(User... user){
        ByteDatabase.databaseWriteExecutor.execute(()->{
            userDAO.insert(user);
        });
    }
    public LiveData<User> getUserByUserName(String username){
        return userDAO.getUserByUserName(username);
    }
    public LiveData<User> getUserByUserId(int userId){
        return userDAO.getUserByUserId(userId);
    }

    public LiveData<List<User>> getAllUsers(){
        return userDAO.getAllUsers();
    }

    public void deleteUserByUsername(final String username){
        ByteDatabase.databaseWriteExecutor.execute(new Runnable(){
            @Override
            public void run(){
                userDAO.deleteUserByUsername(username);
            }
        });
    }
}
