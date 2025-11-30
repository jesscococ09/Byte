package com.example.abyte;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.abyte.database.entities.User;
import com.example.abyte.databinding.ActivityLoginBinding;
import com.example.abyte.database.repositories.UserRepository;
import com.example.abyte.fragments.ForgotPasswordFragment;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    private UserRepository repository;
    private int loginAttempts=0;
    private static final int MAX_ATTEMPTS=5;
    private static final int COOL_DOWN=(5*60*1000);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository=UserRepository.getRepository(getApplication());
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyUser();
            }
        });
        binding.forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.titleLoginTextView.setVisibility(View.GONE);
                binding.userNameLoginEditText.setVisibility(View.GONE);
                binding.passwordLoginEditText.setVisibility(View.GONE);
                binding.loginButton.setVisibility(View.GONE);
                binding.forgotPasswordTextView.setVisibility(View.GONE);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentFrameLayout, new ForgotPasswordFragment())
                        .addToBackStack(null).commit();
            }
        });
        getSupportFragmentManager().addOnBackStackChangedListener(()->{
            boolean hasFragment=getSupportFragmentManager().getBackStackEntryCount()>0;
            int loginVisibility=hasFragment ? View.GONE:View.VISIBLE;
            binding.titleLoginTextView.setVisibility(loginVisibility);
            binding.userNameLoginEditText.setVisibility(loginVisibility);
            binding.passwordLoginEditText.setVisibility(loginVisibility);
            binding.loginButton.setVisibility(loginVisibility);
            binding.forgotPasswordTextView.setVisibility(loginVisibility);
        });
    }
    private void verifyUser(){
        if(loginAttempts>=MAX_ATTEMPTS){
            toastMaker("Login disabled. Please wait 5 min then Try Again.");
            return;
        }
        String username=binding.userNameLoginEditText.getText().toString();
        if(username.isEmpty()){
            toastMaker("Username may not be blank");
            return;
        }
        LiveData<User> userObserver=repository.getUserByUserName(username);
        userObserver.observe(this,user -> {
            if(user!=null){
                String password=binding.passwordLoginEditText.getText().toString();
                if(password.equals(user.getPassword())){
                    startActivity(MainActivity.mainActivityIntentFactory(getApplication(),user.getUserId()));
                }else{
                    loginAttempts++;
                    toastMaker("Invalid password");
                    if(loginAttempts>=MAX_ATTEMPTS){
                        binding.loginButton.setEnabled(false);
                        toastMaker("Login Disabled! Please wait 5 min");
                        startCooldownTimer(binding.loginButton);
                    }
                    binding.passwordLoginEditText.setSelection(0);
                }
            }else{
                toastMaker(String.format("%s is not a valid username",username));
                binding.userNameLoginEditText.setSelection(0);
            }
        });
    }
    private void startCooldownTimer(Button button){
        new CountDownTimer(COOL_DOWN,1000){
            @Override
            public void onTick(long millisUntilFinished){
                long sec=millisUntilFinished/1000;
                long min=sec/60;
                long rem=sec%60;
                button.setText(String.format(Locale.US,"Locked %d:%02d", min, rem));
            }
            @Override
            public  void onFinish(){
                loginAttempts=0;
                button.setEnabled(true);
                button.setText(getString(R.string.login));
            }
        }
                .start();
    }
    private void toastMaker(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
    static Intent loginIntentFactory(Context context){
        return new Intent(context, LoginActivity.class);
    }
}