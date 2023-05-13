package com.damlacim.fitme.feature.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.damlacim.fitme.R;
import com.damlacim.fitme.databinding.FragmentRegisterBinding;
import com.damlacim.fitme.feature.base.BaseFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import dagger.hilt.android.scopes.FragmentScoped;

@AndroidEntryPoint
public class RegisterFragment extends BaseFragment {
    private FragmentRegisterBinding binding;


    RegisterViewModel viewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_register;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

    }

    private void initViews() {
        binding.ivLeftArrow.setOnClickListener(v -> {
            getNavController().navigateUp();
        });
        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), s -> {
            Snackbar.make(binding.getRoot(), s, Snackbar.LENGTH_SHORT).show();
        });

        binding.btnSignUp.setOnClickListener(v -> {
            viewModel.register(Objects.requireNonNull(binding.etEmail.getText()).toString(), binding.etPassword.getText().toString());
        });

        viewModel.getSuccessEvent().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                getNavController().navigate(R.id.action_RegisterFragment_to_main_nav_graph);
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
