package com.example.btsppe_android.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btsppe_android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class afficher_fichefrais extends AppCompatActivity {

    private TableLayout tableLayout;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficher_fichefrais);

        tableLayout = findViewById(R.id.tableLayout);

        // Récupérer le token de l'intent
        token = getIntent().getStringExtra("token");

        // Appeler la méthode pour récupérer les données JSON de l'API
        new FetchFicheFraisTask().execute();
    }

    private class FetchFicheFraisTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            String apiUrl = "https://connexionapi.000webhostapp.com/AfficherFichesFrais.php";
            String result = "";

            try {
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // Ajouter le token à la requête
                connection.setRequestProperty("Authorization", "Bearer " + token);

                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

                bufferedReader.close();
                inputStream.close();
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject response = new JSONObject(result);
                int status = response.getInt("status");
                JSONArray data = response.getJSONArray("data");

                if (status == 200) {
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject fiche = data.getJSONObject(i);

                        String date = fiche.getString("date");
                        String description = fiche.getString("description");
                        String deplacement = fiche.getString("deplacement");
                        String repas = fiche.getString("repas");
                        String hebergement = fiche.getString("hebergement");
                        String autres = fiche.getString("autres");

                        // Créer une nouvelle TableRow et y ajouter les TextViews
                        TableRow row = new TableRow(afficher_fichefrais.this);

                        TextView dateTextView = new TextView(afficher_fichefrais.this);
                        dateTextView.setText(date);
                        row.addView(dateTextView);

                        TextView descriptionTextView = new TextView(afficher_fichefrais.this);
                        descriptionTextView.setText(description);
                        row.addView(descriptionTextView);

                        TextView deplacementTextView = new TextView(afficher_fichefrais.this);
                        deplacementTextView.setText(deplacement);
                        row.addView(deplacementTextView);

                        TextView repasTextView = new TextView(afficher_fichefrais.this);
                        repasTextView.setText(repas);
                        row.addView(repasTextView);

                        TextView hebergementTextView = new TextView(afficher_fichefrais.this);
                        hebergementTextView.setText(hebergement);
                        row.addView(hebergementTextView);

                        TextView autresTextView = new TextView(afficher_fichefrais.this);
                        autresTextView.setText(autres);
                        row.addView(autresTextView);

                        // Ajouter la nouvelle TableRow au TableLayout
                        tableLayout.addView(row);
                    }
                } else {
                    // Afficher un message d'erreur en cas de statut différent de 200
                    String message = response.getString("message");
                    // TODO: Afficher le message d'erreur à l'utilisateur
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
