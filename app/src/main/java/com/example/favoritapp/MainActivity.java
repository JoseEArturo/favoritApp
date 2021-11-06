package com.example.favoritapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    }
}