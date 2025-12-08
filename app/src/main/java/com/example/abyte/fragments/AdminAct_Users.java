package com.example.abyte.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.abyte.R;
import com.example.abyte.database.entities.User;
import com.example.abyte.database.repositories.UserRepository;
import java.util.HashSet;
import java.util.Set;

public class AdminAct_Users extends Fragment{
    private static final String PREFS_NAMES= "admin_flag_prefs";
    private static final String FLAGGED_USERS_KEY = "Flagged_Users";
    private UserRepository repository;
    private EditText newUserName;
    private EditText newUserPassword;
    private EditText newUserSecurityKey;
    private EditText deleteUserName;
    private EditText flagUserName;
    private EditText unflagUserName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstance){
        return inflater.inflate(R.layout.admin_users, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        repository = UserRepository.getRepository(requireActivity().getApplication());
        newUserName = view.findViewById(R.id.adminNewUserNameEdit);
        newUserPassword = view.findViewById(R.id.adminNewUserPasswordEdit);
        newUserSecurityKey = view.findViewById(R.id.adminNewUserSecurityKeyEdit);
        deleteUserName = view.findViewById(R.id.adminDeleteUserNameEdit);
        flagUserName = view.findViewById(R.id.adminFlagUserNameEdit);
        unflagUserName = view.findViewById(R.id.adminUnflagUserNameEdit);

        Button addUser = view.findViewById(R.id.adminAddUser);
        Button deleteUser = view.findViewById(R.id.adminDeleteUser);
        Button flagUser = view.findViewById(R.id.adminFlagUser);
        Button unflagUser = view.findViewById(R.id.adminUnflagUser);
        Button back = view.findViewById(R.id.adminUserBackButton);

        addUser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View  v){
                addUser();
            }
        });
        deleteUser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View  v){
                deleteUser();
            }
        });
        flagUser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View  v){
                flagUser();
            }
        });
        unflagUser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View  v){
                unflagUser();
            }
        });
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View  v){
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
    private void addUser(){
        String username = newUserName.getText().toString().trim();
        String password = newUserPassword.getText().toString().trim();
        String securityKey = newUserSecurityKey.getText().toString().trim();

        if(username.isEmpty() || password.isEmpty() || securityKey.isEmpty()){
            toast("All fields are required");
            return;
        }

        User user = new User(username, password, securityKey, true);
        repository.insertUser(user);
        toast("Added Admin: " + username);
        newUserName.setText("");
        newUserPassword.setText("");
        newUserSecurityKey.setText("");
    }
    private void deleteUser(){
        String username = deleteUserName.getText().toString().trim();

        if(username.isEmpty()){
            toast("Enter a username to delete");
            return;
        }
        if(username.equalsIgnoreCase("admin1")){
            toast("Admin cannot be eliminated");
            return;
        }
        repository.deleteUserByUsername(username);
        Set<String> flagged = getFlaggedUsers();
        if(flagged.contains(username)){
            flagged.remove(username);
            saveFlaggedUser(flagged);
        }
        toast("Delete requested for:" + username);
        deleteUserName.setText("");
    }
    private void flagUser(){
        String username = flagUserName.getText().toString().trim();
        if(username.isEmpty()){
            toast("Enter a username to flag");
            return;
        }
        Set<String> flagged = getFlaggedUsers();
        if(flagged.contains(username)){
            toast("User already flagged");
        }else{
            flagged.add(username);
            saveFlaggedUser(flagged);
            toast("Flagged: " + username);
        }
        flagUserName.setText("");
    }
    private void unflagUser(){
        String username = unflagUserName.getText().toString().trim();
        if(username.isEmpty()){
            toast("Enter a username to unflag");
            return;
        }
        Set<String> flagged = getFlaggedUsers();
        if(flagged.contains(username)){
            flagged.remove(username);
            saveFlaggedUser(flagged);
            toast("Unflagged: " + username);
        }else{
            toast("User is not flagged");
        }
        unflagUserName.setText("");
    }
    private Set<String> getFlaggedUsers(){
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAMES, Context.MODE_PRIVATE);
        Set<String> stored = prefs.getStringSet(FLAGGED_USERS_KEY, null);
        if(stored == null){
            return new HashSet<String>();
        }
        return new HashSet<String>(stored);
    }
    private void saveFlaggedUser(Set<String> flagged){
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAMES, Context.MODE_PRIVATE);
        prefs.edit().putStringSet(FLAGGED_USERS_KEY, flagged).apply();
    }
    private void toast(String text){
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }
}