package com.example.favoritapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Mapa extends AppCompatActivity {

    private double latitud;
    private double longitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        FloatingActionButton btnVolver = (FloatingActionButton) findViewById(R.id.mapa_btnRegresar);
        FloatingActionButton btnGuardar = (FloatingActionButton) findViewById(R.id.mapa_btnAgregar);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Mapa.this, Registrar_sitio.class));
            }
        });

    }

    public void actualizarCoordenada(double lat, double lon)
    {
        this.latitud = lat;
        this.longitud = lon;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent i = new Intent();
        i.putExtra("latitud", this.latitud);
        i.putExtra("longitud", this.longitud);
        setResult(1000,i);
        finish();

    }
}