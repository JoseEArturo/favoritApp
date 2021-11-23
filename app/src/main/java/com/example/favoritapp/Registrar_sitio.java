package com.example.favoritapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.favoritapp.ado.SitiosADO;
import com.example.favoritapp.clases.Mensajes;
import com.example.favoritapp.modelos.Sitios;

public class Registrar_sitio extends AppCompatActivity {

    private Sitios registro=null;
    private EditText txtNombre;
    private EditText txtDescripcion;
    private EditText txtLatitud;
    private EditText txtLongitud;
    private Spinner spinnerSitio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_sitio);

        txtNombre = (EditText) findViewById(R.id.registrarSitio_txtNombre);
        txtDescripcion = (EditText) findViewById(R.id.registrarSitio_txtDescripcion);
        txtLatitud = (EditText) findViewById(R.id.registrarSitio_txtLatitud);
        txtLongitud = (EditText) findViewById(R.id.registrarSitio_txtLongitud);
        spinnerSitio = (Spinner) findViewById(R.id.registrarSitio_spnTipo);
        Button btnGuardar = (Button) findViewById(R.id.registrarSitio_btnGuardar);
        Button btnVolver = (Button) findViewById(R.id.registrarSitio_btnCancelar);

        android.widget.ArrayAdapter<String> adaptador = new android.widget.ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item);

        adaptador.add("Tienda");
        adaptador.add("Mecanico");
        adaptador.add("Hotel");
        adaptador.add("Iglesia");
        adaptador.add("Punto de Control");
        adaptador.add("Su posición actual");

        spinnerSitio.setAdapter(adaptador);

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
                String tipo = spinnerSitio.getSelectedItem().toString();
                double latitud = 0;
                double longitud = 0;

                try {
                    latitud = Double.parseDouble(txtLatitud.getText().toString());
                    longitud = Double.parseDouble(txtLongitud.getText().toString());
                }
                catch (Exception ex) {
                    txtLatitud.setText("0");
                    txtLongitud.setText("0");
                }

                if(validarCampos(nombre, descripcion, tipo, latitud, longitud))
                {
                    if(registro!=null)
                    {
                        registro.setNombre(nombre);
                        registro.setDescripcion(descripcion);
                        registro.setTipo(tipo);
                        registro.setLatitud(latitud);
                        registro.setLongitud(longitud);

                        SitiosADO db = new SitiosADO(view.getContext());
                        if(db.editar(registro))
                            new Mensajes(view.getContext()).confirmSi("Registro actualizado", "Se ha actualizado el registro correctamente.", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    onBackPressed();
                                }
                            });
                        else
                            new Mensajes(view.getContext()).alert("Error", "Se ha producido un error al intentar actualizar el registro.");
                    }
                    else {
                        SitiosADO registro = new SitiosADO(view.getContext());
                        Sitios sit = new Sitios();
                        sit.setNombre(nombre);
                        sit.setDescripcion(descripcion);
                        sit.setTipo(tipo);
                        sit.setLatitud(latitud);
                        sit.setLongitud(longitud);
                        long idInsercion = registro.insertar(sit);

                        if (idInsercion > 0)
                            new Mensajes(view.getContext()).confirmSi("Registro insertado", "Se ha insertado el registro correctamente con el codigo " + String.valueOf(idInsercion), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    onBackPressed();
                                }
                            });
                        else
                            new Mensajes(view.getContext()).alert("Error", "Se ha producido un error al intentar insertar el registro.");
                    }

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
    public static boolean validarCampos(String nombre, String descripcion, String tipo, double latitud, double longitud)
    {
        boolean camposAceptados = false;

        if((!nombre.isEmpty() && !descripcion.isEmpty() && !tipo.isEmpty() && !(latitud==0) && !(longitud==0)))
            camposAceptados=true;

        return camposAceptados;
    }

    public void cargarDatos()
    {
        this.txtNombre.setText(this.registro.getNombre());
        this.txtDescripcion.setText(this.registro.getDescripcion());
        this.spinnerSitio.setSelection(Integer.parseInt(this.registro.getTipo()));
        this.txtLatitud.setText( String.valueOf(this.registro.getLatitud()));
        this.txtLongitud.setText( String.valueOf(this.registro.getLongitud()));
    }
}