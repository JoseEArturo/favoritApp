package com.example.favoritapp.ado;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.favoritapp.clases.SqliteConectionSitios;
import com.example.favoritapp.modelos.Sitios;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class SitiosADO extends SqliteConectionSitios {

    private Context contexto;

    public SitiosADO(@Nullable Context c)
    {
        super(c);
        this.contexto = c;
    }

    public long insertar(Sitios sit)
    {
        long id = 0;

        SqliteConectionSitios dbc = new SqliteConectionSitios(this.contexto);
        SQLiteDatabase db = dbc.getWritableDatabase();
        try
        {

            ContentValues valores = new ContentValues();
            valores.put("nombre", sit.getNombre());
            valores.put("descripcion", sit.getDescripcion());
            valores.put("tipo", sit.getTipo());
            valores.put("latitud", sit.getLatitud());
            valores.put("longitud", sit.getLongitud());

            id = db.insert("sitios", null, valores);
        }
        catch (Exception ex)
        {

        }
        finally {
            db.close();
        }

        return id;
    }

    public Sitios obtenerSitio(long id)
    {
        Sitios sit = null;

        SqliteConectionSitios conexion = new SqliteConectionSitios(this.contexto);
        SQLiteDatabase db = conexion.getWritableDatabase();

        try
        {
            Cursor cregistros = db.rawQuery("select id, nombre, descripcion, tipo, latitud, longitud from sitios where id = " + String.valueOf(id), null);
            if(cregistros.getCount()>0) {
                cregistros.moveToFirst();
                sit = new Sitios();
                sit.setId(cregistros.getInt(0));
                sit.setNombre(cregistros.getString(1));
                sit.setDescripcion(cregistros.getString(2));
                sit.setTipo(cregistros.getString(3));
                sit.setLatitud(cregistros.getDouble(4));
                sit.setLongitud(cregistros.getDouble(5));
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        finally {
            db.close();
        }
        return sit;
    }

    public ArrayList<Sitios> listar()
    {
        ArrayList<Sitios> registros = new ArrayList<>();
        SqliteConectionSitios conexion = new SqliteConectionSitios(this.contexto);
        SQLiteDatabase db = conexion.getWritableDatabase();

        try {
            Cursor cregistros = db.rawQuery("select id, nombre, descripcion, tipo, latitud, longitud from sitios", null);

            if (cregistros.moveToFirst())
                do {
                    Sitios sit = new Sitios();
                    sit.setId(cregistros.getInt(0));
                    sit.setNombre(cregistros.getString(1));
                    sit.setDescripcion(cregistros.getString(2));
                    sit.setTipo(cregistros.getString(3));
                    sit.setLatitud(cregistros.getDouble(4));
                    sit.setLongitud(cregistros.getDouble(5));

                    registros.add(sit);
                } while (cregistros.moveToNext());
        }
        catch (Exception ex)
        {

        }
        finally {
            db.close();
        }

        return registros;
    }

    public boolean editar(Sitios sit)
    {
        boolean editado = false;

        SqliteConectionSitios conexion = new SqliteConectionSitios(this.contexto);
        SQLiteDatabase db = conexion.getWritableDatabase();
        try
        {

            db.execSQL("UPDATE sitios" +
                    "   SET nombre = '" + sit.getNombre() + "'," +
                    "       descripcion = '" + sit.getDescripcion() + "'," +
                    "       tipo = '" + sit.getTipo() + "'," +
                    "       latitud = '" + sit.getLatitud() + "'," +
                    "       longitud = '" + sit.getLongitud() + "'" +
                    " WHERE id = '" + String.valueOf(sit.getId()) + "'");
            editado=true;

        }
        catch (Exception ex)
        {

        }
        finally {
            db.close();
        }

        return editado;
    }

    public boolean eliminar(long id)
    {
        boolean eliminado = false;

        SqliteConectionSitios conexion = new SqliteConectionSitios(this.contexto);
        SQLiteDatabase db = conexion.getWritableDatabase();

        try
        {
            db.execSQL("DELETE FROM sitios " +
                    "      WHERE id = " + String.valueOf(id));
            eliminado=true;
        }
        catch (Exception ex)
        {

        }
        finally {
            db.close();
        }

        return eliminado;
    }

}
