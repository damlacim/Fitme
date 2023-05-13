package com.damlacim.fitme.feature.dashboard.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.damlacim.fitme.R;
import com.damlacim.fitme.data.model.MealItem;

import java.util.List;


public class MealFoodAdapter extends RecyclerView.Adapter<MealFoodAdapter.ViewHolder> {
    private final List<MealItem> mealList;

    public MealFoodAdapter(List<MealItem> mealList) {
        this.mealList = mealList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_meal_food_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MealItem mealItem = mealList.get(position);
        holder.mealTitle.setText("Name:" + mealItem.getName());
        holder.mealCalories.setText("Calories" + mealItem.getCalories());
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mealTitle;
        public TextView mealCalories;

        public ViewHolder(View view) {
            super(view);
            mealTitle = view.findViewById(R.id.tvMealName);
            mealCalories = view.findViewById(R.id.tvCalories);
        }
    }
}

