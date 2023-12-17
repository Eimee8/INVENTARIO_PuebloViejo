package com.example.inventario_puebloviejo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.inventario_puebloviejo.databinding.ActivityInicioSesionBinding;
import com.example.inventario_puebloviejo.db.DataBase;
import com.example.inventario_puebloviejo.ui.home.HomeFragment;

public class Inicio_Sesion extends AppCompatActivity {

     EditText nombre;
     EditText pass;
    DataBase db;
    private Button sesion;

    private ActivityInicioSesionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInicioSesionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = new DataBase(this);

        nombre = findViewById(R.id.nombre);
        pass = findViewById(R.id.password);

        sesion = (Button) findViewById(R.id.login);

        sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre =  binding.nombre.getText().toString();
                String pass = binding.password.getText().toString();

                if (nombre.isEmpty() || pass.isEmpty()) {

                    Toast.makeText(Inicio_Sesion.this, "Los campos están vacíos", Toast.LENGTH_SHORT).show();
                } else {

                    boolean usuarioCorrecto = db.usuarioCorrecto(nombre);

                    if (usuarioCorrecto) {
                        boolean contraseñaCorrecta = db.contraseñaCorrecta(nombre, pass);

                        if (contraseñaCorrecta) {

                            Toast.makeText(Inicio_Sesion.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(Inicio_Sesion.this, MainActivity.class);
                            startActivity(i);

                        } else {
                            Toast.makeText(Inicio_Sesion.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Inicio_Sesion.this, "Usuario incorrecto", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}