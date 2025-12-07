package com.example.berita.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.berita.R;
import com.example.berita.fragment.HomeFragment;
import com.example.berita.fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    private boolean isDarkMode = false;
    private static final String PREFS_NAME = "theme_prefs";
    private static final String KEY_DARK_MODE = "dark_mode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Load saved theme
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        isDarkMode = prefs.getBoolean(KEY_DARK_MODE, false);
        setTheme(isDarkMode ? R.style.Theme_Berita_Dark : R.style.Theme_Berita_Light);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottomNav);

        // Default fragment pertama
        loadFragment(new HomeFragment());

        // Update theme icon
        bottomNav.getMenu().findItem(R.id.nav_theme)
                .setIcon(isDarkMode ? R.drawable.ic_moon : R.drawable.ic_sun);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            Fragment fragment = null;

            if (id == R.id.nav_home) {
                fragment = new HomeFragment();
            } else if (id == R.id.nav_search) {
                fragment = new SearchFragment();
            } else if (id == R.id.nav_theme) {
                // Toggle theme
                isDarkMode = !isDarkMode;
                SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                editor.putBoolean(KEY_DARK_MODE, isDarkMode);
                editor.apply();

                // Recreate activity to apply theme
                recreate();
                return true;
            }

            if (fragment != null) loadFragment(fragment);
            return true;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerFragment, fragment)
                .commit();
    }
}
