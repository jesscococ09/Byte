package com.example.abyte.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.abyte.R;

public class AdminAct_Settings extends Fragment {
    private static final String PREFS_NAME = "admin_settings_prefs";
    public static final String KEY_ALLOW_USERS = "allow_new_user_registration";
    public static final String KEY_MAINTENANCE_MODE = "maintenance_mode";
    private Switch allowUsersSwitch;
    private Switch maintenanceModeSwitch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.admin_settings, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        allowUsersSwitch = view.findViewById(R.id.allowUsersSwitch);
        maintenanceModeSwitch = view.findViewById(R.id.maintenanceModeSwitch);
        loadSettings();
        view.findViewById(R.id.settingsSaveButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                saveSettings();
                Toast.makeText(getContext(), "Settings Saved", Toast.LENGTH_SHORT).show();
            }
        });
        view.findViewById(R.id.adminBackButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
    private void loadSettings() {
        Context ctx = requireContext();
        SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean allowUsers = prefs.getBoolean(KEY_ALLOW_USERS, true);
        boolean maintenance = prefs.getBoolean(KEY_MAINTENANCE_MODE, false);
        allowUsersSwitch.setChecked(allowUsers);
        maintenanceModeSwitch.setChecked(maintenance);
    }

    private void saveSettings() {
        Context ctx = requireContext();
        SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        prefs.edit().putBoolean(KEY_ALLOW_USERS, allowUsersSwitch.isChecked()).
                putBoolean(KEY_MAINTENANCE_MODE, maintenanceModeSwitch.isChecked()).apply();
    }
}