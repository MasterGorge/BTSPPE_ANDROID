package com.example.btsppe_android.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btsppe_android.FichesFraisActivity;
import com.example.btsppe_android.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class acceuilActivity extends AppCompatActivity {

    private SharedPreferences tokenPrefs;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);

        tokenPrefs = getSharedPreferences("TokenPrefs", Context.MODE_PRIVATE);
        token = tokenPrefs.getString("token", "");

        if (!token.isEmpty()) {
            // L'utilisateur est connecté, vous pouvez exécuter les actions nécessaires ici
            Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT).show();
        } else {
            // L'utilisateur n'est pas connecté, vous pouvez rediriger vers l'écran de connexion
            Intent loginIntent = new Intent(acceuilActivity.this, MainActivity.class);
            startActivity(loginIntent);
            finish(); // Terminer cette activité pour empêcher l'utilisateur de revenir ici en appuyant sur le bouton Retour
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        // Redirection vers l'accueil (peut-être vous n'en avez pas besoin)
                        Intent homeIntent = new Intent(acceuilActivity.this, acceuilActivity.class);
                        startActivity(homeIntent);
                        return true;
                    case R.id.navigation_search:
                        // Redirection vers FichesFraisActivity
                        Intent ficheFraisIntent = new Intent(acceuilActivity.this, FichesFraisActivity.class);
                        startActivity(ficheFraisIntent);
                        return true;
                    case R.id.navigation_profile:
                        // Redirection vers le profil (peut-être vous n'en avez pas besoin)
                        Intent profileIntent = new Intent(acceuilActivity.this, ProfilActivity.class);
                        startActivity(profileIntent);
                        return true;
                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }
            }
        });
    }
}


