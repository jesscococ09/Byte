package com.example.abyte.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.abyte.R;

import java.util.HashSet;
import java.util.Set;

public class AdminAct_Flagged extends Fragment{
    private static final String PREFS_NAMES= "admin_flag_prefs";
    private static final String FLAGGED_USERS_KEY = "Flagged_Users";
    private LinearLayout flaggedList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstance){
        return inflater.inflate(R.layout.admin_flagged, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        flaggedList = view.findViewById(R.id.flaggedList);
        loadFlaggedUsers();
        view.findViewById(R.id.adminFlaggedBack).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void loadFlaggedUsers(){
        flaggedList.removeAllViews();
        Set<String> flagged = getFlaggedUser();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        if(flagged.isEmpty()){
            TextView e = new TextView(getContext());
            e.setText(R.string.no_flagged_users);
            flaggedList.addView(e);
            return;
        }
        for(String username : flagged){
            View v = inflater.inflate(R.layout.byte_recycler_item, flaggedList, false);
            TextView tv = v.findViewById(R.id.recyclerItemTextview);
            tv.setText(username + getString(R.string.is_now_flagged));
            flaggedList.addView(v);
        }
    }

    private Set<String> getFlaggedUser(){
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAMES, Context.MODE_PRIVATE);
        Set<String> stored = prefs.getStringSet(FLAGGED_USERS_KEY, null);
        if(stored == null){
            return new HashSet<String>();
        }
        return new HashSet<String>(stored);
    }
}
