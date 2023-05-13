package com.example.btsppe_android.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btsppe_android.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

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

        // Créer un objet JSON avec les données à envoyer
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nom", nom);
            jsonObject.put("prenom", prenom);
            jsonObject.put("poste", poste);
            jsonObject.put("mois", mois);
            jsonObject.put("date", dates);
            jsonObject.put("hebergement", fraisHebergement);
            jsonObject.put("repas", fraisRepas);
            jsonObject.put("transport", fraisTransport);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Envoyer les données au serveur
        sendJSONData(jsonObject.toString());

        // Afficher un message de succès
        Toast.makeText(this, "Données envoyées avec succès", Toast.LENGTH_SHORT).show();
    }

    private void sendJSONData(final String json) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // URL de l'API
                    URL url = new URL("https://connexionapi.000webhostapp.com/ApiFichesFrais.php");

                    // Ouvrir une connexion HTTP
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    // Configurer la requête POST
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setDoOutput(true);

                    // Envoyer les données JSON
                    DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());
                    outputStream.writeBytes(json);
                    outputStream.flush();
                    outputStream.close();

                    // Lire la réponse du serveur
                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        // Succès : faire quelque chose avec la réponse du serveur si nécessaire
                    } else {
                        // Erreur : gérer l'erreur de la requête
                    }

                    // Fermer la connexion
                    conn.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}



