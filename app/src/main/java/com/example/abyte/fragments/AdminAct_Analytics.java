package com.example.abyte.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import com.example.abyte.R;
import com.example.abyte.database.entities.User;
import com.example.abyte.database.repositories.UserRepository;

import java.util.List;

public class AdminAct_Analytics extends Fragment{
    private UserRepository repository;
    private LinearLayout listContainer_User;
    private TextView totalUsers;
    private TextView adminUsers;
    private TextView regularUsers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstance){
        return inflater.inflate(R.layout.admin_analytics, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        repository = UserRepository.getRepository(requireActivity().getApplication());
        listContainer_User = view.findViewById(R.id.ListContainer);
        totalUsers = view.findViewById(R.id.totalUsersView);
        adminUsers = view.findViewById(R.id.adminUsersValueView);
        regularUsers = view.findViewById(R.id.regularUsersValueView);

        Button back = view.findViewById(R.id.adminAnalyticsBackButton);

        LiveData<List<User>> allUsers = repository.getAllUsers();

        allUsers.observe(getViewLifecycleOwner(), new Observer<List<User>>(){
            @Override
            public void onChanged(List<User> users) {
                countUpdate(users);
                usersDisplay(users);
            }
        });

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
    private void countUpdate(List<User> users){
        int total = 0;
        int admins = 0;

        if(users != null){
            total = users.size();
            for(int i = 0; i < users.size(); i++){
                if(users.get(i).isAdmin()){
                    admins++;
                }
            }
        }

        int regular = total - admins;

        totalUsers.setText(String.valueOf(total));
        adminUsers.setText(String.valueOf(admins));
        regularUsers.setText(String.valueOf(regular));
    }
    @SuppressLint("SetTextI18n")
    private void usersDisplay(List<User> users){
        listContainer_User.removeAllViews();

        LayoutInflater inflater = LayoutInflater.from(getContext());

        if(users == null || users.isEmpty()){
            TextView e = new TextView(getContext());
            e.setText(R.string.no_users_found);
            listContainer_User.addView(e);
            return;
        }
        for(int i = 0; i < users.size(); i++){
            User u = users.get(i);
            View v = inflater.inflate(R.layout.byte_recycler_item, listContainer_User, false);
            TextView tv = v.findViewById(R.id.recyclerItemTextview);

            String userRole;

            if(u.isAdmin()){
                userRole = "Admin";
            }else{
                userRole = "Regular";
            }

            tv.setText(u.getUsername() + " - " + userRole);

            listContainer_User.addView(v);
        }
    }
}