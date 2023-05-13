package com.example.btsppe_android.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btsppe_android.R;

public class FichesFraisActivity extends AppCompatActivity {

    private EditText etNom, etPrenom, etPoste, etMois, etDates, etFraisHebergement, etFraisRepas, etFraisTransport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiches_frais);

        // Récupération des références des éléments de l'interface utilisateur
        etNom = findViewById(R.id.etNom);
        etPrenom = findViewById(R.id.etPrenom);
        etPoste = findViewById(R.id.etPoste);
        etMois = findViewById(R.id.etMois);
        etDates = findViewById(R.id.etDates);
        etFraisHebergement = findViewById(R.id.etFraisHebergement);
        etFraisRepas = findViewById(R.id.etFraisRepas);
        etFraisTransport = findViewById(R.id.etFraisTransport);

        Button btnEnvoyer = findViewById(R.id.btnEnvoyer);
        btnEnvoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                envoyerDonnees();
            }
        });
    }

    public void envoyerDonnees() {
        // Récupérer les valeurs des champs de texte
        String nom = etNom.getText().toString();
        String prenom = etPrenom.getText().toString();
        String poste = etPoste.getText().toString();
        String mois = etMois.getText().toString();
        String dates = etDates.getText().toString();
        String fraisHebergement = etFraisHebergement.getText().toString();
        String fraisRepas = etFraisRepas.getText().toString();
        String fraisTransport = etFraisTransport.getText().toString();

        // Envoyer les données au serveur
        // ...

        // Afficher un message de succès
        Toast.makeText(this, "Données envoyées avec succès", Toast.LENGTH_SHORT).show();
    }
}
