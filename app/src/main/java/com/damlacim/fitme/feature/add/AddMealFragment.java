package com.damlacim.fitme.feature.add;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.damlacim.fitme.R;
import com.damlacim.fitme.databinding.FragmentAddMealBinding;
import com.damlacim.fitme.feature.base.BaseFragment;
import com.google.android.material.snackbar.Snackbar;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddMealFragment extends BaseFragment {

    AddMealViewModel viewModel;

    private FragmentAddMealBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAddMealBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_add_meal;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AddMealViewModel.class);
    }

    private void initViews() {
        binding.ivLeftArrow.setOnClickListener(v -> {
            getNavController().navigateUp();
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), s -> {
            Snackbar.make(binding.getRoot(), s, Snackbar.LENGTH_SHORT).show();

        });

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                showLoading();
            } else {
                hideLoading();
            }
        });

        viewModel.getSuccessEvent().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                getNavController().navigateUp();
            }
        });

        binding.btnSave.setOnClickListener(v -> {
            viewModel.saveMeal(binding.firstEtFood.getText().toString(),
                    binding.secondFood.getText().toString(),
                    binding.thirdFood.getText().toString(),
                    binding.etFirstCalories.getText().toString(),
                    binding.secondEtCalories.getText().toString(),
                    binding.thirdEtCalories.getText().toString(),
                    binding.etMealOfDay.getText().toString());

        });
    }
}