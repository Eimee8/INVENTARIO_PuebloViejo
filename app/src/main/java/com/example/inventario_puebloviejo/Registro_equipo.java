package com.example.inventario_puebloviejo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import android.content.Context;

import com.example.inventario_puebloviejo.Equipo.Equipo;
import com.example.inventario_puebloviejo.Volley.CallBack;
import com.example.inventario_puebloviejo.Volley.VolleyPOST;
import com.example.inventario_puebloviejo.databinding.ActivityRegistroEquipoBinding;
import com.example.inventario_puebloviejo.db.DataBase;
import com.example.inventario_puebloviejo.ui.gallery.GalleryFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class Registro_equipo extends AppCompatActivity implements CallBack {

    Spinner spinnerTipo;
    Spinner spinnerEstatus;
    private Button btnSeleccionarFecha;
    private Calendar calendar;
    ArrayAdapter<CharSequence> spinnerAdapter;
    ArrayAdapter<CharSequence> spinnerEstatusAdapter;
/*
    DataBase db;

    Button btnregistro;

    EditText serie, marca, prop;

    Spinner tipo, status, area;
*/
    private ActivityRegistroEquipoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroEquipoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        spinnerTipo = findViewById(R.id.Tipo);

        CharSequence[] opciones = getResources().getTextArray(R.array.opciones_tipo);
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipo.setAdapter(spinnerAdapter);

        spinnerEstatus = findViewById(R.id.Status);
        CharSequence[] opcionesEstatus = getResources().getTextArray(R.array.opciones_estatus);
        spinnerEstatusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcionesEstatus);
        spinnerEstatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstatus.setAdapter(spinnerEstatusAdapter);


        Spinner spinnerArea = findViewById(R.id.Area);
        CharSequence[] opcionesArea = getResources().getTextArray(R.array.opciones_area);
        ArrayAdapter<CharSequence> spinnerAreaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcionesArea);
        spinnerAreaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerArea.setAdapter(spinnerAreaAdapter);

        binding.btnRegistroEquipo.setOnClickListener(clickRegistrar);
        //db = new DataBase(this);
/*
        serie = findViewById(R.id.noserie);
        tipo =  findViewById(R.id.Tipo);
        status =  findViewById(R.id.Status);
        marca = findViewById(R.id.Marca);
        prop = findViewById(R.id.propietario);
        area = findViewById(R.id.Area);

        btnregistro = (Button) findViewById(R.id.btnRegistroEquipo);

        btnregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String serie = binding.noserie.getText().toString();
                String tipo = binding.Tipo.getSelectedItem().toString();
                String status = binding.Status.getSelectedItem().toString();
                String marca = binding.Marca.getText().toString();
                String prop = binding.propietario.getText().toString();
                String area = binding.Area.getSelectedItem().toString();
                String formatofecha = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(formatofecha, Locale.getDefault());
                String fechaSeleccionada = sdf.format(calendar.getTime());

                if (serie.isEmpty() || tipo.isEmpty() || status.isEmpty() || marca.isEmpty() || prop.isEmpty() || area.isEmpty()) {
                    Toast.makeText(Registro_equipo.this, "Campos Vacíos", Toast.LENGTH_SHORT).show();
                } else {
                    boolean equipoExistente = db.equipoExistente(serie);
                    if (equipoExistente) {
                        Toast.makeText(Registro_equipo.this, "El equipo ya existe", Toast.LENGTH_SHORT).show();
                    } else {
                        // Corrección: Pasa la fecha formateada (fechaSeleccionada) en lugar del formato de fecha (formatofecha)
                        boolean correcto = db.insertEquipo(serie, tipo, status, marca, prop, area, fechaSeleccionada);
                        if (correcto) {
                            Toast.makeText(Registro_equipo.this, "Se registró correctamente el equipo", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Registro_equipo.this, MainActivity.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(Registro_equipo.this, "Error al registrar el equipo", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
*/

        btnSeleccionarFecha = (Button) findViewById(R.id.btnSeleccionarFecha);
        calendar = Calendar.getInstance();

        btnSeleccionarFecha.setOnClickListener((View)->{
            mostrarDatePickerDialog();
        });

        /*
        btnSeleccionarFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDatePickerDialog();
            }
        });
         */

    }

    private void mostrarDatePickerDialog() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                actualizarFechaSeleccionada();
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void actualizarFechaSeleccionada() {
        String formatoFecha = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(formatoFecha, Locale.getDefault());

        String fechaSeleccionada = sdf.format(calendar.getTime());
        //Toast.makeText(this, "Fecha seleccionada: " + fechaSeleccionada, Toast.LENGTH_SHORT).show();
    }

    /*****
     * La funcion extrae del componente DatePicker la fecha seleccionada
     * por el usuario para posteriormente hacerle un format (dd/MM/yyyy)
     *
     * @return String
     * */
    private String getFechaPicker(){
        String formatoFecha = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(formatoFecha, Locale.getDefault());
        return sdf.format(calendar.getTime());
    }

    /*****
     * Creamos un evento para detectar el click el cual nos recolectara la
     * informacion proporcionada por el usuario y realizara una peticion
     * POST al servidor.
     *
     * */
    private View.OnClickListener clickRegistrar = (View) -> {
        String url = "https://inventariopv.estudiasistemas.com/inventory/api.php?tk=200220240844";

        String n_serie = binding.noserie.getText().toString();
        String tipo = binding.Tipo.getSelectedItem().toString();
        String estatus = binding.Status.getSelectedItem().toString();
        String marca = binding.Marca.getText().toString();
        String propietario = binding.propietario.getText().toString();
        String area = binding.Area.getSelectedItem().toString();
        String fecha = getFechaPicker();

        Equipo equipo = new Equipo(n_serie,tipo, estatus, marca,propietario,area,fecha);
        System.out.println(equipo.toJson());
        VolleyPOST post = new VolleyPOST(url, getBaseContext(), equipo.toJson(),this::callback);
        post.start();
    };


    @Override
    public void callback(JSONObject jsonObject) {
        try {
            String status = jsonObject.getString("status");
            if(status.equals("200")){
                Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                finish();
            }else if(status.equals("404")){
                String error = jsonObject.getString("Error");
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }else{
                String error = jsonObject.getString("Error");
                Log.e("Login", error);
                Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            Log.e("login", e.getMessage());
        }
    }
}