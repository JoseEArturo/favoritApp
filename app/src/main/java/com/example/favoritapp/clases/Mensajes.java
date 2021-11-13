package com.example.favoritapp.clases;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class Mensajes {
    private Context contexto;

    public Mensajes(Context contexto) {
        this.contexto = contexto;
    }

    public void alert(String titulo, String cuerpo)
    {
        AlertDialog.Builder msj = new AlertDialog.Builder(this.contexto);
        msj.setTitle(titulo);
        msj.setMessage(cuerpo);
        msj.create();
        msj.show();
    }

    public void confirm(String titulo, String cuerpo, DialogInterface.OnClickListener opcionSi, DialogInterface.OnClickListener opcionNo)
    {
        AlertDialog.Builder msj = new AlertDialog.Builder(this.contexto);
        msj.setTitle(titulo);
        msj.setMessage(cuerpo);
        msj.setPositiveButton("Aceptar", opcionSi);
        msj.setNegativeButton("Cancelar", opcionNo);
        msj.create();
        msj.show();
    }
}
