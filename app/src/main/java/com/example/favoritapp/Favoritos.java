package com.example.favoritapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.favoritapp.adapters.AdapterRecyclerView;
import com.example.favoritapp.ado.SitiosADO;
import com.example.favoritapp.modelos.Sitios;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class Favoritos extends AppCompatActivity {

    ArrayList<Sitios> sitios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        try {
            database.setPersistenceEnabled(true);
        }
        catch (Exception ex)
        {}


        // Read from the database
        RecyclerView rcvUsuarios = (RecyclerView) findViewById(R.id.favoritos_rcwMarcadores);
        rcvUsuarios.setLayoutManager(new LinearLayoutManager(this));

        sitios = new ArrayList<>();
        database.getReference().child("Sitios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                sitios.clear();
                for(DataSnapshot nodoHijo : dataSnapshot.getChildren())
                {
                    Sitios us = nodoHijo.getValue(Sitios.class);
                    sitios.add(us);
                }

                AdapterRecyclerView adaptador = new AdapterRecyclerView(sitios, getSupportFragmentManager());
                rcvUsuarios.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

       // this.actualizarLista();

        FloatingActionButton btnAgregarSitio = (FloatingActionButton) findViewById(R.id.favoritos_fbtnNuevo);
        FloatingActionButton btnVolver = (FloatingActionButton) findViewById(R.id.favoritos_fbtnRegresar);
        SearchView buscador = (SearchView) findViewById(R.id.favoritos_Buscar);


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

        buscador.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                buscar(s);
                return true;
            }
        });

    }

    private void buscar(String s) {
        ArrayList <Sitios> miLista = new ArrayList<>();
        for(Sitios obj:sitios){
                if(obj.getNombre().toLowerCase().contains(s.toLowerCase())){
                    miLista.add(obj);
                }
                AdapterRecyclerView adapter = new AdapterRecyclerView(miLista, null);
                RecyclerView rcvUsuarios = (RecyclerView) findViewById(R.id.favoritos_rcwMarcadores);
                rcvUsuarios.setAdapter(adapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //actualizarLista();
    }

    private void actualizarLista()
    {
        RecyclerView rcvUsuarios = (RecyclerView) findViewById(R.id.favoritos_rcwMarcadores);
        rcvUsuarios.setLayoutManager(new LinearLayoutManager(this));

        SitiosADO db = new SitiosADO(this);
        ArrayList<Sitios> sitios = db.listar();

        AdapterRecyclerView adaptador = new AdapterRecyclerView(sitios, getSupportFragmentManager());
        rcvUsuarios.setAdapter(adaptador);

    }
}