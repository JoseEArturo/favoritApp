package com.example.favoritapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.favoritapp.clases.Mensajes;

public class Registrar_sitio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_sitio);

        Button btnGuardar = (Button) findViewById(R.id.registrarSitio_btnGuardar);
        Button btnVolver = (Button) findViewById(R.id.registrarSitio_btnCancelar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Mensajes(v.getContext()).alert("Atención", "La opción de guardado está temporalmente inactiva");
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registrar_sitio.this, Favoritos.class));
            }
        });
    }
}