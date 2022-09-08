package com.example.login.navbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.login.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class hostNav extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_nav);
       BottomNavigationView bottomNavigationView = findViewById(R.id.BottomNavigationView);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);

        if (navHostFragment != null) {

            NavController navController = navHostFragment.getNavController();

            // Setup NavigationUI here
            NavigationUI.setupWithNavController(bottomNavigationView, navController);
        }

    }
}