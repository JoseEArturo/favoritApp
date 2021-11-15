package com.example.favoritapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Favoritos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        FloatingActionButton btnAgregarSitio = (FloatingActionButton) findViewById(R.id.favoritos_fbtnNuevo);
        FloatingActionButton btnVolver = (FloatingActionButton) findViewById(R.id.favoritos_fbtnRegresar);

        btnAgregarSitio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Favoritos.this, Registrar_sitio.class));
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Favoritos.this, MainActivity.class));
            }
        });
    }
}