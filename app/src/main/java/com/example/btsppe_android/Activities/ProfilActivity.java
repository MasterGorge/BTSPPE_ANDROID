package com.example.btsppe_android.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.example.btsppe_android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProfilActivity extends AppCompatActivity {

    private TextView textViewName;
    private TextView textViewAddress;
    private TextView textViewAdressMail;
    private TextView textViewPoste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        textViewName = findViewById(R.id.textViewName);
        textViewAddress = findViewById(R.id.textViewAddress);
        textViewAdressMail = findViewById(R.id.textViewAdressMail);
        textViewPoste = findViewById(R.id.textViewPoste);

        // Appeler la tâche asynchrone pour récupérer les données du profil de l'utilisateur
        GetProfileDataTask getProfileDataTask = new GetProfileDataTask();
        getProfileDataTask.execute();
    }

    private class GetProfileDataTask extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(Void... voids) {
            SharedPreferences tokenPrefs = getSharedPreferences("TokenPrefs", Context.MODE_PRIVATE);
            String token = tokenPrefs.getString("token", "");

            String apiUrl = "https://protfoliomartinbillault.000webhostapp.com/gsb/ApiProfil.php"; // Remplacez par l'URL de votre API PHP

            try {
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Authorization", "Bearer " + token); // Ajouter le token d'authentification dans l'en-tête de la requête

                // Lire la réponse de l'API
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }
                bufferedReader.close();
                inputStream.close();
                connection.disconnect();

                // Convertir la réponse JSON en objet JSON
                return new JSONObject(response.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (jsonObject != null) {
                try {
                    // Vérifier si la réponse contient les données du profil de l'utilisateur
                    if (jsonObject.has("status") && jsonObject.getInt("status") == 200 && jsonObject.has("data")) {
                        JSONArray dataArray = jsonObject.getJSONArray("data");

                        // Récupérer le premier objet JSON dans le tableau de données
                        JSONObject profileData = dataArray.getJSONObject(0);

                        // Vérifier si le profil contient les clés nécessaires
                        if (profileData.has("nom") && profileData.has("adresse") && profileData.has("mail") && profileData.has("poste")) {
                            String name = profileData.getString("nom");
                            String address = profileData.getString("adresse");
                            String email = profileData.getString("mail");
                            String poste = profileData.getString("poste");

                            // Afficher les données dans les TextView correspondants
                            textViewName.setText(name);
                            textViewAddress.setText(address);
                            textViewAdressMail.setText(email);
                            textViewPoste.setText(poste);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
