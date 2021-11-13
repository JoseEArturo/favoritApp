package com.example.favoritapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bundle variables = getIntent().getExtras();

        Button btnAcceder = (Button) findViewById(R.id.main_btnAcceder);
        Button btnRegistro = (Button) findViewById(R.id.main_btnRegistro);
        EditText txtEmail = (EditText) findViewById(R.id.main_edtEmail);
        EditText txtClave = (EditText) findViewById(R.id.main_edClave);

        try {
            txtEmail.setText(variables.getString("email"));
            txtClave.setText(variables.getString("clave"));
        }catch (Exception ex){

        }


        btnAcceder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String email = txtEmail.getText().toString();
                String clave = txtClave.getText().toString();

                if(email.isEmpty() || clave.isEmpty()){
                    AlertDialog.Builder mensaje = new AlertDialog.Builder(view.getContext());
                    mensaje.setTitle(R.string.login_btnAcceder_vacioTitulo);
                    mensaje.setMessage(R.string.login_btnAcceder_vacioMensaje);
                    mensaje.create();
                    mensaje.show();
                }
                else if(email.equals(txtEmail.getText().toString()) && clave.equals(txtClave.getText().toString())){
                    Intent i = new Intent(view.getContext(), MainActivity.class);
                    startActivity(i);
                }
                else {
                    AlertDialog.Builder mensaje = new AlertDialog.Builder(view.getContext());
                    mensaje.setTitle(R.string.login_btnAcceder_errorTitulo);
                    mensaje.setMessage(R.string.login_btnAcceder_errorMensaje);
                    mensaje.create();
                    mensaje.show();
                }
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Registro.class));
            }
        });
    }
}