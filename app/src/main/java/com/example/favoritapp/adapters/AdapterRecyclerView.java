package com.example.favoritapp.adapters;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.favoritapp.Favoritos;
import com.example.favoritapp.FragmentDetallesSitio;
import com.example.favoritapp.MapFragmentSitios;
import com.example.favoritapp.R;
import com.example.favoritapp.Registrar_sitio;
import com.example.favoritapp.ado.SitiosADO;
import com.example.favoritapp.clases.Mensajes;
import com.example.favoritapp.modelos.Sitios;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.ViewHolderRegistro>{

    private final ArrayList<Sitios> datos;
    private final FragmentManager f;

    public AdapterRecyclerView(ArrayList<Sitios> datos, FragmentManager fragmento) {

        this.datos = datos;
        this.f = fragmento;
    }

    @NonNull
    @Override
    public AdapterRecyclerView.ViewHolderRegistro onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, null, false);
        return new ViewHolderRegistro(vista, f);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerView.ViewHolderRegistro holder, int position) {
        holder.asignarRegistros(datos.get(position));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public class ViewHolderRegistro extends RecyclerView.ViewHolder {

        private TextView txtNombre;
        private TextView txtDescripcion;
        private TextView txtTipo;
        private String nombre;
        private String descripcion;
        private String tipo;
        private double latitud;
        private double longitud;
        private long id;

        public ViewHolderRegistro(@NonNull View itemView, @NonNull FragmentManager f) {

            super(itemView);

            txtNombre = (TextView) itemView.findViewById(R.id.recyclerviewitem_txtNombre);
            txtDescripcion = (TextView) itemView.findViewById(R.id.recyclerviewitem_txtDescripcion);
            txtTipo = (TextView) itemView.findViewById(R.id.recyclerviewitem_txtTipo);

            ImageButton btnEditar = (ImageButton) itemView.findViewById(R.id.recyclerviewitem_btnEditar);
            ImageButton btnEliminar = (ImageButton) itemView.findViewById(R.id.recyclerviewitem_btnEliminar);
            ImageButton btnMapa = (ImageButton) itemView.findViewById(R.id.recyclerviewitem_btnMapa);
            ImageButton btnDetalles = (ImageButton) itemView.findViewById(R.id.recyclerviewitem_btnDetalles);

            btnEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view.getContext(), Registrar_sitio.class);
                    i.putExtra("id", id);
                    view.getContext().startActivity(i);
                }
            });

            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder msj = new AlertDialog.Builder(view.getContext());
                    msj.setTitle("Confirmación");
                    msj.setMessage("¿Realmente desea eliminar el registro?");
                    msj.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SitiosADO dbSitio = new SitiosADO(view.getContext());
                            if(dbSitio.eliminar(id)) {
                                new Mensajes(view.getContext()).confirmSi("Registro eliminado", "Se ha eliminado el registro correctamente.", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        FirebaseDatabase bd = FirebaseDatabase.getInstance();
                                        bd.getReference().child("Sitios").child(String.valueOf(id)).removeValue().addOnCompleteListener((Activity) view.getContext(), new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                ((Favoritos) view.getContext()).recreate();
                                            }
                                        });
                                    }
                                });
                            }
                            else {
                                new Mensajes(view.getContext()).alert("Error", "Se ha producido un error al intentar eliminar el registro.");
                            }
                        }
                    });
                    msj.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    msj.create();
                    msj.show();
                }
            });

            btnMapa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MapFragmentSitios.newInstance(latitud, longitud).show(f,null);

                }
            });

            btnDetalles.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentDetallesSitio.newInstance(tipo, nombre, descripcion, latitud, longitud).show(f,null);

                }
            });
        }
        public void asignarRegistros(Sitios sit)
        {
            txtNombre.setText(sit.getNombre());
            txtDescripcion.setText(sit.getDescripcion());
            txtTipo.setText(sit.getTipo());
            id = sit.getId();
            latitud = sit.getLatitud();
            longitud = sit.getLongitud();
        }
    }
}
