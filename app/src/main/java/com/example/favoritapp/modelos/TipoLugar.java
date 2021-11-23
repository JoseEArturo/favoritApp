package com.example.favoritapp.modelos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.example.favoritapp.R;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import androidx.core.content.ContextCompat;

public enum TipoLugar {
    tienda("Tienda", R.drawable.ic_restaurante), mecanico("Mecanico", R.drawable.ic_mecanico), hotel("Hotel", R.drawable.ic_hotel), iglesia("Iglesia", R.drawable.ic_iglesia), control("Punto de Control", R.drawable.ic_punto_control), posactual("Su posición actual", R.drawable.ic_posicion);

    private String valor;
    private int imagen;

    TipoLugar(String valor, int imagen)
    {
        this.valor = valor;
        this.imagen = imagen;
    }

    public BitmapDescriptor getImagen(Context contexto)
    {
        return obtenerBitmap(contexto, this.imagen);
    }

    private BitmapDescriptor obtenerBitmap(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString()
    {
        return this.getValor();
    }
}
