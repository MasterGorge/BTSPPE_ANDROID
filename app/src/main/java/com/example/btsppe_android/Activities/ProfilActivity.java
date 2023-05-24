package com.example.btsppe_android.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.example.btsppe_android.R;

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

    private class GetProfileDataTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            SharedPreferences tokenPrefs = getSharedPreferences("TokenPrefs", Context.MODE_PRIVATE);
            String token = tokenPrefs.getString("token", "");

            String apiUrl = "https://example.com/api/profile.php"; // Remplacez par l'URL de votre API PHP

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

                return response.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            if (response != null) {
                try {
                    // Convertir la réponse JSON en objet JSON
                    JSONObject jsonObject = new JSONObject(response);

                    // Vérifier si la réponse contient les données du profil de l'utilisateur
                    if (jsonObject.has("name") && jsonObject.has("address") && jsonObject.has("email") && jsonObject.has("poste")) {
                        String name = jsonObject.getString("name");
                        String address = jsonObject.getString("address");
                        String email = jsonObject.getString("email");
                        String poste = jsonObject.getString("poste");

                        // Afficher les données dans les TextView correspondants
                        textViewName.setText(name);
                        textViewAddress.setText(address);
                        textViewAdressMail.setText(email);
                        textViewPoste.setText(poste);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
