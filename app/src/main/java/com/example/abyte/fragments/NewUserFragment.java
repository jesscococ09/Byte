package com.example.abyte.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abyte.R;
import com.example.abyte.database.entities.User;
import com.example.abyte.database.repositories.UserRepository;


public class NewUserFragment extends Fragment {
    private UserRepository repository;
    private EditText userNameField;
    private EditText passwordField;
    private EditText securityKeyField;
    private Button signUpButton;

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        repository= UserRepository.getRepository(requireActivity().getApplication());
        userNameField=view.findViewById(R.id.newUserNameEditText);
        passwordField=view.findViewById(R.id.newUserPasswordEditText);
        securityKeyField=view.findViewById(R.id.newUserSecurityKeyEditText);
        signUpButton=view.findViewById(R.id.newUserSubmitButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_user, container, false);
    }

    private void createUser(){
        String username= userNameField.getText().toString().trim();
        String password= passwordField.getText().toString().trim();
        String securityKey= securityKeyField.getText().toString().trim();
        if(username.isEmpty() || password.isEmpty() || securityKey.isEmpty()){
            toastMaker("All fields must be filled!");
            return;
        }
        User newUser= new User(username,password,securityKey);
        repository.insertUser(newUser);
        toastMaker("New User Created!");
        requireActivity().getSupportFragmentManager().popBackStack();
    }
    private void toastMaker(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }
}