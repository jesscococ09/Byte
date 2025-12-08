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
        holder.ingredient1.setText(mealList.get(position).getIngredient1());
        holder.ingredient2.setText(mealList.get(position).getIngredient2());
        holder.ingredient3.setText(mealList.get(position).getIngredient3());
        holder.ingredient4.setText(mealList.get(position).getIngredient4());
        holder.ingredient5.setText(mealList.get(position).getIngredient5());
        holder.ingredient6.setText(mealList.get(position).getIngredient6());
        holder.ingredient7.setText(mealList.get(position).getIngredient7());
        holder.ingredient8.setText(mealList.get(position).getIngredient8());
        holder.ingredient9.setText(mealList.get(position).getIngredient9());
        holder.ingredient10.setText(mealList.get(position).getIngredient10());
//        holder.mealStatus.setText(mealList.get(position).getStatus()); //TODO: Create way of storing status
//        holder.mealImage.setImageResource(mealList.get(position).getImageFile());
    }

    @Override
    //Number of rows
    public int getItemCount(){
        return mealList.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder {

        TextView mealName;
        TextView ingredient1;
        TextView ingredient2;
        TextView ingredient3;
        TextView ingredient4;
        TextView ingredient5;
        TextView ingredient6;
        TextView ingredient7;
        TextView ingredient8;
        TextView ingredient9;
        TextView ingredient10;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.byteMealRecyclerRowName);
        }
    }
}
