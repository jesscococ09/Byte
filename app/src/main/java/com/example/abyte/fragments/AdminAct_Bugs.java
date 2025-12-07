package com.example.abyte.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.abyte.R;
import com.example.abyte.adapter.BugsAdapter;
import com.example.abyte.model.Bugs;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdminAct_Bugs extends Fragment{
    private static final String PREFS_NAME = "admin_prefs";
    private static final String KEY_BUGS_JSON = "bugs_json";
    private RecyclerView bugsRecyclerView;
    private BugsAdapter bugAdapter;
    private List<Bugs> bugList = new ArrayList<Bugs>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.admin_bugs, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        bugsRecyclerView = (RecyclerView) view.findViewById(R.id.bugsRecyclerView);
        bugsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        bugsRecyclerView.setVisibility(View.GONE);
        bugAdapter = new BugsAdapter(bugList, new BugsAdapter.BugListener(){
            @Override
            public void onBugClicked(Bugs bug){
                showBugDetails(bug);
            }
        });
        bugsRecyclerView.setAdapter(bugAdapter);
        loadBugs();
        view.findViewById(R.id.viewBugsButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                bugsRecyclerView.setVisibility(View.VISIBLE);
                loadBugs();
                Toast.makeText(getContext(), "Showing Bugs", Toast.LENGTH_SHORT).show();
            }
        });
        view.findViewById(R.id.addBugButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showAddBugDialog();
            }
        });
        view.findViewById(R.id.deleteBugButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showDeleteBugsDialog();
            }
        });
        view.findViewById(R.id.adminBackButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
    private void showAddBugDialog(){
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.admin_add_bug,
                null, false);
        final EditText titleInput = (EditText)dialogView.findViewById(R.id.bugTitleInput);
        final EditText descriptionInput = (EditText)dialogView.findViewById(R.id.bugDescriptionInput);

        new AlertDialog.Builder(requireContext()).setTitle("Add Bug").setView(dialogView).
                setPositiveButton("Add", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        String title = titleInput.getText().toString().trim();
                        String description = descriptionInput.getText().toString().trim();

                        if(TextUtils.isEmpty(title))
                        {Toast.makeText(getContext(), "Title Cannot Be Empty",
                                Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Bugs bug = new Bugs(UUID.randomUUID().toString(), title, description);
                        bugList.add(bug);
                        saveBugs();
                        bugAdapter.updateList(bugList);
                        Toast.makeText(getContext(), "Bug Added", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("Cancel", null).show();
    }
    private void showBugDetails(Bugs bug){
        String msg = "Title: " + bug.getTitle() + "\n\nDescription:\n" + bug.getDescription();
        new AlertDialog.Builder(requireContext()).setTitle("Bug Details").setMessage(msg).
                setPositiveButton("OK", null).show();
    }
    private void showDeleteBugsDialog(){
        if (bugList.isEmpty()){
            Toast.makeText(getContext(), "No Bugs To Delete", Toast.LENGTH_SHORT).show();
            return;
        }
        final String [] titles = new String[bugList.size()];
        final boolean [] checked = new boolean[bugList.size()];
        for(int i = 0; i < bugList.size(); i++){
            titles[i] = bugList.get(i).getTitle();
            checked[i] = false;
        }
        new AlertDialog.Builder(requireContext()).setTitle("Select Bugs To Delete").
                setMultiChoiceItems(titles, checked, new DialogInterface.
                        OnMultiChoiceClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked){
                        checked[which] = isChecked;
                    }
                }).setPositiveButton("Delete", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        for(int i = checked.length - 1; i >= 0; i--){
                            if (checked[i]){
                                bugList.remove(i);
                            }
                        }
                        saveBugs();
                        bugAdapter.updateList(bugList);
                        Toast.makeText(getContext(), "Selected Bugs Deleted",
                                Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("Cancel", null).show();
    }
    private void loadBugs(){
        SharedPreferences prefs = requireContext().getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_BUGS_JSON, "[]");
        bugList = new ArrayList<Bugs>();

        try{
            JSONArray array = new JSONArray(json);
            for(int i = 0; i < array.length(); i++){
                JSONObject obj = array.getJSONObject(i);
                String id = obj.optString("id");
                String title = obj.optString("title");
                String description = obj.optString("description");
                bugList.add(new Bugs(id, title, description));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }

        if(bugAdapter != null){
            bugAdapter.updateList(bugList);
        }
    }
    private void saveBugs(){
        JSONArray array = new JSONArray();
        for(Bugs bug : bugList){
            JSONObject obj = new JSONObject();
            try{
                obj.put("id", bug.getId());
                obj.put("title", bug.getTitle());
                obj.put("description", bug.getDescription());
                array.put(obj);
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
        SharedPreferences prefs = requireContext().getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_BUGS_JSON, array.toString()).apply();
    }
}