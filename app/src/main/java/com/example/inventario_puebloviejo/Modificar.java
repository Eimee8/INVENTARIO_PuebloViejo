package com.example.inventario_puebloviejo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.inventario_puebloviejo.Equipo.AdapterEquipo;
import com.example.inventario_puebloviejo.Volley.CallBack;
import com.example.inventario_puebloviejo.Volley.VolleyPOST;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Modificar extends AppCompatActivity implements CallBack {

    private EditText txtcorreo, txtnombre, txtpuesto, txtnumero;
    private Button btnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);
        initComponents();
        btnUpdate.setOnClickListener(clickActualizar);
    }

    private void initComponents(){
        txtcorreo = findViewById(R.id.CorreoElectronico);
        txtnombre = findViewById(R.id.NombreCompleto);
        txtpuesto = findViewById(R.id.Puesto);
        txtnumero = findViewById(R.id.NumeroCelular);

        btnUpdate = findViewById(R.id.btnupdate);
    }

    private View.OnClickListener clickActualizar = (View) ->{
        updateUser();
    };

    private void updateUser(){
        JSONObject json = new JSONObject();
        try {
            json.put("nombre", txtnombre.getText().toString());
            json.put("correo", txtcorreo.getText().toString());
            json.put("puesto", txtpuesto.getText().toString());
            json.put("telefono", txtnumero.getText().toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        String url = "https://inventariopv.estudiasistemas.com/inventory/api.php?tk=030320240256";
        VolleyPOST post = new VolleyPOST(url,this,json,this::callback);
        post.start();
    }

    @Override
    public void callback(JSONObject jsonObject) {
        try {
            String status = jsonObject.getString("status");
            if(status.equals("200")){
                Toast.makeText(this, "Informacion Actualizada", Toast.LENGTH_SHORT).show();
                finish();
            }else if(status.equals("404")){
                String error = jsonObject.getString("Error");
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }else{
                String error = jsonObject.getString("Error");
                Log.e("500", error);
                Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            Log.e("egresos", e.getMessage());
        }
    }
}