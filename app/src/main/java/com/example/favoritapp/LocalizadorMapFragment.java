package com.example.favoritapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocalizadorMapFragment extends Fragment {

    private double lat;
    private double lon;

    public LocalizadorMapFragment(){

    }

    public static LocalizadorMapFragment newInstance(double la, double lo){
        LocalizadorMapFragment fragmento = new LocalizadorMapFragment();
        Bundle args = new Bundle();
        args.putDouble("latitud", la);
        args.putDouble("longitud", lo);
        fragmento.setArguments(args);
        return fragmento;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            lat = getArguments().getDouble("latitud");
            lon = getArguments().getDouble("longitud");
        }
    }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {


        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng bogota = new LatLng(4.732008, -74.068576);
            googleMap.addMarker(new MarkerOptions().position(bogota).title("Marcador de Colombia"));
            googleMap.setMinZoomPreference(10);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(bogota));
            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(@NonNull LatLng latLng) {
                    ((Mapa) getActivity()).actualizarCoordenada(latLng.latitude, latLng.longitude);
                    ((Mapa) getActivity()).onBackPressed();
                }
            });
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_localizador_map, container, false);
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