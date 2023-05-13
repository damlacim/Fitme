package com.damlacim.fitme.feature.profile;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.damlacim.fitme.R;
import com.damlacim.fitme.databinding.FragmentProfileBinding;
import com.damlacim.fitme.databinding.FragmentRegisterBinding;
import com.damlacim.fitme.feature.base.BaseFragment;
import com.damlacim.fitme.feature.bottomsheet.LogoutBottomSheetDialog;
import com.damlacim.fitme.feature.register.RegisterViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint

public class ProfileFragment extends BaseFragment implements LogoutBottomSheetDialog.LogoutListener {
    private FragmentProfileBinding binding;

    ProfileViewModel viewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_profile;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

    }

    @SuppressLint("SetTextI18n")
    private void initViews() {
        viewModel.getTotalMealCount();
        binding.tvEmail.setText(viewModel.getUserEmail());
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                showLoading();
            } else {
                hideLoading();
            }
        });

        viewModel.getSuccessEvent().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
               getNavController().navigate(R.id.action_profileFragment_to_loginFragment);
            }
        });

        viewModel.getTotalMealCountLiveData().observe(getViewLifecycleOwner(), integer -> {
            binding.tvTotalMeal.setText("Total Meal:" + integer);
        });
        binding.tvLogout.setOnClickListener(v -> showLogoutBottomSheet());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showLogoutBottomSheet() {
        LogoutBottomSheetDialog bottomSheet = new LogoutBottomSheetDialog(this);
        bottomSheet.show(getFragmentManager(), "logoutBottomSheet");
    }

    @Override
    public void onLogout() {
        viewModel.logout();
    }
}