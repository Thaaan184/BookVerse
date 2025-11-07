package com.app.bookverse;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.app.bookverse.api.RetrofitClient;
import com.app.bookverse.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inisialisasi RetrofitClient dengan konteks dari activity
        RetrofitClient.getClient(this);

        // 1. Set Toolbar sebagai Action Bar utama
        setSupportActionBar(binding.toolbar);

        // 2. Ambil NavController dari NavHostFragment
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        // 3. Hubungkan Action Bar (Toolbar) dengan NavController
        NavigationUI.setupActionBarWithNavController(this, navController);

        // 4. Hubungkan BottomNavigationView dengan NavController
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController);
    }

    // Dibutuhkan agar tombol "Up" (kembali) di Toolbar berfungsi
    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}