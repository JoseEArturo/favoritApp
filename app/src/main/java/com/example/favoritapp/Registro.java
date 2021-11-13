package com.example.favoritapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.favoritapp.ado.UsuarioADO;
import com.example.favoritapp.clases.Mensajes;
import com.example.favoritapp.modelos.Usuario;

public class Registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Button btnVolver = (Button) findViewById(R.id.usuarios_insertar_btnVolver);
        Button btnGuardar = (Button) findViewById(R.id.usuarios_insertar_btnGuardar);
        EditText txtNombres = (EditText) findViewById(R.id.usuarios_insertar_txtNombres);
        EditText txtApellidos = (EditText) findViewById(R.id.usuarios_insertar_txtApellidos);
        EditText txtEmail = (EditText) findViewById(R.id.usuarios_insertar_txtEmail);
        EditText txtClave = (EditText) findViewById(R.id.usuarios_insertar_txtClave);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombres = txtNombres.getText().toString();
                String apellidos = txtApellidos.getText().toString();
                String email = txtEmail.getText().toString();
                String clave = txtClave.getText().toString();

                if(validarCampos(nombres, apellidos, email, clave))
                {
                    UsuarioADO registro = new UsuarioADO(view.getContext());
                    Usuario us = new Usuario();
                    us.setNombres(nombres);
                    us.setApellidos(apellidos);
                    us.setEmail(email);
                    us.setClave(clave);
                    long id = registro.insertar(us);
                    if(id>0)
                        new Mensajes(view.getContext()).alert("Registro insertado", "Se ha insertado el registro correctamente con el codigo " + String.valueOf(id));
                    else
                        new Mensajes(view.getContext()).alert("Error", "Se ha producido un error al intentar insertar el registro.");

                    registro.listar();

                }
                else
                {
                    new Mensajes(view.getContext()).alert("Advertencia", "Digite los campos en blanco.");
                }
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registro.this, Login.class));
            }
        });
    }
    public static boolean validarCampos(String nombres, String apellidos, String email, String clave)
    {
        boolean camposAceptados = false;

        if((!nombres.isEmpty() && !apellidos.isEmpty() && !email.isEmpty() && !clave.isEmpty()))
            camposAceptados=true;

        return camposAceptados;
    }
}