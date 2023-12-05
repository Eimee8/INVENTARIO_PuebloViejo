package com.example.inventario_puebloviejo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


import androidx.annotation.Nullable;

public class DataBase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 5;

    private static final String DATABASE_NOMBRE = "DataBasePV.db";
    private static final String TABLE_USUARIO = "Usuario";

    private static final String TABLE_DEPARTAMENTO = "Area";
    private static final String TABLE_EQUIPO = "Equipo";
    private static final String TABLE_MANTENIMIENTO = "Mantenimiento";


    public DataBase(Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }
    //public DataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
    //    super(context, name, factory, version);
  //  }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_USUARIO + "("+
                "id_usuario INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL," +
                "puesto TEXT NOT NULL," +
                "correo TEXT NOT NULL," +
                "password TEXT NOT NULL," +
                "telefono INT NOT NULL)");

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_DEPARTAMENTO + "("+
                "id_area INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre_area TEXT NOT NULL," +
                "estatus BOOLEAN NOT NULL," +
                "n_serie TEXT NOT NULL," +
                "marca TEXT NOT NULL," +
                "propietario TEXT NOT NULL," +
                "fecha_ini DATE NOT NULL)");

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_MANTENIMIENTO + "("+
                "id_mant INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "n_serie TEXT NOT NULL," +
                "fecha_llegada DATE NOT NULL," +
                "estatus BOOLEAN NOT NULL," +
                "descripcion TEXT NOT NULL," +
                "fecha_entrega DATE NOT NULL)");

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_EQUIPO + "("+
                "id_equipo INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "n_serie TEXT NOT NULL," +
                "tipo TEXT NOT NULL," +
                "estatus TEXT NOT NULL," +
                "marca TEXT NOT NULL," +
                "propietario TEXT NOT NULL," +
                "area TEXT NOT NULL," +
                "fecha_ini TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEPARTAMENTO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MANTENIMIENTO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EQUIPO);

        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean insertEquipo(String n_serie, String tipo, String estatus,
                                String marca, String propietario, String area, String fecha_ini) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("n_serie", n_serie);
        contentValues.put("tipo", tipo);
        contentValues.put("estatus", estatus);
        contentValues.put("marca", marca);
        contentValues.put("propietario", propietario);
        contentValues.put("area", area);
        contentValues.put("fecha_ini", fecha_ini);

        long result = sqLiteDatabase.insert(TABLE_EQUIPO, null, contentValues);

        // Devuelve true si la inserción fue exitosa, false si hubo un error
        return result != -1;
    }

    public boolean equipoExistente(String n_serie){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select * from Equipo Where n_serie=?", new String[]{n_serie.trim()                                                                                                                                                                                                                                                                                                                                                });

        boolean existe = (cursor.getCount() > 0);

        cursor.close();
        database.close();

        return existe;
    }

    public ArrayList<Date> mostrarEquipos() {
        SQLiteDatabase database = this.getWritableDatabase();
        ArrayList<Date> listEquipo = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = database.rawQuery("SELECT * FROM " + TABLE_EQUIPO, null);

            while (cursor.moveToNext()) {
                Date date = new Date();
                date.setEstatus(cursor.getString(3));
                date.setMarca(cursor.getString(4));
                date.setN_serie(cursor.getString(1));
                date.setNombre_area(cursor.getString(6));
                date.setFecha_ini(cursor.getString(7));
                date.setPropietario(cursor.getString(5));

                listEquipo.add(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listEquipo;
    }

    public ArrayList<Date> mostrarArea() {
        ArrayList<Date> listdatos = new ArrayList<>();
        SQLiteDatabase date = this.getWritableDatabase();

        try (Cursor cursor = date.rawQuery("SELECT * FROM " + TABLE_EQUIPO + " WHERE area LIKE ?", new String[]{"%egresos%"})) {
            while (cursor != null && cursor.moveToNext()) {
                Date datos = new Date();
                datos.setEstatus(cursor.getString(3));
                datos.setMarca(cursor.getString(4));
                datos.setN_serie(cursor.getString(1));
                datos.setNombre_area(cursor.getString(6));
                datos.setFecha_ini(cursor.getString(7));
                datos.setPropietario(cursor.getString(5));

                listdatos.add(datos);
            }
        } catch (Exception e) {
            // Manejar cualquier excepción que pueda ocurrir al trabajar con la base de datos
            e.printStackTrace();
        }

        return listdatos;
    }

}
