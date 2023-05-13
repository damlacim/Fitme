package com.damlacim.fitme.feature.register;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.damlacim.fitme.util.SingleLiveEvent;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public final class RegisterViewModel extends ViewModel {

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFireStore;

    private FirebaseApp mApp;

    @Inject
    RegisterViewModel(FirebaseAuth auth, FirebaseFirestore fireStore) {
        mAuth = auth;
        mFireStore = fireStore;

    }

    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    private final SingleLiveEvent<Boolean> successEvent = new SingleLiveEvent<>();
    private final SingleLiveEvent<Boolean> isLoading = new SingleLiveEvent<>();

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public SingleLiveEvent<Boolean> getSuccessEvent() {
        return successEvent;
    }

    public void register(String email, String password) {
        isLoading.setValue(true);
        if (validate(email, password)) {
            createUser(email, password);
        } else {
            successEvent.setValue(false);
            isLoading.setValue(false);
        }
    }

    private void createUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        String uid = mAuth.getCurrentUser().getUid();
                        Map<String, Object> user = new HashMap<>();
                        user.put("email", email);
                        user.put("name", "");
                        mFireStore.collection("users").document(uid)
                                .set(user)
                                .addOnSuccessListener(aVoid -> {
                                })
                                .addOnFailureListener(e -> {
                                    errorMessage.setValue(task.getException().getMessage());
                                });
                        successEvent.setValue(true);
                        isLoading.setValue(false);
                    } else {
                        errorMessage.setValue(task.getException().getMessage());
                        isLoading.setValue(false);
                    }
                });
    }


    public boolean validate(String email, String password) {
        if (email.isEmpty()) {
            errorMessage.setValue("Email cannot be empty");
            return false;
        }
        if (password.isEmpty()) {
            errorMessage.setValue("Password cannot be empty");
            return false;
        }
        if (password.length() < 6) {
            errorMessage.setValue("Password must be at least 6 characters");
            return false;
        }
        return true;
    }


}