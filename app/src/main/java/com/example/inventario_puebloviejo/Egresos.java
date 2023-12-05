package com.example.inventario_puebloviejo;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.inventario_puebloviejo.databinding.ActivityRegistroEquipoBinding;
import com.example.inventario_puebloviejo.db.AdapterEquipo;
import com.example.inventario_puebloviejo.db.DataBase;
import com.example.inventario_puebloviejo.db.Date;

import java.util.ArrayList;

public class Egresos extends AppCompatActivity {

    private ActivityRegistroEquipoBinding binding;
    DataBase db;
    ArrayList <Date>date;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegistroEquipoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = new DataBase(this);

        date = db.mostrarArea();

        recyclerView = findViewById(R.id.VistaEgresos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AdapterEquipo adapter = new AdapterEquipo(date, this);
        recyclerView.setAdapter(adapter);

    }
}