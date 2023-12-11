package com.example.inventario_puebloviejo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import androidx.annotation.Nullable;

public class DataBase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 10;

    private static final String DATABASE_NOMBRE = "DataBasePV.db";
    private static final String TABLE_USUARIO = "Usuario";

    private static final String TABLE_DEPARTAMENTO = "Area";
    private static final String TABLE_EQUIPO = "Equipo";
    private static final String TABLE_MANTENIMIENTO = "Mantenimiento";


    public DataBase(Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_USUARIO + "("+
                "id_usuario INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL," +
                "puesto TEXT NOT NULL," +
                "correo TEXT NOT NULL," +
                "password TEXT NOT NULL," +
                "telefono TEXT NOT NULL)");

        //insertarUsuario(db,"123", "Ingeniero", "correo@ejemplo.com", "123", "8331235649");

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_DEPARTAMENTO + "("+
                "id_area INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre_area TEXT NOT NULL," +
                "estatus TEXT NOT NULL," +
                "n_serie TEXT NOT NULL," +
                "marca TEXT NOT NULL," +
                "propietario TEXT NOT NULL," +
                "fecha_ini TEXT NOT NULL)");

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_MANTENIMIENTO + "("+
                "id_mant INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "n_serie TEXT NOT NULL," +
                "tipo TEXT NOT NULL," +
                "estatus TEXT NOT NULL," +
                "descripcion TEXT NOT NULL," +
                "fecha_llegada TEXT NOT NULL," +
                "fecha_entrega TEXT NOT NULL)");

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

        /*insertarUsuario(db, "123", "Ingeniero", "correo@ejemplo.com", "123", "8331235649");
        long idUsuarioPrueba = insertarUsuario(db, "UsuarioPrueba", "Ingeniero", "correo_prueba@ejemplo.com", "contraseña123", "123456789");

        if (idUsuarioPrueba != -1) {
            Log.d("DatabaseHelper", "Usuario de prueba insertado correctamente con ID: " + idUsuarioPrueba);
        } else {
            Log.e("DatabaseHelper", "Error al insertar usuario de prueba");
        }*/
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public long insertarUsuario(SQLiteDatabase db, String nombre, String puesto, String correo, String password, String telefono) {
        ContentValues values = new ContentValues();

        values.put("nombre", nombre);
        values.put("Ingeniero", puesto);
        values.put("correo", correo);
        values.put("password", password);
        values.put("telefono", telefono);

        long resultado = db.insert(TABLE_USUARIO, null, values);

        db.close();

        return resultado;
    }

    public boolean usuarioError(String nombre) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM Usuario WHERE nombre=?", new String[]{nombre});

        boolean usuarioError = (cursor.getCount() == 0);

        cursor.close();
        database.close();

        return usuarioError;

    }

    public boolean contraseñaError(String password){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select * from Usuario Where password=?", new String[]{password});

        boolean contraseñaIncorrecta = (cursor.getCount() == 0);

        cursor.close();
        database.close();

        return contraseñaIncorrecta;

    }

    public int checkpass(String password, String nombre) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select * from Usuario Where nombre=? and password = ?", new String[]{nombre.trim(), password.trim()});

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            return cursor.getInt(0);
        } else {
            return -1;
        }
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

        return result != -1;
    }

    public boolean insertEquipoAgenda(String n_serie, String tipo, String estatus,
                                      String descripcion, String fecha_llegada, String fecha_entrega) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("n_serie", n_serie);
        contentValues.put("tipo", tipo);
        contentValues.put("estatus", estatus);
        contentValues.put("descripcion", descripcion);
        contentValues.put("fecha_llegada", fecha_llegada);
        contentValues.put("fecha_entrega", fecha_entrega);

        long result = sqLiteDatabase.insert(TABLE_MANTENIMIENTO, null, contentValues);

        if (result == -1) {
            Log.e("Database", "Error al insertar equipo en la base de datos");
        }

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

    public boolean equipoAreaExistente(String n_serie){
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
                date.setTipo(cursor.getString(2));
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

    public ArrayList<Date> mostrarEgresos() {
        ArrayList<Date> listdatos = new ArrayList<>();
        SQLiteDatabase date = this.getWritableDatabase();

        try (Cursor cursor = date.rawQuery("SELECT * FROM " + TABLE_EQUIPO + " WHERE area LIKE ?", new String[]{"%egresos%"})) {
            while (cursor != null && cursor.moveToNext()) {
                Date datos = new Date();
                datos.setEstatus(cursor.getString(3));
                datos.setTipo(cursor.getString(2));
                datos.setMarca(cursor.getString(4));
                datos.setN_serie(cursor.getString(1));
                datos.setNombre_area(cursor.getString(6));
                datos.setFecha_ini(cursor.getString(7));
                datos.setPropietario(cursor.getString(5));

                listdatos.add(datos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listdatos;
    }

    public ArrayList<Date> mostrarIngresos() {
        ArrayList<Date> listdatos = new ArrayList<>();
        SQLiteDatabase date = this.getWritableDatabase();

        try (Cursor cursor = date.rawQuery("SELECT * FROM " + TABLE_EQUIPO + " WHERE area LIKE ?", new String[]{"%ingresos%"})) {
            while (cursor != null && cursor.moveToNext()) {
                Date datos = new Date();
                datos.setEstatus(cursor.getString(3));
                datos.setTipo(cursor.getString(2));
                datos.setMarca(cursor.getString(4));
                datos.setN_serie(cursor.getString(1));
                datos.setNombre_area(cursor.getString(6));
                datos.setFecha_ini(cursor.getString(7));
                datos.setPropietario(cursor.getString(5));

                listdatos.add(datos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listdatos;
    }

    public ArrayList<Date> mostrarTesoreria() {
        ArrayList<Date> listdatos = new ArrayList<>();
        SQLiteDatabase date = this.getWritableDatabase();

        try (Cursor cursor = date.rawQuery("SELECT * FROM " + TABLE_EQUIPO + " WHERE area LIKE ?", new String[]{"%tesoreria%"})) {
            while (cursor != null && cursor.moveToNext()) {
                Date datos = new Date();
                datos.setEstatus(cursor.getString(3));
                datos.setTipo(cursor.getString(2));
                datos.setMarca(cursor.getString(4));
                datos.setN_serie(cursor.getString(1));
                datos.setNombre_area(cursor.getString(6));
                datos.setFecha_ini(cursor.getString(7));
                datos.setPropietario(cursor.getString(5));

                listdatos.add(datos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listdatos;
    }

    public ArrayList<Date> mostrarCajas() {
        ArrayList<Date> listdatos = new ArrayList<>();
        SQLiteDatabase date = this.getWritableDatabase();

        try (Cursor cursor = date.rawQuery("SELECT * FROM " + TABLE_EQUIPO + " WHERE area LIKE ?", new String[]{"%cajas%"})) {
            while (cursor != null && cursor.moveToNext()) {
                Date datos = new Date();
                datos.setEstatus(cursor.getString(3));
                datos.setTipo(cursor.getString(2));
                datos.setMarca(cursor.getString(4));
                datos.setN_serie(cursor.getString(1));
                datos.setNombre_area(cursor.getString(6));
                datos.setFecha_ini(cursor.getString(7));
                datos.setPropietario(cursor.getString(5));

                listdatos.add(datos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listdatos;
    }

    public ArrayList<Date> mostrarCatastro() {
        ArrayList<Date> listdatos = new ArrayList<>();
        SQLiteDatabase date = this.getWritableDatabase();

        try (Cursor cursor = date.rawQuery("SELECT * FROM " + TABLE_EQUIPO + " WHERE area LIKE ?", new String[]{"%catastro%"})) {
            while (cursor != null && cursor.moveToNext()) {
                Date datos = new Date();
                datos.setEstatus(cursor.getString(3));
                datos.setTipo(cursor.getString(2));
                datos.setMarca(cursor.getString(4));
                datos.setN_serie(cursor.getString(1));
                datos.setNombre_area(cursor.getString(6));
                datos.setFecha_ini(cursor.getString(7));
                datos.setPropietario(cursor.getString(5));

                listdatos.add(datos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listdatos;
    }

    public ArrayList<Date> mostrarContraloria() {
        ArrayList<Date> listdatos = new ArrayList<>();
        SQLiteDatabase date = this.getWritableDatabase();

        try (Cursor cursor = date.rawQuery("SELECT * FROM " + TABLE_EQUIPO + " WHERE area LIKE ?", new String[]{"%contraloria%"})) {
            while (cursor != null && cursor.moveToNext()) {
                Date datos = new Date();
                datos.setEstatus(cursor.getString(3));
                datos.setTipo(cursor.getString(2));
                datos.setMarca(cursor.getString(4));
                datos.setN_serie(cursor.getString(1));
                datos.setNombre_area(cursor.getString(6));
                datos.setFecha_ini(cursor.getString(7));
                datos.setPropietario(cursor.getString(5));

                listdatos.add(datos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listdatos;
    }

    public ArrayList<Date> mostrarRegistroCivil() {
        ArrayList<Date> listdatos = new ArrayList<>();
        SQLiteDatabase date = this.getWritableDatabase();

        try (Cursor cursor = date.rawQuery("SELECT * FROM " + TABLE_EQUIPO + " WHERE area LIKE ?", new String[]{"%registro civil%"})) {
            while (cursor != null && cursor.moveToNext()) {
                Date datos = new Date();
                datos.setEstatus(cursor.getString(3));
                datos.setTipo(cursor.getString(2));
                datos.setMarca(cursor.getString(4));
                datos.setN_serie(cursor.getString(1));
                datos.setNombre_area(cursor.getString(6));
                datos.setFecha_ini(cursor.getString(7));
                datos.setPropietario(cursor.getString(5));

                listdatos.add(datos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listdatos;
    }

    public ArrayList<Date> mostrarSecretarias() {
        ArrayList<Date> listdatos = new ArrayList<>();
        SQLiteDatabase date = this.getWritableDatabase();

        try (Cursor cursor = date.rawQuery("SELECT * FROM " + TABLE_EQUIPO + " WHERE area LIKE ?", new String[]{"%secretarias%"})) {
            while (cursor != null && cursor.moveToNext()) {
                Date datos = new Date();
                datos.setEstatus(cursor.getString(3));
                datos.setTipo(cursor.getString(2));
                datos.setMarca(cursor.getString(4));
                datos.setN_serie(cursor.getString(1));
                datos.setNombre_area(cursor.getString(6));
                datos.setFecha_ini(cursor.getString(7));
                datos.setPropietario(cursor.getString(5));

                listdatos.add(datos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listdatos;
    }

    public ArrayList<Date> mostrarRegidores() {
        ArrayList<Date> listdatos = new ArrayList<>();
        SQLiteDatabase date = this.getWritableDatabase();

        try (Cursor cursor = date.rawQuery("SELECT * FROM " + TABLE_EQUIPO + " WHERE area LIKE ?", new String[]{"%regidores%"})) {
            while (cursor != null && cursor.moveToNext()) {
                Date datos = new Date();
                datos.setEstatus(cursor.getString(3));
                datos.setTipo(cursor.getString(2));
                datos.setMarca(cursor.getString(4));
                datos.setN_serie(cursor.getString(1));
                datos.setNombre_area(cursor.getString(6));
                datos.setFecha_ini(cursor.getString(7));
                datos.setPropietario(cursor.getString(5));

                listdatos.add(datos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listdatos;
    }

    public ArrayList<Date> mostrarTecnologia() {
        ArrayList<Date> listdatos = new ArrayList<>();
        SQLiteDatabase dato = this.getWritableDatabase();

        try (Cursor cursor = dato.rawQuery("SELECT * FROM " + TABLE_EQUIPO + " WHERE area LIKE ?", new String[]{"%area de tecnología%"})) {
            while (cursor != null && cursor.moveToNext()) {
                Date datos = new Date();
                datos.setEstatus(cursor.getString(3));
                datos.setTipo(cursor.getString(2));
                datos.setMarca(cursor.getString(4));
                datos.setN_serie(cursor.getString(1));
                datos.setNombre_area(cursor.getString(6));
                datos.setFecha_ini(cursor.getString(7));
                datos.setPropietario(cursor.getString(5));

                listdatos.add(datos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listdatos;
    }

    public ArrayList<Date> mostrarObraPublica() {
        ArrayList<Date> listdatos = new ArrayList<>();
        SQLiteDatabase date = this.getWritableDatabase();

        try (Cursor cursor = date.rawQuery("SELECT * FROM " + TABLE_EQUIPO + " WHERE area LIKE ?", new String[]{"%obra publica%"})) {
            while (cursor != null && cursor.moveToNext()) {
                Date datos = new Date();
                datos.setEstatus(cursor.getString(3));
                datos.setTipo(cursor.getString(2));
                datos.setMarca(cursor.getString(4));
                datos.setN_serie(cursor.getString(1));
                datos.setNombre_area(cursor.getString(6));
                datos.setFecha_ini(cursor.getString(7));
                datos.setPropietario(cursor.getString(5));

                listdatos.add(datos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listdatos;
    }

    public ArrayList<Date> mostrarRecursosHumanos() {
        ArrayList<Date> listdatos = new ArrayList<>();
        SQLiteDatabase date = this.getWritableDatabase();

        try (Cursor cursor = date.rawQuery("SELECT * FROM " + TABLE_EQUIPO + " WHERE area LIKE ?", new String[]{"%recursos humanos%"})) {
            while (cursor != null && cursor.moveToNext()) {
                Date datos = new Date();
                datos.setEstatus(cursor.getString(3));
                datos.setTipo(cursor.getString(2));
                datos.setMarca(cursor.getString(4));
                datos.setN_serie(cursor.getString(1));
                datos.setNombre_area(cursor.getString(6));
                datos.setFecha_ini(cursor.getString(7));
                datos.setPropietario(cursor.getString(5));

                listdatos.add(datos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listdatos;
    }

    public ArrayList<Date> mostrarReligion() {
        ArrayList<Date> listdatos = new ArrayList<>();
        SQLiteDatabase date = this.getWritableDatabase();

        try (Cursor cursor = date.rawQuery("SELECT * FROM " + TABLE_EQUIPO + " WHERE area LIKE ?", new String[]{"%religion%"})) {
            while (cursor != null && cursor.moveToNext()) {
                Date datos = new Date();
                datos.setEstatus(cursor.getString(3));
                datos.setTipo(cursor.getString(2));
                datos.setMarca(cursor.getString(4));
                datos.setN_serie(cursor.getString(1));
                datos.setNombre_area(cursor.getString(6));
                datos.setFecha_ini(cursor.getString(7));
                datos.setPropietario(cursor.getString(5));

                listdatos.add(datos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listdatos;
    }

    public ArrayList<Date> mostrarInstMujer() {
        ArrayList<Date> listdatos = new ArrayList<>();
        SQLiteDatabase date = this.getWritableDatabase();

        try (Cursor cursor = date.rawQuery("SELECT * FROM " + TABLE_EQUIPO + " WHERE area LIKE ?", new String[]{"%instituto de la mujer%"})) {
            while (cursor != null && cursor.moveToNext()) {
                Date datos = new Date();
                datos.setEstatus(cursor.getString(3));
                datos.setTipo(cursor.getString(2));
                datos.setMarca(cursor.getString(4));
                datos.setN_serie(cursor.getString(1));
                datos.setNombre_area(cursor.getString(6));
                datos.setFecha_ini(cursor.getString(7));
                datos.setPropietario(cursor.getString(5));

                listdatos.add(datos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listdatos;
    }

    public ArrayList<Date> mostrarComandancia() {
        ArrayList<Date> listdatos = new ArrayList<>();
        SQLiteDatabase date = this.getWritableDatabase();

        try (Cursor cursor = date.rawQuery("SELECT * FROM " + TABLE_EQUIPO + " WHERE area LIKE ?", new String[]{"%comandancia%"})) {
            while (cursor != null && cursor.moveToNext()) {
                Date datos = new Date();
                datos.setEstatus(cursor.getString(3));
                datos.setTipo(cursor.getString(2));
                datos.setMarca(cursor.getString(4));
                datos.setN_serie(cursor.getString(1));
                datos.setNombre_area(cursor.getString(6));
                datos.setFecha_ini(cursor.getString(7));
                datos.setPropietario(cursor.getString(5));

                listdatos.add(datos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listdatos;
    }

    public ArrayList<Date> mostrarComercio() {
        ArrayList<Date> listdatos = new ArrayList<>();
        SQLiteDatabase date = this.getWritableDatabase();

        try (Cursor cursor = date.rawQuery("SELECT * FROM " + TABLE_EQUIPO + " WHERE area LIKE ?", new String[]{"%comercio%"})) {
            while (cursor != null && cursor.moveToNext()) {
                Date datos = new Date();
                datos.setEstatus(cursor.getString(3));
                datos.setTipo(cursor.getString(2));
                datos.setMarca(cursor.getString(4));
                datos.setN_serie(cursor.getString(1));
                datos.setNombre_area(cursor.getString(6));
                datos.setFecha_ini(cursor.getString(7));
                datos.setPropietario(cursor.getString(5));

                listdatos.add(datos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listdatos;
    }

    public ArrayList<Date> mostrarAlcoholes() {
        ArrayList<Date> listdatos = new ArrayList<>();
        SQLiteDatabase date = this.getWritableDatabase();

        try (Cursor cursor = date.rawQuery("SELECT * FROM " + TABLE_EQUIPO + " WHERE area LIKE ?", new String[]{"%alcoholes%"})) {
            while (cursor != null && cursor.moveToNext()) {
                Date datos = new Date();
                datos.setEstatus(cursor.getString(3));
                datos.setTipo(cursor.getString(2));
                datos.setMarca(cursor.getString(4));
                datos.setN_serie(cursor.getString(1));
                datos.setNombre_area(cursor.getString(6));
                datos.setFecha_ini(cursor.getString(7));
                datos.setPropietario(cursor.getString(5));

                listdatos.add(datos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listdatos;
    }

    public ArrayList<Date> mostrarPanteones() {
        ArrayList<Date> listdatos = new ArrayList<>();
        SQLiteDatabase date = this.getWritableDatabase();

        try (Cursor cursor = date.rawQuery("SELECT * FROM " + TABLE_EQUIPO + " WHERE area LIKE ?", new String[]{"%panteones%"})) {
            while (cursor != null && cursor.moveToNext()) {
                Date datos = new Date();
                datos.setEstatus(cursor.getString(3));
                datos.setTipo(cursor.getString(2));
                datos.setMarca(cursor.getString(4));
                datos.setN_serie(cursor.getString(1));
                datos.setNombre_area(cursor.getString(6));
                datos.setFecha_ini(cursor.getString(7));
                datos.setPropietario(cursor.getString(5));

                listdatos.add(datos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listdatos;
    }

    public ArrayList<Date> mostrarTransparencia() {
        ArrayList<Date> listdatos = new ArrayList<>();
        SQLiteDatabase date = this.getWritableDatabase();

        try (Cursor cursor = date.rawQuery("SELECT * FROM " + TABLE_EQUIPO + " WHERE area LIKE ?", new String[]{"%transparencia%"})) {
            while (cursor != null && cursor.moveToNext()) {
                Date datos = new Date();
                datos.setEstatus(cursor.getString(3));
                datos.setTipo(cursor.getString(2));
                datos.setMarca(cursor.getString(4));
                datos.setN_serie(cursor.getString(1));
                datos.setNombre_area(cursor.getString(6));
                datos.setFecha_ini(cursor.getString(7));
                datos.setPropietario(cursor.getString(5));

                listdatos.add(datos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listdatos;
    }

    public ArrayList<Date> mostrarDrlloSocial() {
        ArrayList<Date> listdatos = new ArrayList<>();
        SQLiteDatabase date = this.getWritableDatabase();

        try (Cursor cursor = date.rawQuery("SELECT * FROM " + TABLE_EQUIPO + " WHERE area LIKE ?", new String[]{"%desarrollo social%"})) {
            while (cursor != null && cursor.moveToNext()) {
                Date datos = new Date();
                datos.setEstatus(cursor.getString(3));
                datos.setTipo(cursor.getString(2));
                datos.setMarca(cursor.getString(4));
                datos.setN_serie(cursor.getString(1));
                datos.setNombre_area(cursor.getString(6));
                datos.setFecha_ini(cursor.getString(7));
                datos.setPropietario(cursor.getString(5));

                listdatos.add(datos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listdatos;
    }

    public ArrayList<Date> mostrarInformatica() {
        ArrayList<Date> listdatos = new ArrayList<>();
        SQLiteDatabase date = this.getWritableDatabase();

        try (Cursor cursor = date.rawQuery("SELECT * FROM " + TABLE_EQUIPO + " WHERE area LIKE ?", new String[]{"%informatica%"})) {
            while (cursor != null && cursor.moveToNext()) {
                Date datos = new Date();
                datos.setEstatus(cursor.getString(3));
                datos.setTipo(cursor.getString(2));
                datos.setMarca(cursor.getString(4));
                datos.setN_serie(cursor.getString(1));
                datos.setNombre_area(cursor.getString(6));
                datos.setFecha_ini(cursor.getString(7));
                datos.setPropietario(cursor.getString(5));

                listdatos.add(datos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listdatos;
    }

    public ArrayList<Date> mostrarTrabajoSocial() {
        ArrayList<Date> listdatos = new ArrayList<>();
        SQLiteDatabase date = this.getWritableDatabase();

        try (Cursor cursor = date.rawQuery("SELECT * FROM " + TABLE_EQUIPO + " WHERE area LIKE ?", new String[]{"%trabajo social%"})) {
            while (cursor != null && cursor.moveToNext()) {
                Date datos = new Date();
                datos.setEstatus(cursor.getString(3));
                datos.setTipo(cursor.getString(2));
                datos.setMarca(cursor.getString(4));
                datos.setN_serie(cursor.getString(1));
                datos.setNombre_area(cursor.getString(6));
                datos.setFecha_ini(cursor.getString(7));
                datos.setPropietario(cursor.getString(5));

                listdatos.add(datos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listdatos;
    }

    public ArrayList<Date> mostrarEquiposPendientes() {
        SQLiteDatabase database = this.getWritableDatabase();
        ArrayList<Date> list = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = database.rawQuery("SELECT * FROM " + TABLE_MANTENIMIENTO, null);

            while (cursor.moveToNext()) {
                Date date = new Date();
                date.setN_serie(cursor.getString(1));
                date.setTipo(cursor.getString(2));
                date.setEstatus(cursor.getString(3));
                date.setDescripcion(cursor.getString(4));
                date.setFecha_llegada(cursor.getString(5));
                date.setFecha_entrega(cursor.getString(6));

                list.add(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return list;
    }

    public ArrayList<Date> mostrarEquiposPorNombre(String propietario) {
        ArrayList<Date> listDatos = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        try (Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EQUIPO + " WHERE propietario LIKE ?", new String[]{"%" + propietario + "%"})) {
            while (cursor != null && cursor.moveToNext()) {
                Date datos = new Date();
                datos.setEstatus(cursor.getString(3));
                datos.setTipo(cursor.getString(2));
                datos.setMarca(cursor.getString(4));
                datos.setN_serie(cursor.getString(1));
                datos.setNombre_area(cursor.getString(6));
                datos.setFecha_ini(cursor.getString(7));
                datos.setPropietario(cursor.getString(5));

                listDatos.add(datos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listDatos;
    }

    public ArrayList<Date> mostrarEquiposPorTipo(String tipo) {
        ArrayList<Date> listDatos = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_EQUIPO + " WHERE tipo LIKE ?", new String[]{"%" + tipo + "%"});

            while (cursor != null && cursor.moveToNext()) {
                Date dato = new Date();
                dato.setEstatus(cursor.getString(3));
                dato.setTipo(cursor.getString(2));
                dato.setMarca(cursor.getString(4));
                dato.setN_serie(cursor.getString(1));
                dato.setNombre_area(cursor.getString(6));
                dato.setFecha_ini(cursor.getString(7));
                dato.setPropietario(cursor.getString(5));

                listDatos.add(dato);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listDatos;
    }

    public ArrayList<Date> mostrarEquiposPorTipoArea(String area) {
        ArrayList<Date> listDatos = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        try (Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EQUIPO + " WHERE area = ?", new String[]{"%" + area + "%"})) {
            while (cursor != null && cursor.moveToNext()) {
                Date datos = new Date();
                datos.setEstatus(cursor.getString(3));
                datos.setTipo(cursor.getString(2));
                datos.setMarca(cursor.getString(4));
                datos.setN_serie(cursor.getString(1));
                datos.setNombre_area(cursor.getString(6));
                datos.setFecha_ini(cursor.getString(7));
                datos.setPropietario(cursor.getString(5));

                listDatos.add(datos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listDatos;
    }
}
