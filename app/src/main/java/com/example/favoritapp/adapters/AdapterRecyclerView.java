package com.example.favoritapp.adapters;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.favoritapp.Favoritos;
import com.example.favoritapp.R;
import com.example.favoritapp.Registrar_sitio;
import com.example.favoritapp.ado.SitiosADO;
import com.example.favoritapp.clases.Mensajes;
import com.example.favoritapp.modelos.Sitios;

import java.util.ArrayList;

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.ViewHolderRegistro>{

    private final ArrayList<Sitios> datos;

    public AdapterRecyclerView(ArrayList<Sitios> datos) {
        this.datos = datos;
    }

    @NonNull
    @Override
    public AdapterRecyclerView.ViewHolderRegistro onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, null, false);
        return new ViewHolderRegistro(vista);
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
        private long id;

        public ViewHolderRegistro(@NonNull View itemView) {

            super(itemView);

            txtNombre = (TextView) itemView.findViewById(R.id.recyclerviewitem_txtNombre);
            txtDescripcion = (TextView) itemView.findViewById(R.id.recyclerviewitem_txtDescripcion);
            ImageButton btnEditar = (ImageButton) itemView.findViewById(R.id.recyclerviewitem_btnEditar);
            ImageButton btnEliminar = (ImageButton) itemView.findViewById(R.id.recyclerviewitem_btnEliminar);

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
                            SitiosADO dbUsuario = new SitiosADO(view.getContext());
                            if(dbUsuario.eliminar(id))
                                new Mensajes(view.getContext()).alert("Registro eliminado", "Se ha eliminado el registro correctamente.");
                            else
                                new Mensajes(view.getContext()).alert("Error", "Se ha producido un error al intentar eliminar el registro.");

                            ((Favoritos) view.getContext()).recreate();
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
        }
        public void asignarRegistros(Sitios sit)
        {
            txtNombre.setText(sit.getNombre());
            txtDescripcion.setText(sit.getDescripcion());
            id = sit.getId();
        }
    }
}
