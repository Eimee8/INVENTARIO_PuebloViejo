package com.example.inventario_puebloviejo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.inventario_puebloviejo.db.AdapterEquipo;
import com.example.inventario_puebloviejo.db.DataBase;
import com.example.inventario_puebloviejo.db.Date;

import java.util.ArrayList;

public class Alcoholes extends AppCompatActivity {

    DataBase db;
    private ArrayList<Date> date;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alcoholes);

        db = new DataBase(this);
        recyclerView = findViewById(R.id.VistaAlcoholes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        date = new ArrayList<>();
        date = db.mostrarAlcoholes();

        AdapterEquipo adapter = new AdapterEquipo(date, this);
        recyclerView.setAdapter(adapter);
    }
}