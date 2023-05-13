package com.damlacim.fitme.feature.splash;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public final class SplashViewModel extends ViewModel {

    private FirebaseAuth mAuth;

    @Inject
    SplashViewModel(FirebaseAuth auth) {
        mAuth = auth;
    }


    public boolean isUserLoggedIn() {
        return mAuth.getCurrentUser() != null;
    }

}