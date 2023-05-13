package com.damlacim.fitme.feature.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.damlacim.fitme.util.SingleLiveEvent;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public final class ProfileViewModel extends ViewModel {
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFireStore;

    private FirebaseApp mApp;

    @Inject
    ProfileViewModel(FirebaseAuth auth, FirebaseFirestore fireStore) {
        mAuth = auth;
        mFireStore = fireStore;

    }

    private final SingleLiveEvent<Boolean> isLoading = new SingleLiveEvent<>();

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    private final SingleLiveEvent<Boolean> successEvent = new SingleLiveEvent<>();

    public SingleLiveEvent<Boolean> getSuccessEvent() {
        return successEvent;
    }

    private final MutableLiveData<String> totalMealCount = new MutableLiveData<>();

    public LiveData<String> getTotalMealCountLiveData() {
        return totalMealCount;
    }

    public String getUserEmail() {
        return Objects.requireNonNull(mAuth.getCurrentUser()).getEmail();
    }

    public void logout() {
        isLoading.setValue(true);
        mAuth.signOut();
        successEvent.setValue(true);
        isLoading.setValue(false);
    }

    public void getTotalMealCount() {
        isLoading.setValue(true);
        mFireStore.collection("meals")
                .document(mAuth.getUid()).collection("mealsItems")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        isLoading.setValue(false);
                        totalMealCount.setValue(String.valueOf(Objects.requireNonNull(task.getResult()).size()));
                    } else {
                        isLoading.setValue(false);
                    }
                });
    }
}