package com.example.btsppe_android.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btsppe_android.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FichesFraisActivity extends AppCompatActivity {

    private EditText etNom, etPrenom, etPoste, etMois, etDates, etFraisHebergement, etFraisRepas, etFraisTransport, etAutres;
    private Button btnEnvoyer;

    private static final String API_URL = "https://connexionapi.000webhostapp.com/ApiFichesFrais.php";
    private String token = ""; // Store the token of the logged-in user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiches_frais);

        etNom = findViewById(R.id.etNom);
        etPrenom = findViewById(R.id.etPrenom);
        etPoste = findViewById(R.id.etPoste);
        etMois = findViewById(R.id.etMois);
        etDates = findViewById(R.id.etDates);
        etFraisHebergement = findViewById(R.id.etFraisHebergement);
        etFraisRepas = findViewById(R.id.etFraisRepas);
        etFraisTransport = findViewById(R.id.etFraisTransport);
        etAutres = findViewById(R.id.etAutres);
        btnEnvoyer = findViewById(R.id.btnEnvoyer);

        btnEnvoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFicheFrais();
            }
        });

        // Récupérer le token de l'utilisateur connecté depuis l'activité précédente
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            token = extras.getString("token");
        }
    }

    private void sendFicheFrais() {
        String nom = etNom.getText().toString().trim();
        String prenom = etPrenom.getText().toString().trim();
        String poste = etPoste.getText().toString().trim();
        String mois = etMois.getText().toString().trim();
        String dates = etDates.getText().toString().trim();
        String fraisHebergement = etFraisHebergement.getText().toString().trim();
        String fraisRepas = etFraisRepas.getText().toString().trim();
        String fraisTransport = etFraisTransport.getText().toString().trim();
        String autres = etAutres.getText().toString().trim();

        // Vérifier si tous les champs sont remplis
        if (nom.isEmpty() || prenom.isEmpty() || poste.isEmpty() || mois.isEmpty() || dates.isEmpty() || fraisHebergement.isEmpty() || fraisRepas.isEmpty() || fraisTransport.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        // Créer un objet JSON avec les données
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
            jsonObject.put("autres", autres);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Envoyer la requête POST à l'API
        new SendFicheFraisTask().execute(jsonObject.toString());
    }

    private class SendFicheFraisTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            String jsonInput = params[0];
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(API_URL);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Authorization", "Bearer " + token);
                urlConnection.setDoOutput(true);

                OutputStream outputStream = urlConnection.getOutputStream();
                outputStream.write(jsonInput.getBytes());
                outputStream.flush();
                outputStream.close();

                return urlConnection.getResponseCode();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer responseCode) {
            if (responseCode != null) {
                if (responseCode == HttpURLConnection.HTTP_CREATED) {
                    Toast.makeText(FichesFraisActivity.this, "Fiche frais envoyée avec succès", Toast.LENGTH_SHORT).show();
                    // Réinitialiser les champs du formulaire
                    etNom.setText("");
                    etPrenom.setText("");
                    etPoste.setText("");
                    etMois.setText("");
                    etDates.setText("");
                    etFraisHebergement.setText("");
                    etFraisRepas.setText("");
                    etFraisTransport.setText("");
                    etAutres.setText("");
                } else {
                    Toast.makeText(FichesFraisActivity.this, "Une erreur s'est produite. Veuillez réessayer", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(FichesFraisActivity.this, "Une erreur s'est produite. Veuillez vérifier votre connexion Internet", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
