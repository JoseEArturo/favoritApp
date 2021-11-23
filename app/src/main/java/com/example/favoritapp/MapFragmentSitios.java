package com.example.favoritapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.favoritapp.modelos.Sitios;
import com.example.favoritapp.viewmodels.SitiosViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragmentSitios extends Fragment {

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            SitiosViewModel usvm = ViewModelProviders.of(getActivity()).get(SitiosViewModel.class);
            usvm.getSitio().observe(getViewLifecycleOwner(), new Observer<Sitios>() {
                @Override
                public void onChanged(Sitios sitio) {
                    LatLng sydney = new LatLng(sitio.getLatitud(), sitio.getLongitud());
                    googleMap.clear();
                    googleMap.addMarker(new MarkerOptions().position(sydney).title("Posicion"));
                    googleMap.setMinZoomPreference(10);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                }
            });
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map_sitios, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}