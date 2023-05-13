package com.damlacim.fitme.data.model;

import java.util.Date;
import java.util.List;

public class Meal {
    private String mealName;
    private Date mealDate;
    private List<MealItem> mealItems;

    public Meal(String mealName, Date mealDate, List<MealItem> mealItems) {
        this.mealName = mealName;
        this.mealDate = mealDate;
        this.mealItems = mealItems;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public Date getMealDate() {
        return mealDate;
    }

    public void setMealDate(Date mealDate) {
        this.mealDate = mealDate;
    }

    public List<MealItem> getMealItems() {
        return mealItems;
    }

    public void setMealItems(List<MealItem> mealItems) {
        this.mealItems = mealItems;
    }
}