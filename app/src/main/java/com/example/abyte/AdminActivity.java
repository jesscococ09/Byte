package com.example.abyte;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class AdminActivity extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.admin_home, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        View usersBtn = view.findViewById(R.id.viewAdminUsers);
        if (usersBtn != null) {
            usersBtn.setVisibility(View.VISIBLE);
            usersBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openFragment(new AdminUserControlActivity());
                }
            });
        }

        View flaggedBtn = view.findViewById(R.id.viewAdminFlagged);
        if (flaggedBtn != null) {
            flaggedBtn.setVisibility(View.GONE);
        }

        View analyticsBtn = view.findViewById(R.id.viewAdminAnalytics);
        if (analyticsBtn != null) {
            analyticsBtn.setVisibility(View.GONE);
        }

        View settingsBtn = view.findViewById(R.id.viewAdminSettingsButton);
        if (settingsBtn != null) {
            settingsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openFragment(new com.example.abyte.fragments.AdminAct_Settings());
                }
            });
        }


        View backBtn = view.findViewById(R.id.adminBackButton);
        if (backBtn != null){
            backBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    requireActivity().getSupportFragmentManager().popBackStack();
                }
            });
        }
    }
    private void openFragment(Fragment frag){
        requireActivity().getSupportFragmentManager().beginTransaction().
                replace(R.id.fragmentContainer, frag).addToBackStack(null).commit();
    }
}