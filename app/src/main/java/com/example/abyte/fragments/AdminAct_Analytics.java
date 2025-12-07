package com.example.abyte.fragments;

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

public class AdminAct_Analytics extends Fragment {

    private UserRepository repository;

    // user summary
    private TextView totalUsersView;
    private TextView adminUsersView;
    private TextView regularUsersView;

    // user list container
    private LinearLayout userListContainer;

    // stats section
    private TextView totalMealDbMealsView;
    private TextView totalSupabaseMealsView;
    private TextView totalMealsView;
    private TextView totalBugsView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.admin_analytics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // repo
        repository = UserRepository.getRepository(requireActivity().getApplication());

        // bind views for user summary
        totalUsersView = (TextView) view.findViewById(R.id.totalUsersView);
        adminUsersView = (TextView) view.findViewById(R.id.adminUsersValueView);
        regularUsersView = (TextView) view.findViewById(R.id.regularUsersValueView);

        // user list
        userListContainer = (LinearLayout) view.findViewById(R.id.ListContainer);

        // stats views (from included admin_stats.xml)
        totalMealDbMealsView = (TextView) view.findViewById(R.id.adminStatsTotalMealDBMeals);
        totalSupabaseMealsView = (TextView) view.findViewById(R.id.adminStatsTotalSupabaseMeals);
        totalMealsView = (TextView) view.findViewById(R.id.adminStatsTotalMeals);
        totalBugsView = (TextView) view.findViewById(R.id.adminStatsTotalBugs);

        // back button
        Button backButton = (Button) view.findViewById(R.id.adminAnalyticsBackButton);
        if (backButton != null) {
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requireActivity().getSupportFragmentManager().popBackStack();
                }
            });
        }

        // observe users for summary + list
        LiveData<List<User>> allUsersLiveData = repository.getAllUsers();
        allUsersLiveData.observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                displayUserCounts(users);
                displayUsernames(users);
            }
        });

        // load stats (replace placeholders with real values when ready)
        loadStats();
    }

    private void displayUserCounts(List<User> users) {
        int total = 0;
        int admins = 0;

        if (users != null) {
            total = users.size();
            int i;
            for (i = 0; i < users.size(); i++) {
                User u = users.get(i);
                if (u.isAdmin()) {
                    admins++;
                }
            }
        }

        int regular = total - admins;

        totalUsersView.setText(String.valueOf(total));
        adminUsersView.setText(String.valueOf(admins));
        regularUsersView.setText(String.valueOf(regular));
    }

    private void displayUsernames(List<User> users) {
        userListContainer.removeAllViews();

        LayoutInflater inflater = LayoutInflater.from(getContext());

        if (users == null || users.isEmpty()) {
            TextView empty = new TextView(getContext());
            empty.setText("No users found.");
            userListContainer.addView(empty);
            return;
        }

        int i;
        for (i = 0; i < users.size(); i++) {
            User u = users.get(i);

            View row = inflater.inflate(R.layout.byte_recycler_item, userListContainer, false);
            TextView tv = (TextView) row.findViewById(R.id.recyclerItemTextview);

            String roleLabel;
            if (u.isAdmin()) {
                roleLabel = "Admin";
            } else {
                roleLabel = "Regular";
            }

            tv.setText(u.getUsername() + " - " + roleLabel);
            userListContainer.addView(row);
        }
    }

    // TODO: hook these up to your actual data sources
    private void loadStats() {
        int totalMealDbMeals = 0;
        int totalSupabaseMeals = 0;
        int totalMeals = 0;
        int totalBugs = 0;

        totalMealDbMealsView.setText("Total Meals (MealDB): " + totalMealDbMeals);
        totalSupabaseMealsView.setText("Total Supabase Meals: " + totalSupabaseMeals);
        totalMealsView.setText("Total Meals: " + totalMeals);
        totalBugsView.setText("Total Bugs: " + totalBugs);
    }
}