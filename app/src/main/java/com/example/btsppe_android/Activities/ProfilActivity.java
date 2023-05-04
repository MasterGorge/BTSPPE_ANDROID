package com.example.btsppe_android.Activities;

import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import android.os.Bundle;

public class ProfilActivity extends AppCompatActivity {


        private String serverUrl = "https://protfoliomartinbillault.000webhostapp.com/gsb/login.php"; // Remplacer par l'URL de votre API PHP
        private String username = "user123"; // Remplacer par votre nom d'utilisateur
        private String password = "pass123"; // Remplacer par votre mot de passe
        private String result = ""; // Variable pour stocker le résultat de la requête API

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            // Exécuter la requête API dans un thread séparé pour éviter de bloquer l'interface utilisateur
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // Encoder les paramètres de la requête dans l'URL
                        String encodedUsername = URLEncoder.encode(username, "UTF-8");
                        String encodedPassword = URLEncoder.encode(password, "UTF-8");
                        String urlParameters = "username=" + encodedUsername + "&password=" + encodedPassword;

                        // Ouvrir une connexion HTTP avec l'API PHP
                        URL url = new URL(serverUrl + "?" + urlParameters);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");

                        // Lire le résultat de la requête depuis le flux d'entrée de la connexion HTTP
                        InputStream inputStream = connection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            result += line;
                        }

                        // Fermer les flux de lecture et la connexion HTTP
                        bufferedReader.close();
                        inputStream.close();
                        connection.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

            // Attendre la fin du thread de requête API avant de continuer l'exécution du code
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Utiliser la variable "result" pour traiter les données récupérées depuis la base de données
            Log.d("API Result", result);
        }
    }