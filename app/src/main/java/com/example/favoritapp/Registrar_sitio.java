package com.example.favoritapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.favoritapp.ado.SitiosADO;
import com.example.favoritapp.clases.Mensajes;
import com.example.favoritapp.modelos.Sitios;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Context contexto = this;

        txtNombre = (EditText) findViewById(R.id.registrarSitio_txtNombre);
        txtDescripcion = (EditText) findViewById(R.id.registrarSitio_txtDescripcion);
        txtLatitud = (EditText) findViewById(R.id.registrarSitio_txtLatitud);
        txtLongitud = (EditText) findViewById(R.id.registrarSitio_txtLongitud);
        spinnerSitio = (Spinner) findViewById(R.id.registrarSitio_spnTipo);
        Button btnGuardar = (Button) findViewById(R.id.registrarSitio_btnGuardar);
        Button btnVolver = (Button) findViewById(R.id.registrarSitio_btnCancelar);
        ImageButton btnObtenersitio = (ImageButton) findViewById(R.id.registrarSitio_ibtnObtenerUbicacion);

        //Conexi??n al mapa

        btnObtenersitio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vistamapa = new Intent (contexto,Mapa.class);
                startActivityForResult(vistamapa, 1000);

            }
        });


        android.widget.ArrayAdapter<String> adaptador = new android.widget.ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item);

        adaptador.add("Tienda");
        adaptador.add("Mecanico");
        adaptador.add("Hotel");
        adaptador.add("Iglesia");
        adaptador.add("Punto de Control");
        adaptador.add("Su posici??n actual");

        spinnerSitio.setAdapter(adaptador);

        Bundle parametros = getIntent().getExtras();
        if(parametros!=null)
            if(parametros.containsKey("id"))
            {
                SitiosADO db = new SitiosADO(this);
                long id = parametros.getLong("id");
                registro = db.obtenerSitio(id);
                if(registro==null)
                    database.getReference().child("Sitios").child(String.valueOf(id)).get().addOnCompleteListener(this, new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            registro = task.getResult().getValue(Sitios.class);
                            cargarDatos();

                        }
                    });
                else
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
                        SitiosADO registroADO = new SitiosADO(view.getContext());
                        Sitios sit = new Sitios();
                        sit.setNombre(nombre);
                        sit.setDescripcion(descripcion);
                        sit.setTipo(tipo);
                        sit.setLatitud(latitud);
                        sit.setLongitud(longitud);
                        long idInsercion = registroADO.insertar(sit);
                        sit.setId((int) idInsercion);
                        registro = sit;

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

                    // Write a message to the database
                    database.getReference().child("Sitios").child(String.valueOf(registro.getId())).setValue(registro);
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
        //this.spinnerSitio.getSelectedItem(this.registro.getTipo());
        this.txtLatitud.setText( String.valueOf(this.registro.getLatitud()));
        this.txtLongitud.setText( String.valueOf(this.registro.getLongitud()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1000)
        {
            Bundle datos = data.getExtras();
            this.txtLatitud.setText(String.valueOf(datos.getDouble("latitud")));
            this.txtLongitud.setText(String.valueOf(datos.getDouble("longitud")));
        }
    }
}