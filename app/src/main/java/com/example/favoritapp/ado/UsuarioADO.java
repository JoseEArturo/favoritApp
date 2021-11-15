package com.example.favoritapp.ado;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.favoritapp.clases.SqliteConex;
import com.example.favoritapp.modelos.Usuario;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class UsuarioADO extends SqliteConex {

    private Context contexto;

    public UsuarioADO(@Nullable Context c)
    {
        super(c);
        this.contexto = c;
    }

    public long insertar(Usuario us)
    {
        long id = 0;

        try
        {
            SqliteConex dbc = new SqliteConex(this.contexto);
            SQLiteDatabase db = dbc.getWritableDatabase();

            ContentValues valores = new ContentValues();
            valores.put("nombres", us.getNombres());
            valores.put("apellidos", us.getApellidos());
            valores.put("email", us.getEmail());
            valores.put("clave", us.getClave());

            id = db.insert("usuarios", null, valores);
        }
        catch (Exception ex)
        {

        }

        return id;
    }

    public ArrayList<Usuario> listar()
    {
        SqliteConex conexion = new SqliteConex(this.contexto);
        SQLiteDatabase db = conexion.getWritableDatabase();

        ArrayList<Usuario> registros = new ArrayList<>();
        Cursor cregistros = db.rawQuery("select id, nombres, apellidos, email, clave from usuarios", null);

        if(cregistros.moveToFirst())
            do {
                Usuario us = new Usuario();
                us.setId(cregistros.getInt(0));
                us.setNombres(cregistros.getString(1));
                us.setApellidos(cregistros.getString(2));
                us.setEmail(cregistros.getString(3));
                us.setClave(cregistros.getString(4));

                registros.add(us);
            }while (cregistros.moveToNext());

        return registros;
    }

    public boolean validarUsuario(Usuario us)
    {
        boolean validado = false;

        String emailPrueba = "usuario@gmail.com";
        String clavePrueba = "prueba";

        if(us.getEmail().equals(emailPrueba) && us.getClave().equals(clavePrueba))
            validado=true;

        return validado;
    }

}
