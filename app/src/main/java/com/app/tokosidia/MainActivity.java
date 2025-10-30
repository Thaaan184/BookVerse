// Lokasi: com/app/tokosidia/MainActivity.java
package com.app.tokosidia;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.app.tokosidia.databinding.ActivityMainBinding; // Import ViewBinding
import com.app.tokosidia.ui.BookListFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding; // Gunakan ViewBinding

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate layout menggunakan ViewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // a. Mengaktifkan Toolbar sebagai Action Bar utama (sesuai soal)
        setSupportActionBar(binding.toolbar);

        // b. Menampilkan BookListFragment sebagai tampilan awal
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new BookListFragment())
                    .commit();
        }
    }
}