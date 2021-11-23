package com.example.favoritapp.viewmodels;

import com.example.favoritapp.modelos.Sitios;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SitiosViewModel extends ViewModel{

    private MutableLiveData<Sitios> sitio = new MutableLiveData<>();

    public MutableLiveData<Sitios> getSitio() {

        return sitio;
    }

    public void setSitio(Sitios sitio) {

        this.sitio.setValue(sitio);
    }

}
