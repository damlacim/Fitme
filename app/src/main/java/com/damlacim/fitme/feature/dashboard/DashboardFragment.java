package com.damlacim.fitme.feature.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.damlacim.fitme.R;
import com.damlacim.fitme.databinding.FragmentDashboardBinding;
import com.damlacim.fitme.feature.base.BaseFragment;
import com.damlacim.fitme.feature.dashboard.adapter.MealAdapter;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DashboardFragment extends BaseFragment {

    private FragmentDashboardBinding binding;
    DashboardViewModel viewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_dashboard;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void initViews() {
        viewModel.getMealList();

        binding.fabAddActivity.setOnClickListener(v -> {
            getNavController().navigate(R.id.action_dashboardFragment_to_addMealFragment);
        });

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                showLoading();
            } else {
                hideLoading();
            }
        });

        viewModel.getMeals().observe(getViewLifecycleOwner(), meals -> {
            binding.rvMeal.setAdapter(new MealAdapter(meals));
        });

    }
}