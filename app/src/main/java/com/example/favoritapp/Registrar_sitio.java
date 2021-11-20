package com.example.favoritapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.favoritapp.ado.SitiosADO;
import com.example.favoritapp.clases.Mensajes;
import com.example.favoritapp.modelos.Sitios;

public class Registrar_sitio extends AppCompatActivity {

    private Sitios registro=null;
    private EditText txtNombre;
    private EditText txtDescripcion;
    private EditText txtLatitud;
    private EditText txtLongitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_sitio);

        txtNombre = (EditText) findViewById(R.id.registrarSitio_txtNombre);
        txtDescripcion = (EditText) findViewById(R.id.registrarSitio_txtDescripcion);
        txtLatitud = (EditText) findViewById(R.id.registrarSitio_txtLatitud);
        txtLongitud = (EditText) findViewById(R.id.registrarSitio_txtLongitud);
        Button btnGuardar = (Button) findViewById(R.id.registrarSitio_btnGuardar);
        Button btnVolver = (Button) findViewById(R.id.registrarSitio_btnCancelar);

        Bundle parametros = getIntent().getExtras();
        if(parametros!=null)
            if(parametros.containsKey("id"))
            {
                SitiosADO db = new SitiosADO(this);
                Sitios sit = db.obtenerSitio(parametros.getLong("id"));
                this.registro = sit;
                cargarDatos();
            }

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = txtNombre.getText().toString();
                String descripcion = txtDescripcion.getText().toString();
                String la = txtLatitud.getText().toString();
                String lo = txtLongitud.getText().toString();
                double latitud=Double.valueOf(la).doubleValue();
                double longitud=Double.valueOf(lo).doubleValue();

                if(validarCampos(nombre, descripcion, latitud, longitud))
                {
                    if(registro!=null)
                    {
                        registro.setNombre(nombre);
                        registro.setDescripcion(descripcion);
                        registro.setLatitud(latitud);
                        registro.setLongitud(longitud);

                        SitiosADO db = new SitiosADO(view.getContext());
                        if(db.editar(registro))
                            new Mensajes(view.getContext()).alert("Registro actualizado", "Se ha actualizado el registro correctamente.");
                        else
                            new Mensajes(view.getContext()).alert("Error", "Se ha producido un error al intentar actualizar el registro.");
                    }
                    else {
                        SitiosADO registro = new SitiosADO(view.getContext());
                        Sitios sit = new Sitios();
                        sit.setNombre(nombre);
                        sit.setDescripcion(descripcion);
                        sit.setLatitud(latitud);
                        sit.setLongitud(longitud);
                        long idInsercion = registro.insertar(sit);
                        if (idInsercion > 0)
                            new Mensajes(view.getContext()).alert("Registro insertado", "Se ha insertado el registro correctamente con el codigo " + String.valueOf(idInsercion));
                        else
                            new Mensajes(view.getContext()).alert("Error", "Se ha producido un error al intentar insertar el registro.");
                    }
                    onBackPressed();
                }
                else
                {
                    new Mensajes(view.getContext()).alert("Advertencia", "Digite los campos vacios.");
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
    public static boolean validarCampos(String nombre, String descripcion, double latitud, double longitd)
    {
        boolean camposAceptados = false;

        if((!nombre.isEmpty() && !descripcion.isEmpty() /*&& !latitud.isEmpty() && !longitd.isEmpty()*/))
            camposAceptados=true;

        return camposAceptados;
    }

    public void cargarDatos()
    {
        this.txtNombre.setText(this.registro.getNombre());
        this.txtDescripcion.setText(this.registro.getDescripcion());
        this.txtLatitud.setText((int) this.registro.getLatitud());
        this.txtLongitud.setText((int) this.registro.getLongitud());
    }
}