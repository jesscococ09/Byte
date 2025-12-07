package com.example.abyte;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abyte.database.entities.Meal;

import java.util.ArrayList;

public class MEAL_RecyclerViewAdapter extends RecyclerView.Adapter<MEAL_RecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<Meal> mealList;

    public MEAL_RecyclerViewAdapter(Context context, ArrayList<Meal> mealList) {
        this.context = context;
        this.mealList = mealList;
    }

    @NonNull
    @Override
    //Inflates the Layout making rows visual
    public MEAL_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.meal_recycler_row, parent, false);
        return new MEAL_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    //Assigning values to the views created using meal_recycler_row.xml layout file
    public void onBindViewHolder(@NonNull MEAL_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.mealName.setText(mealList.get(position).getMealName());
        holder.mealStatus.setText(mealList.get(position).getStatus()); //TODO: Create way of storing status
        holder.mealImage.setImageResource(mealList.get(position).getMealImage());
    }

    @Override
    //Number of rows
    public int getItemCount(){
        return mealList.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder {

        ImageView mealImage;
        TextView mealName, mealStatus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.byteMealRecyclerPhoto);
            mealName = itemView.findViewById(R.id.byteMealRecyclerRowName);
            mealStatus = itemView.findViewById(R.id.byteMealRecyclerRowStatus);
        }
    }
}
