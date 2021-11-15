package com.example.favoritapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Mapa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        FloatingActionButton btnVolver = (FloatingActionButton) findViewById(R.id.mapa_btnRegresar);
        FloatingActionButton btnGuardar = (FloatingActionButton) findViewById(R.id.mapa_btnAgregar);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Mapa.this, MainActivity.class));
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Mapa.this, Registrar_sitio.class));
            }
        });

    }
}