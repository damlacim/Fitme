package com.damlacim.fitme.feature.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.damlacim.fitme.data.model.Meal;
import com.damlacim.fitme.data.model.MealItem;
import com.damlacim.fitme.util.SingleLiveEvent;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public final class DashboardViewModel extends ViewModel {
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFireStore;

    private FirebaseApp mApp;

    @Inject
    DashboardViewModel(FirebaseAuth auth, FirebaseFirestore fireStore) {
        mAuth = auth;
        mFireStore = fireStore;

    }

    private final MutableLiveData<List<Meal>> mealsLiveData = new MutableLiveData<>();

    public LiveData<List<Meal>> getMeals() {
        return mealsLiveData;
    }

    private final SingleLiveEvent<Boolean> isLoading = new SingleLiveEvent<>();

    public SingleLiveEvent<Boolean> getIsLoading() {
        return isLoading;
    }

    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }


    public void getMealList() {
        isLoading.setValue(true);
        CollectionReference mealsCollection = mFireStore.collection("meals").document(mAuth.getUid()).collection("mealsItems");

        mealsCollection.get().addOnSuccessListener(querySnapshot -> {
            List<Meal> meals = new ArrayList<>();
            for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                Map<String, Object> mealData = documentSnapshot.getData();
                String mealName = mealData.get("mealName").toString();
                Date mealDate = new Date((long) mealData.get("mealDate"));
                List<Map<String, Object>> mealsList = (List<Map<String, Object>>) mealData.get("meals");
                List<MealItem> mealItems = new ArrayList<>();
                for (Map<String, Object> mealItemData : mealsList) {
                    String name = mealItemData.get("name").toString();
                    String calories = mealItemData.get("calories").toString();
                    mealItems.add(new MealItem(name, calories));
                }
                meals.add(new Meal(mealName, mealDate, mealItems));
            }
            mealsLiveData.setValue(meals);
            isLoading.setValue(false);
        }).addOnFailureListener(e -> {
            isLoading.setValue(false);
            errorMessage.setValue(e.getMessage());
        });
    }
}