package com.example.abyte;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import com.example.abyte.database.entities.User;
import com.example.abyte.databinding.ActivityMainBinding;
import com.example.abyte.database.repositories.UserRepository;
import com.example.abyte.fragments.AdminAct_Settings;
//
public class MainActivity extends BaseActivity {
    private static final String MAIN_ACTIVITY_USER_ID="com.example.abyte.MAIN_ACTIVITY_USER_ID";
    static final String SAVED_INSTANCE_STATE_USERID_KEY="com.example.abyte.SAVED_INSTANCE_STATE_USERID_KEY";
    private static final int LOGGED_OUT=-1;
    private ActivityMainBinding binding;
    private UserRepository repository;
    public static final String TAG="DAC_BYTE";
    private int loggedinuserid=-1;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        String themeName = prefs.getString("app_theme", "AppTheme");
        int themeResId = getResources().getIdentifier(themeName, "style", getPackageName());
        setTheme(themeResId);

        SharedPreferences prefs2 = getSharedPreferences("prefs2", MODE_PRIVATE);
        boolean themeOff = prefs2.getBoolean("theme_off", true);
        String themeName2 = themeOff ? "ThemeWhite" : prefs2.getString("app_theme", "AppTheme");
        int themeResId2 = getResources().getIdentifier(themeName2, "style", getPackageName());
        setTheme(themeResId2);



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs3 = getSharedPreferences("prefs3", MODE_PRIVATE);
        int fontSize = prefs3.getInt("font_size", 16); // default 16sp

        TextView myText = findViewById(R.id.byteLabelTextView);
        myText.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);

        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository=UserRepository.getRepository(getApplication());
        loginUser(savedInstanceState);



        if(loggedinuserid==-1){
            Intent intent=LoginActivity.loginIntentFactory(getApplication());
            startActivity(intent);
            finish();
        }
        updateSharedPreference();


        binding.byteCreateMealsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bing to create_meals.xml
                Toast.makeText(MainActivity.this,"first button",Toast.LENGTH_SHORT).show();
            }
        });
        binding.byteFindMealsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bing to find_meals.xml
                Toast.makeText(MainActivity.this,"second button",Toast.LENGTH_SHORT).show();
            }
        });
        binding.byteSavedMealsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bing to saved_meals.xml
                Toast.makeText(MainActivity.this,"third button",Toast.LENGTH_SHORT).show();
            }
        });

        Button themeButton = findViewById(R.id.byteThemesButton);

        themeButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ThemeActivity.class);
            startActivity(intent);
        });

        ImageButton settingsButton = findViewById(R.id.byteSettingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open settings screen or perform your action
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
        binding.isadminImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.byteLabelTextView.setVisibility(View.GONE);
                binding.byteCreateMealsButton.setVisibility(View.GONE);
                binding.byteFindMealsButton.setVisibility(View.GONE);
                binding.byteSavedMealsButton.setVisibility(View.GONE);
                binding.byteThemesButton.setVisibility(View.GONE);
                binding.byteSettingsButton.setVisibility(View.GONE);
                binding.logDisplayRecyclerView.setVisibility(View.GONE);

                findViewById(R.id.fragmentContainer).setVisibility(View.VISIBLE);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                        new com.example.abyte.AdminActivity()).addToBackStack("AdminHome").
                        commit();
            }
        });


        getSupportFragmentManager().addOnBackStackChangedListener(
                new androidx.fragment.app.FragmentManager.OnBackStackChangedListener(){
                    @Override
                    public void onBackStackChanged(){
                        int count = getSupportFragmentManager().getBackStackEntryCount();

                        if(count == 0){

                            binding.byteLabelTextView.setVisibility(View.VISIBLE);
                            binding.byteCreateMealsButton.setVisibility(View.VISIBLE);
                            binding.byteFindMealsButton.setVisibility(View.VISIBLE);
                            binding.byteSavedMealsButton.setVisibility(View.VISIBLE);
                            binding.byteThemesButton.setVisibility(View.VISIBLE);
                            binding.byteSettingsButton.setVisibility(View.VISIBLE);
                            binding.logDisplayRecyclerView.setVisibility(View.VISIBLE);

                            findViewById(R.id.fragmentContainer).setVisibility(View.GONE);
                        }
                    }
                }
        );
    }
    private void loginUser(Bundle savedInstanceState){
        SharedPreferences sharedPreferences= getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        loggedinuserid= sharedPreferences.getInt(getString(R.string.preference_userId_key),LOGGED_OUT);
        if(loggedinuserid==LOGGED_OUT & savedInstanceState!=null && savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USERID_KEY)){
            loggedinuserid= savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY,LOGGED_OUT);
        }
        if(loggedinuserid==LOGGED_OUT){
            loggedinuserid=getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID,LOGGED_OUT);
        }
        if(loggedinuserid==LOGGED_OUT){
            return;
        }
        LiveData<User> userObserver=repository.getUserByUserId(loggedinuserid);
        userObserver.observe(this,user -> {
            this.user=user;
            if(user!=null){
                invalidateOptionsMenu();
                ImageButton adminButton=findViewById(R.id.isadminImageButton);
                if(user.isAdmin()){
                    adminButton.setVisibility(View.VISIBLE);
                }else{
                    adminButton.setVisibility(View.INVISIBLE);
                }
                SharedPreferences adminPrefs = getSharedPreferences("admin_settings_prefs",
                        MODE_PRIVATE);
                boolean maintenanceOn = adminPrefs.getBoolean(AdminAct_Settings.
                        KEY_MAINTENANCE_MODE, false);

                if(maintenanceOn && !user.isAdmin()){
                    new AlertDialog.Builder(MainActivity.this).setTitle("Maintenance Mode").
                            setMessage("The system is under maintenance. You are being logged out.")
                            .setCancelable(false).setPositiveButton("OK",
                                    new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which){
                                    logout();
                                    Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
                                    startActivity(intent);
                                    finish();
                                }
                            }).show();
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_INSTANCE_STATE_USERID_KEY,loggedinuserid);
        updateSharedPreference();
    }
//recycler tools
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.logout_menu,menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item= menu.findItem(R.id.logoutMenuItem);
        item.setVisible(true);
        if(user==null){
            return false;
        }
        item.setTitle(user.getUsername());
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                showLogoutDialog();
                return false;
            }
        });
        return true;
    }

    private void showLogoutDialog(){
        AlertDialog.Builder alertBuilder= new AlertDialog.Builder(MainActivity.this);
        final AlertDialog alertDialog= alertBuilder.create();
        alertBuilder.setMessage("Logout?");
        alertBuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });
        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertBuilder.create().show();
    }

    private void logout() {
        loggedinuserid=LOGGED_OUT;
        updateSharedPreference();
        getIntent().putExtra(MAIN_ACTIVITY_USER_ID,loggedinuserid);
        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    }

    private void updateSharedPreference(){
        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences(
                getString(R.string.preference_file_key)
                ,Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor=sharedPreferences.edit();
        sharedPrefEditor.putInt(getString(R.string.preference_userId_key),loggedinuserid);
        sharedPrefEditor.apply();
    }

    static Intent mainActivityIntentFactory(Context context, int userID){
        Intent intent= new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID,userID);
        return intent;
    }

}