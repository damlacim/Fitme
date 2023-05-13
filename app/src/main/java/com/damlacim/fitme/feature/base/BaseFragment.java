package com.damlacim.fitme.feature.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.damlacim.fitme.feature.dialog.LottieProgressDialog;

public abstract class BaseFragment extends Fragment {

    private LottieProgressDialog progressDialog;
    NavController navController;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResourceId(), container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);


    }

    protected abstract int getLayoutResourceId();

    protected void showLoading() {
        if (progressDialog == null) {
            progressDialog = new LottieProgressDialog(requireContext());
        }
        progressDialog.show();
    }

    protected void hideLoading() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    protected NavController getNavController() {
        return navController;
    }
}