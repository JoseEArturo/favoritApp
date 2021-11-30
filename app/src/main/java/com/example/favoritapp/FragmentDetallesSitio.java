package com.example.favoritapp;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.favoritapp.modelos.Sitios;
import com.example.favoritapp.viewmodels.SitiosViewModel;


public class FragmentDetallesSitio extends DialogFragment {

    private String tipo;
    private String nombre;
    private String descripcion;
    private double lat;
    private double lon;

    public FragmentDetallesSitio() {
    }

    public static FragmentDetallesSitio newInstance(String tip, String nom, String des, double la, double lo) {
        FragmentDetallesSitio fragmento = new FragmentDetallesSitio();
        Bundle args = new Bundle();
        args.putString("tipo", tip);
        args.putString("nombre", nom);
        args.putString("descripcion", des);
        args.putDouble("latitud", la);
        args.putDouble("longitud", lo);
        fragmento.setArguments(args);
        return fragmento;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tipo = getArguments().getString("tipo");
            nombre = getArguments().getString("nombre");
            descripcion = getArguments().getString("descripcion");
            lat = getArguments().getDouble("latitud");
            lon = getArguments().getDouble("longitud");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View vista = inflater.inflate(R.layout.fragment_detalles_sitio, container, false);

        EditText txtTipo = (EditText) vista.findViewById(R.id.fragmento_detalles_txtTipo);
        EditText txtNombre = (EditText) vista.findViewById(R.id.fragmento_detalles_txtNombre);
        EditText txtDescripcion = (EditText) vista.findViewById(R.id.fragmento_detalles_txtDescripcion);
        EditText txtLatitud = (EditText) vista.findViewById(R.id.fragmento_detalles_txtLatitud);
        EditText txtLongitud = (EditText) vista.findViewById(R.id.fragmento_detalles_txtLongitud);

        SitiosViewModel usuariovm = ViewModelProviders.of(getActivity()).get(SitiosViewModel.class);
        usuariovm.getSitio().observe(getViewLifecycleOwner(), new Observer<Sitios>() {
            @Override
            public void onChanged(Sitios sit) {
                txtTipo.setText(sit.getTipo());
                txtNombre.setText(sit.getNombre());
                txtDescripcion.setText(sit.getDescripcion());
                txtLatitud.setText(String.valueOf(sit.getLatitud()));
                txtLongitud.setText(String.valueOf(sit.getLongitud()));
            }
        });

        return vista;
    }
}