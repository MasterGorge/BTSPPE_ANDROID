package com.example.btsppe_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class SecondActivity extends AppCompatActivity {
    private TextView Home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Home=findViewById(R.id.txtHome);
        Bundle b=getIntent().getExtras();
        Home.setText("Bienvenue Mr "+b.getString("user")+" votre MDP: "+b.getString("password"));

        MaterialButton retour = (MaterialButton) findViewById(R.id.retour);

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondPage = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(secondPage);
            }
        });

    }
}