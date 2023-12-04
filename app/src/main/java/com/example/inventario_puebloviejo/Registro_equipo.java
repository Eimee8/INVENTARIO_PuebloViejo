package com.example.inventario_puebloviejo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
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
import java.util.Date;

import com.example.inventario_puebloviejo.databinding.ActivityRegistroEquipoBinding;
import com.example.inventario_puebloviejo.db.DataBase;

public class Registro_equipo extends AppCompatActivity {

    Spinner spinnerTipo;
    Spinner spinnerEstatus;
    private Button btnSeleccionarFecha;
    private Calendar calendar;
    ArrayAdapter<CharSequence> spinnerAdapter;
    ArrayAdapter<CharSequence> spinnerEstatusAdapter;

    DataBase db;

    Button btnregistro, fecha;

    EditText serie, marca, prop;

    Spinner tipo, status, area;

    private ActivityRegistroEquipoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroEquipoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        spinnerTipo = findViewById(R.id.Tipo);
        CharSequence[] opciones = getResources().getTextArray(R.array.opciones_tipo);
        // Crea el ArrayAdapter utilizando el array de recursos
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
        // Especifica el diseño que se utilizará cuando aparezca la lista de opciones
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Asigna el ArrayAdapter al Spinner
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

        db = new DataBase(this);



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
                        } else {
                            Toast.makeText(Registro_equipo.this, "Error al registrar el equipo", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });


        btnSeleccionarFecha = (Button) findViewById(R.id.btnSeleccionarFecha);
        calendar = Calendar.getInstance();

        btnSeleccionarFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDatePickerDialog();
            }
        });
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
        Toast.makeText(this, "Fecha seleccionada: " + fechaSeleccionada, Toast.LENGTH_SHORT).show();
    }







}