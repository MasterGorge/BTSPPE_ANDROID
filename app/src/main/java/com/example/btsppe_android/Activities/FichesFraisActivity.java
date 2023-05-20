package com.example.btsppe_android.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.btsppe_android.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FichesFraisActivity extends AppCompatActivity {

    private EditText etNom, etPrenom, etPoste, etMois, etDates, etFraisHebergement, etFraisRepas, etFraisTransport, etAutres;
    private Button btnEnvoyer;

    private static final String API_URL = "https://connexionapi.000webhostapp.com/ApiFichesFrais.php";
    private SharedPreferences tokenPrefs;
    private String token;

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

        tokenPrefs = getSharedPreferences("TokenPrefs", Context.MODE_PRIVATE);
        token = tokenPrefs.getString("token", "");
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
        sendFicheFraisRequest(jsonObject.toString());
    }

    private void sendFicheFraisRequest(String jsonInput) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Traitez la réponse de l'API ici, par exemple en affichant un message de réussite
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
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Gérez les erreurs de l'API ici, par exemple en affichant un message d'erreur
                Toast.makeText(FichesFraisActivity.this, "Une erreur s'est produite. Veuillez réessayer", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return jsonInput.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        queue.add(request);
    }
}
