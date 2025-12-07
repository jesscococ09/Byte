package com.example.abyte.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.abyte.R;

public class AdminAct_Settings extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.admin_settings, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.settingsSaveButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
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
}