package com.example.inventario_puebloviejo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NOMBRE = "DataBasePV.db";
    private static final String TABLE_USUARIO = "Usuario";

    private static final String TABLE_DEPARTAMENTO = "Area";
    private static final String TABLE_EQUIPO = "Equipo";
    private static final String TABLE_MANTENIMIENTO = "Mantenimiento";


    public DataBase(Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }
    public DataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_USUARIO + "("+
                "id_usuario INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre STRING NOT NULL," +
                "puesto STRING NOT NULL," +
                "correo STRING NOT NULL," +
                "password STRING NOT NULL," +
                "telefono INT NOT NULL)");

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_DEPARTAMENTO + "("+
                "id_area INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre_area STRING NOT NULL," +
                "estatus BOOLEAN NOT NULL," +
                "n_serie STRING NOT NULL," +
                "marca STRING NOT NULL," +
                "propietario STRING NOT NULL," +
                "fecha_ini DATE NOT NULL)");

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_MANTENIMIENTO + "("+
                "id_mant INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "n_serie STRING NOT NULL," +
                "fecha_llegada DATE NOT NULL," +
                "estatus BOOLEAN NOT NULL," +
                "descripcion STRING NOT NULL," +
                "fecha_entrega DATE NOT NULL)");

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_EQUIPO + "("+
                "id_equipo INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "n_serie STRING NOT NULL," +
                "tipo STRING NOT NULL," +
                "estatus BOOLEAN NOT NULL," +
                "marca STRING NOT NULL," +
                "propietario STRING NOT NULL," +
                "area STRING NOT NULL," +
                "fecha_ini DATE NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEPARTAMENTO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MANTENIMIENTO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EQUIPO);

        onCreate(db);
    }
}
