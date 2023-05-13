package com.damlacim.fitme.feature.dashboard.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.damlacim.fitme.R;
import com.damlacim.fitme.data.model.Meal;
import com.damlacim.fitme.feature.dashboard.adapter.decoration.HorizontalSpaceItemDecoration;
import com.damlacim.fitme.util.DateUtils;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder> {
    private final List<Meal> mealList;

    public MealAdapter(List<Meal> mealList) {
        this.mealList = mealList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_meal_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meal mealItem = mealList.get(position);
        holder.mealTitle.setText("Meal of Day: " + mealItem.getMealName());
        holder.tvDate.setText(String.valueOf(DateUtils.formatDate(mealItem.getMealDate())));
        holder.tvMealCount.setText("Meal Count: " + mealItem.getMealItems().size());
        holder.rvMealFood.setAdapter(new MealFoodAdapter(mealItem.getMealItems()));
        holder.rvMealFood.setHasFixedSize(true);
        holder.rvMealFood.addItemDecoration(new HorizontalSpaceItemDecoration(8, holder.rvMealFood.getContext()));
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mealTitle;
        public TextView tvDate;
        public TextView tvMealCount;
        public RecyclerView rvMealFood;

        public ViewHolder(View view) {
            super(view);
            mealTitle = view.findViewById(R.id.tvMealTitle);
            tvDate = view.findViewById(R.id.tvDate);
            tvMealCount = view.findViewById(R.id.tvMealCount);
            rvMealFood = view.findViewById(R.id.rvMealFoodList);
        }
    }
}

