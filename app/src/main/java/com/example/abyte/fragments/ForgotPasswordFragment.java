package com.example.abyte.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abyte.R;
import com.example.abyte.database.entities.User;
import com.example.abyte.database.repositories.UserRepository;

import java.util.Locale;


public class ForgotPasswordFragment extends Fragment {

    private UserRepository repository;
    private int securityAttempts=0;
    private static final int MAX_ATTEMPTS=5;
    private static final int COOL_DOWN=(5*60*1000);
    private Button resetButton;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        repository=UserRepository.getRepository(requireActivity().getApplication());
        resetButton=view.findViewById(R.id.forgotPasswordSubmitButton);
        EditText userNameField= view.findViewById(R.id.userNameForgotEditText);
        EditText keyField=view.findViewById(R.id.securityKeyEditText);
        Button backButton=view.findViewById(R.id.backToLoginButton);

        resetButton.setOnClickListener(v -> verifyUser(userNameField,keyField));
        backButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }
    private void verifyUser(EditText userNameField, EditText keyField){
        String username= userNameField.getText().toString().trim();
        String key= keyField.getText().toString().trim();
        if (username.isEmpty()){
            toastMaker("Must fill in Username");
            return;
        }if(key.isEmpty()){
            toastMaker("Must fill in Security Key");
            return;
        }
        LiveData<User> userObserver= repository.getUserByUserName(username);
        userObserver.removeObservers(getViewLifecycleOwner());
        userObserver.observe(getViewLifecycleOwner(), user -> {
            if(user!=null){
                if(key.equals(user.getSecurityKey())){
                    toastMaker("Password: "+user.getPassword());
                    securityAttempts=0;
                }else{
                    securityAttempts++;
                    toastMaker("Invalid security key");
                    keyField.setText("");
                    if(securityAttempts>=MAX_ATTEMPTS){
                        resetButton.setEnabled(false);
                        toastMaker("Too many attempts. Locked for 5 min");
                        startCooldownTimer();
                    }
                }
            }else {
                toastMaker("Username not found!");
                userNameField.setText("");
            }
        });
    }
    private void startCooldownTimer(){
        new CountDownTimer(COOL_DOWN,1000){
            @Override
            public void onTick(long millisUntilFinished){
                long sec=millisUntilFinished/1000;
                long min=sec/60;
                long rem=sec%60;
                resetButton.setText(String.format(Locale.US,"Locked %d:%02d", min, rem));
            }
            @Override
            public  void onFinish(){
                securityAttempts=0;
                resetButton.setEnabled(true);
                toastMaker("Cool Down Complete");
            }

        }.start();
    }
    private void toastMaker(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }
}