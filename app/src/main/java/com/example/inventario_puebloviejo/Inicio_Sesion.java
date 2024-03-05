package com.example.inventario_puebloviejo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.inventario_puebloviejo.Volley.CallBack;
import com.example.inventario_puebloviejo.Volley.VolleyGET;
import com.example.inventario_puebloviejo.databinding.ActivityInicioSesionBinding;
import com.example.inventario_puebloviejo.db.DataBase;
import com.example.inventario_puebloviejo.ui.home.HomeFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class Inicio_Sesion extends AppCompatActivity implements CallBack {

     //private EditText nombre;
     //private EditText pass;
     //private DataBase db;
     //private Button sesion;
     private ActivityInicioSesionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInicioSesionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.login.setOnClickListener(clickLogin);
        //db = new DataBase(this);

        /*
        nombre = findViewById(R.id.nombre);
        pass = findViewById(R.id.password);
*/
        //sesion = (Button) findViewById(R.id.login);

        /*
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
                            finish();

                        } else {
                            Toast.makeText(Inicio_Sesion.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Inicio_Sesion.this, "Usuario incorrecto", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

         */
    }

    private View.OnClickListener clickLogin = (View) -> {
        String user = binding.nombre.getText().toString();
        String pass = binding.password.getText().toString();

        String url = "https://inventariopv.estudiasistemas.com/inventory/api.php?tk=220220240749&usuario="+ user+"&password=" + pass;
        VolleyGET volleyGET = new VolleyGET(url, this.getBaseContext(), this::callback);
        volleyGET.start();

    };

    @Override
    public void callback(JSONObject jsonObject) {
        try {
            String status = jsonObject.getString("status");
            if(status.equals("200")){
                Intent i = new Intent(Inicio_Sesion.this, MainActivity.class);
                startActivity(i);
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