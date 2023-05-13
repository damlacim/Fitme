package com.damlacim.fitme;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.damlacim.fitme.databinding.ActivityMainBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // navhost
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);


        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            setVisibleBottomNavigation(destination.getId() == R.id.dashboardFragment || R.id.profileFragment == destination.getId());
            if (destination.getId() == R.id.dashboardFragment || R.id.profileFragment == destination.getId() || R.id.addMealFragment == destination.getId()) {
                if (navController.getGraph().getId() != R.id.main_nav_graph) {
                    navController.setGraph(R.navigation.main_nav_graph);
                }
            } else {
                if (navController.getGraph().getId() != R.id.nav_graph) {
                    navController.setGraph(R.navigation.nav_graph);
                }
            }
        });
    }

    private void setVisibleBottomNavigation(boolean isVisible) {
        if (isVisible) {
            binding.bottomNavigationView.setVisibility(View.VISIBLE);

        } else {
            binding.bottomNavigationView.setVisibility(View.GONE);
        }
    }
}

