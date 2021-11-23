package com.example.favoritapp.clases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqliteConectionSitios extends SQLiteOpenHelper {

    private static final String nombre_bd = "sitios.db";

    public SqliteConectionSitios(@Nullable Context c)
    {
        super(c, nombre_bd, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE sitios (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nombre VARCHAR (100) NOT NULL, descripcion TEXT, tipo VARCHAR (45) NOT NULL, latitud DECIMAL (4, 8) NOT NULL, longitud DECIMAL (4, 8) NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE sitios");
    }
}
