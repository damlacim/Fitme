package com.damlacim.fitme.feature.splash;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.damlacim.fitme.R;
import com.damlacim.fitme.databinding.FragmentRegisterBinding;
import com.damlacim.fitme.databinding.FragmentSplashBinding;
import com.damlacim.fitme.feature.base.BaseFragment;
import com.damlacim.fitme.feature.register.RegisterViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SplashFragment extends BaseFragment {

    SplashViewModel viewModel;
    private FragmentSplashBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSplashBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_splash;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SplashViewModel.class);

    }

    private void initViews() {
        viewModel.isUserLoggedIn();
        navigate();
    }

    private void checkUserLoggedIn() {
        if (viewModel.isUserLoggedIn()) {
            getNavController().navigate(R.id.action_Splash_to_main_nav_graph);
        } else {
            getNavController().navigate(R.id.action_SplashFragment_to_LoginFragment);
        }
    }

    private void navigate() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkUserLoggedIn();
            }
        }, 2000);
    }
}