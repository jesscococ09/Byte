package com.example.abyte.fragments;

import android.os.Bundle;
import android.util.Log;
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

import com.example.abyte.APIs.MealsApi;
import com.example.abyte.APIs.SupabaseApi;
import com.example.abyte.APIs.apiClient;
import com.example.abyte.APIs.models.CountResult;
import com.example.abyte.APIs.models.MealResponse;
import com.example.abyte.APIs.models.Recipe;
import com.example.abyte.R;
import com.example.abyte.database.entities.User;
import com.example.abyte.database.repositories.UserRepository;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    //API
    private MealsApi mealsApi;
    private SupabaseApi supabaseApi;

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

        //init API
        mealsApi= apiClient.getExternalClient().create(MealsApi.class);
        supabaseApi=apiClient.getSupabaseClient().create(SupabaseApi.class);

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
        // 1) Call MealDB API
        mealsApi.searchMeals("").enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(@NonNull Call<MealResponse> call,
                                   @NonNull Response<MealResponse> response) {
                int totalMealDbMeals = response.body() != null && response.body().getMeals() != null
                        ? response.body().getMeals().size()
                        : 0;
                totalMealDbMealsView.setText("Total Meals (MealDB): " + totalMealDbMeals);

                // 2) Call Supabase API for count
                supabaseApi.countRecipes().enqueue(new Callback<List<CountResult>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<CountResult>> call,
                                           @NonNull Response<List<CountResult>> response) {
                        int totalSupabaseMeals = 0;
                        if (response.body() != null && !response.body().isEmpty()) {
                            totalSupabaseMeals = response.body().get(0).count;
                        }
                        totalSupabaseMealsView.setText("Total Supabase Meals: " + totalSupabaseMeals);
                        int totalMeals = totalMealDbMeals + totalSupabaseMeals;
                        totalMealsView.setText("Total Meals: " + totalMeals);
                        int totalBugs = 0;
                        totalBugsView.setText("Total Bugs: " + totalBugs);
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<CountResult>> call, @NonNull Throwable t) {
                        Log.e("AdminAct_Analytics", "Supabase call failed", t);
                        totalSupabaseMealsView.setText("Total Supabase Meals: error");
                    }
                });
            }

            @Override
            public void onFailure(@NonNull Call<MealResponse> call, @NonNull Throwable t) {
                totalMealDbMealsView.setText("Total Meals (MealDB): error");
                Log.e("AdminAct_Analytics", "MealDB API call failed", t);
            }
        });
    }
}