package com.damlacim.fitme.feature.add;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.damlacim.fitme.util.SingleLiveEvent;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public final class AddMealViewModel extends ViewModel {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    private final SingleLiveEvent<Boolean> successEvent = new SingleLiveEvent<>();
    private final SingleLiveEvent<Boolean> isLoading = new SingleLiveEvent<>();

    public SingleLiveEvent<Boolean> getIsLoading() {
        return isLoading;
    }

    public SingleLiveEvent<Boolean> getSuccessEvent() {
        return successEvent;
    }


    @Inject
    AddMealViewModel(FirebaseAuth auth, FirebaseFirestore firestore) {
        mAuth = auth;
        db = firestore;
    }


    public void saveMeal(String firstFood, String secondFood,
                         String thirdFood, String firstCalorie,
                         String secondCalorie, String thirdCalorie,
                         String mealOfDay) {
        isLoading.setValue(true);
        String uid = mAuth.getCurrentUser().getUid();

        Map<String, Object> mealData = new HashMap<>();
        mealData.put("mealName", mealOfDay);
        mealData.put("mealDate", System.currentTimeMillis());
        mealData.put("meals", Arrays.asList(
                new HashMap<String, Object>() {{
                    put("name", firstFood);
                    put("calories", firstCalorie);
                }},
                new HashMap<String, Object>() {{
                    put("name", secondFood);
                    put("calories", secondCalorie);
                }},
                new HashMap<String, Object>() {{
                    put("name", thirdFood);
                    put("calories", thirdCalorie);
                }}
        ));
        addMeal(uid, mealData);

    }

    public void addMeal(String uid, Map<String, Object> mealData) {
        DocumentReference mealDoc = db.collection("meals").document(uid);
        mealDoc.collection("mealsItems").add(mealData)
                .addOnSuccessListener(aVoid -> {
                    isLoading.setValue(false);
                    errorMessage.setValue("Meal record successfully added.");
                    successEvent.setValue(true);
                })
                .addOnFailureListener(e -> {
                    isLoading.setValue(false);
                    errorMessage.setValue(e.getMessage());
                });
    }
}
