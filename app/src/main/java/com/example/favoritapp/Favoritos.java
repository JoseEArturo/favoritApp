package com.example.favoritapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.favoritapp.adapters.AdapterRecyclerView;
import com.example.favoritapp.ado.SitiosADO;
import com.example.favoritapp.modelos.Sitios;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Favoritos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        this.actualizarLista();

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
                onBackPressed();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        actualizarLista();
    }

    private void actualizarLista()
    {
        RecyclerView rcvUsuarios = (RecyclerView) findViewById(R.id.favoritos_rcwMarcadores);
        rcvUsuarios.setLayoutManager(new LinearLayoutManager(this));

        SitiosADO db = new SitiosADO(this);
        ArrayList<Sitios> usuarios = db.listar();

        AdapterRecyclerView adaptador = new AdapterRecyclerView(usuarios);
        rcvUsuarios.setAdapter(adaptador);

    }
}