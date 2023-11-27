package com.example.inventario_puebloviejo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Inicio_Sesion extends AppCompatActivity {

    Button sesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        sesion = (Button) findViewById(R.id.login);

        sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Inicio_Sesion.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}