package com.example.favoritapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.favoritapp.ado.UsuarioADO;
import com.example.favoritapp.clases.Mensajes;
import com.example.favoritapp.modelos.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registro extends AppCompatActivity {

    private FirebaseAuth autenticacion;
    private static EditText txtEmail;
    private static EditText txtClave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        autenticacion = FirebaseAuth.getInstance();


        Button btnVolver = (Button) findViewById(R.id.usuarios_insertar_btnVolver);
        Button btnGuardar = (Button) findViewById(R.id.usuarios_insertar_btnGuardar);
        txtEmail = (EditText) findViewById(R.id.usuarios_insertar_txtEmail);
        txtClave = (EditText) findViewById(R.id.usuarios_insertar_txtClave);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validarCamposVacios())
                {
                    new Mensajes(view.getContext()).alert("Advertencia", "Digite los campos en blanco.");
                }
                else {
                    autenticacion.createUserWithEmailAndPassword(
                            txtEmail.getText().toString(),
                            txtClave.getText().toString()).addOnCompleteListener((Activity) view.getContext(),
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())
                                    {
                                        Intent i = new Intent(view.getContext(), Login.class);
                                        startActivity(i);
                                    }
                                    else
                                    {
                                        new Mensajes(view.getContext()).alert("Advertencia", "No se pudo registrar el usuario.");
                                    }
                                }
                            });
                }
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
    }

    public static boolean validarCamposVacios()
    {
        boolean vacio = true;

        try {
            if(!txtEmail.getText().toString().isEmpty() && !txtClave.getText().toString().isEmpty())
                vacio=false;
        }
        catch (Exception ex)
        {

        }

        return vacio;
    }
}