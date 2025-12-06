package com.example.abyte.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.abyte.R;

public class Admin extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.admin_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.viewAdminBugsButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openFragment(new AdminAct_Bugs());
            }
        });
        view.findViewById(R.id.viewAdminSettingsButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openFragment(new AdminAct_Settings());
            }
        });
        view.findViewById(R.id.viewAdminThemesButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openFragment(new AdminAct_Themes());
            }
        });
    }

    private void openFragment(Fragment frag){
        requireActivity().getSupportFragmentManager().beginTransaction().
                replace(R.id.fragmentContainer, frag).addToBackStack(null).commit();
    }
}