package com.damlacim.fitme.feature.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.damlacim.fitme.util.SingleLiveEvent;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public final class LoginViewModel extends ViewModel {

    private FirebaseAuth mAuth;

    @Inject
    LoginViewModel(FirebaseAuth auth) {
        mAuth = auth;
    }

    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    private SingleLiveEvent<Boolean> successEvent = new SingleLiveEvent<>();

    public SingleLiveEvent<Boolean> getSuccessEvent() {
        return successEvent;
    }


    private final SingleLiveEvent<Boolean> isLoading = new SingleLiveEvent<>();

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }


    public void login(String email, String password) {
        isLoading.setValue(true);
        if (validate(email, password)) {
            onLogin(email, password);
        } else {
            successEvent.setValue(false);
            isLoading.setValue(true);
        }
    }

    private void onLogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
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