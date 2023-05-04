package com.example.btsppe_android.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.example.btsppe_android.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        // Ajoutez ici le code pour la vue Home
                        return true;
                    case R.id.navigation_search:
                        // Ajoutez ici le code pour la vue Search
                        return true;
                    case R.id.navigation_profile:
                        // Ajoutez ici le code pour la vue Profile
                        return true;
                }
                return false;
            }
        });
    }
}