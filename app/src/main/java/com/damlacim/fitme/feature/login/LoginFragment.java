package com.damlacim.fitme.feature.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.damlacim.fitme.R;
import com.damlacim.fitme.databinding.FragmentLoginBinding;
import com.damlacim.fitme.feature.base.BaseFragment;
import com.google.android.material.snackbar.Snackbar;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends BaseFragment {

    private FragmentLoginBinding binding;
    private NavController navController;
    LoginViewModel viewModel;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_login;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        initViews();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    private void initViews() {
        binding.tvSignUp.setOnClickListener(v -> {
            navController.navigate(R.id.action_loginFragment_to_registerFragment);
        });

        binding.btnSignUp.setOnClickListener(v -> {
            viewModel.login(binding.etEmail.getText().toString(), binding.etPassword.getText().toString());
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), s -> {
            Snackbar.make(binding.getRoot(), s, Snackbar.LENGTH_SHORT).show();
        });


        viewModel.getSuccessEvent().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                navController.navigate(R.id.action_LoginFragment_to_main_nav_graph);
            }
        });

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                showLoading();
            } else {
                hideLoading();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}