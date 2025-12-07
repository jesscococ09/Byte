package com.example.abyte.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.abyte.R;
import com.example.abyte.model.Bugs;
import java.util.List;

public class BugsAdapter extends RecyclerView.Adapter<BugsAdapter.BugViewHolder>{
    public interface BugListener{
        void onBugClicked(Bugs bug);
    }
    private List<Bugs> bugList;
    private BugListener listener;

    public BugsAdapter(List<Bugs> bugList, BugListener listener){
        this.bugList = bugList;
        this.listener = listener;
    }
    @NonNull
    @Override
    public BugViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_bug_item, parent,
                false);
        return new BugViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull BugViewHolder holder, int position){
        Bugs bug = bugList.get(position);
        holder.titleTextView.setText(bug.getTitle());
        holder.descriptionTextView.setText(bug.getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                listener.onBugClicked(bug);
            }
        });
    }
    @Override
    public int getItemCount(){
        return bugList.size();
    }
    public void updateList(List<Bugs> newList){
        this.bugList = newList;
        notifyDataSetChanged();
    }
    static class BugViewHolder extends RecyclerView.ViewHolder{
        TextView titleTextView;
        TextView descriptionTextView;
        public BugViewHolder(@NonNull View itemView){
            super(itemView);
            titleTextView = itemView.findViewById(R.id.bugTitleTextView);
            descriptionTextView = itemView.findViewById(R.id.bugDescriptionTextView);
        }
    }
}