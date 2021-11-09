package com.example.favoritapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Button btnAcceder = (Button) findViewById(R.id.main_btnAcceder);
        EditText txtEmail = (EditText) findViewById(R.id.main_edtEmail);
        EditText txtClave = (EditText) findViewById(R.id.main_edClave);

        btnAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtEmail.getText().toString();
                String clave = txtClave.getText().toString();

                if(email.isEmpty() || clave.isEmpty()){
                    AlertDialog.Builder mensaje = new AlertDialog.Builder(view.getContext());
                    mensaje.setTitle(R.string.main_btnAcceder_errorTitulo);
                    mensaje.setMessage(R.string.main_btnAcceder_errorMensaje);
                    mensaje.create();
                    mensaje.show();
                }
            }
        });*/

        Button btnMapa = findViewById(R.id.main_btnMapa);
        Button btnFavoritos = findViewById(R.id.main_btnFavoritos);
        ImageButton btnAcercaDe = findViewById(R.id.main_btnAcercaDe);

        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Mapa.class));
            }
        });

        btnFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Favoritos.class));
            }
        });

        btnAcercaDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AcercaDe.class));
            }
        });
    }
}