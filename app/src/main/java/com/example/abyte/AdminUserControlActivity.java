package com.example.abyte;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.abyte.fragments.AdminAct_Analytics;
import com.example.abyte.fragments.AdminAct_Flagged;
import com.example.abyte.fragments.AdminAct_Users;

public class AdminUserControlActivity extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.admin_home, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.viewAdminUsers).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openFragment(new AdminAct_Users());
            }
        });
        view.findViewById(R.id.viewAdminAnalytics).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openFragment(new AdminAct_Analytics());
            }
        });
        view.findViewById(R.id.viewAdminFlagged).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openFragment(new AdminAct_Flagged());
            }
        });
    }
    private void openFragment(Fragment frag){
        requireActivity().getSupportFragmentManager().beginTransaction().
                replace(R.id.fragmentContainer, frag).addToBackStack(null).commit();
    }
}