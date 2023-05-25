package com.example.btsppe_android.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.btsppe_android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class afficher_fichefrais extends AppCompatActivity {

    private SharedPreferences tokenPrefs;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficher_fichefrais);

        tokenPrefs = getSharedPreferences("TokenPrefs", Context.MODE_PRIVATE);
        token = tokenPrefs.getString("token", "");

        TableLayout tableLayout = findViewById(R.id.tableLayout);

        // Appel à l'API pour récupérer les données des fiches de frais
        new FetchFichesFraisTask().execute();
    }

    private class FetchFichesFraisTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String apiUrl = "https://connexionapi.000webhostapp.com/AfficherFichesFrais.php";
            RequestQueue queue = Volley.newRequestQueue(afficher_fichefrais.this);
            StringRequest request = new StringRequest(Request.Method.GET, apiUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Traitez la réponse de l'API ici, par exemple en mettant à jour l'interface utilisateur
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        int status = jsonResponse.getInt("status");

                        if (status == 200) {
                            JSONArray jsonArray = jsonResponse.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String mois = jsonObject.getString("mois");
                                String date = jsonObject.getString("date");
                                int hebergement = jsonObject.getInt("hebergement");
                                int repas = jsonObject.getInt("repas");
                                int transport = jsonObject.getInt("transport");
                                int prixTotal = jsonObject.getInt("prix_total");
                                int autres = jsonObject.getInt("autres");
                                String etat = jsonObject.getString("etat"); // Nouvelle ligne pour récupérer l'état

                                TableRow row = new TableRow(afficher_fichefrais.this);

                                TextView textViewHebergement = new TextView(afficher_fichefrais.this);
                                textViewHebergement.setText(String.valueOf(hebergement));
                                row.addView(textViewHebergement);

                                TextView textViewRepas = new TextView(afficher_fichefrais.this);
                                textViewRepas.setText(String.valueOf(repas));
                                row.addView(textViewRepas);

                                TextView textViewTransport = new TextView(afficher_fichefrais.this);
                                textViewTransport.setText(String.valueOf(transport));
                                row.addView(textViewTransport);

                                TextView textViewAutres = new TextView(afficher_fichefrais.this);
                                textViewAutres.setText(String.valueOf(autres));
                                row.addView(textViewAutres);

                                TextView textViewPrixTotal = new TextView(afficher_fichefrais.this);
                                textViewPrixTotal.setText(String.valueOf(prixTotal));
                                row.addView(textViewPrixTotal);

                                TextView textViewMois = new TextView(afficher_fichefrais.this);
                                textViewMois.setText(mois);
                                row.addView(textViewMois);

                                TextView textViewDate = new TextView(afficher_fichefrais.this);
                                textViewDate.setText(date);
                                row.addView(textViewDate);

                                TextView textViewEtat = new TextView(afficher_fichefrais.this); // Nouvelle TextView pour l'état
                                textViewEtat.setText(etat); // Afficher l'état dans la TextView
                                row.addView(textViewEtat); // Ajouter la TextView à la ligne

                                TableLayout tableLayout = findViewById(R.id.tableLayout);
                                tableLayout.addView(row);
                            }
                        } else {
                            // Gérer le cas où aucune donnée n'est trouvée ou une autre erreur
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Gérez les erreurs de l'API ici
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + token);
                    return headers;
                }
            };

            queue.add(request);
            return null;
        }
    }
}
