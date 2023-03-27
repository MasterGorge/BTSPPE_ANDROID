package com.example.btsppe_android.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.btsppe_android.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    Button login;
    String userVar, passVar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userVar = username.getText().toString();
                passVar = password.getText().toString();
                if (userVar.equals("")) {
                    Toast.makeText(getApplicationContext(), "Username cannot be blank", Toast.LENGTH_SHORT).show();
                } else if (passVar.equals("")) {
                    Toast.makeText(getApplicationContext(), "password cannot be blank", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(getApplicationContext(), "login method to proceed", Toast.LENGTH_SHORT).show();
                    Login lg = new Login(MainActivity.this);
                    lg.execute();
                }
            }
        });
    }


    class Login extends AsyncTask<String, Void, Integer> {

        AlertDialog alertDialog;
        Context context;
        ProgressDialog progressDialog;

        Login(Context ctx) {
            this.context = ctx;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(context, "", "Logging you in... Please wait");
        }

        @Override
        protected Integer doInBackground(String... params) {
            String login_url = "https://protfoliomartinbillault.000webhostapp.com/gsb/login.php";
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(userVar, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(passVar, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();

                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                    bufferedReader.close();
                    inputStream.close();

                    JSONObject jsonObject = new JSONObject(response.toString());
                    return jsonObject.getInt("status");
                } else {
                    return HttpURLConnection.HTTP_BAD_REQUEST;
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return HttpURLConnection.HTTP_BAD_REQUEST;
            }
        }

        @Override
        protected void onPostExecute(Integer status) {
            progressDialog.dismiss();

            if (status == HttpURLConnection.HTTP_OK) {
                username.setText("");
                password.setText("");
                Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show();
                Intent secondPage = new Intent(MainActivity.this,SecondActivity.class);
                Bundle b=new Bundle();
                b.putString("user", username.getText().toString());
                b.putString("password", password.getText().toString());

                secondPage.putExtras(b);
                startActivity(secondPage);
            } else if (status == HttpURLConnection.HTTP_BAD_REQUEST) {
                Toast.makeText(context, "Incorrect username or password", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Failed to connect to server. Please check your internet connection and try again.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public static class SecondActivity {
    }
}