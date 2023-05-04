package com.example.btsppe_android.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btsppe_android.R;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class FichesFraisActivity extends AppCompatActivity {

        // Les champs du formulaire
        private EditText nom;
        private EditText prenom;
        private EditText poste;
        private EditText mois;
        private EditText date;
        private EditText hebergement;
        private EditText repas;
        private EditText transport;
        private EditText autres;
        private EditText description;

        // Le bouton d'envoi du formulaire
        private Button envoyer;

        @SuppressLint("MissingInflatedId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            // Récupération des vues du formulaire
            nom = findViewById(R.id.nom);
            prenom = findViewById(R.id.prenom);
            poste = findViewById(R.id.poste);
            mois = findViewById(R.id.mois);
            date = findViewById(R.id.date);
            hebergement = findViewById(R.id.hebergement);
            repas = findViewById(R.id.repas);
            transport = findViewById(R.id.transport);
            autres = findViewById(R.id.autres);
            description = findViewById(R.id.description);
            envoyer = findViewById(R.id.envoyer);

            // Ajout du listener sur le bouton d'envoi du formulaire
            envoyer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Récupération des valeurs des champs du formulaire
                    String nomValue = nom.getText().toString();
                    String prenomValue = prenom.getText().toString();
                    String posteValue = poste.getText().toString();
                    String moisValue = mois.getText().toString();
                    String dateValue = date.getText().toString();
                    String hebergementValue = hebergement.getText().toString();
                    String repasValue = repas.getText().toString();
                    String transportValue = transport.getText().toString();
                    String autresValue = autres.getText().toString();
                    String descriptionValue = description.getText().toString();

                    // Envoi des données du formulaire à l'API
                    try {
                        URL url = new URL("https://connexionapi.000webhostapp.com/ApiFichesFrais.php");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        conn.setRequestProperty("charset", "utf-8");
                        conn.setDoOutput(true);

                        // Construction des données à envoyer
                        String data = URLEncoder.encode("nom", "UTF-8") + "=" + URLEncoder.encode(nomValue, "UTF-8");
                        data += "&" + URLEncoder.encode("prenom", "UTF-8") + "=" + URLEncoder.encode(prenomValue, "UTF-8");
                        data += "&" + URLEncoder.encode("poste", "UTF-8") + "=" + URLEncoder.encode(posteValue, "UTF-8");
                        data += "&" + URLEncoder.encode("mois", "UTF-8") + "=" + URLEncoder.encode(moisValue, "UTF-8");
                        data += "&" + URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(dateValue, "UTF-8");
                        data += "&" + URLEncoder.encode("hebergement", "UTF-8") + "=" + URLEncoder.encode(hebergementValue, "UTF-8");
                        data += "&" + URLEncoder.encode("repas", "UTF-8") + "=" + URLEncoder.encode(repasValue, "UTF-8");
                        data += "&" + URLEncoder.encode("transport", "UTF-8") + "=" + URLEncoder.encode(transportValue, "UTF-8");
                        data += "&" + URLEncoder.encode("autres", "UTF-8") + "=" + URLEncoder.encode(autresValue, "UTF-8");
                        data += "&" + URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(descriptionValue, "UTF-8");

                        // Envoi des données
                        conn.getOutputStream().write(data.getBytes("UTF-8"));

                        // Récupération de la réponse de l'API
                        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                            // Traitement de la réponse si besoin
                        } else {
                            // Traitement de l'erreur si besoin
                        }

                        conn.disconnect();
                    } catch (ProtocolException e) {
                        throw new RuntimeException(e);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                };
            });
        }
}

