package com.example.inventario_puebloviejo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.inventario_puebloviejo.Equipo.Equipo;
import com.example.inventario_puebloviejo.Volley.CallBack;
import com.example.inventario_puebloviejo.Volley.VolleyPOST;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class ModificarInformacion extends AppCompatActivity  implements CallBack {
    private Spinner spinnerTipo,spinnerEstatus, spinnerArea;
    private EditText txtserie,txtpropietario, txtmarca;
    private Button btnupdate, btnfecha;
    private Calendar calendar;
    private String nserieintent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_informacion);
        initComponents();
        getBundleExtra();

        btnfecha.setOnClickListener((View)->{
            mostrarDatePickerDialog();
        });

        btnupdate.setOnClickListener(clickUpdate);
    }

    /**
     * Inicializamos los componentes de la interfaz grafica
     */
    private void initComponents(){
        spinnerTipo = findViewById(R.id.mtipo);
        initSpinner(R.array.opciones_tipoAgenda, spinnerTipo);

        spinnerEstatus = findViewById(R.id.Status);
        initSpinner(R.array.opciones_estatusAgenda, spinnerEstatus);

        spinnerArea = findViewById(R.id.marea);
        initSpinner(R.array.opciones_area, spinnerArea);

        btnupdate = findViewById(R.id.btnRegistroEquipo);

        txtserie = findViewById(R.id.mnoserie);
        txtmarca = findViewById(R.id.mmarca);
        txtpropietario = findViewById(R.id.propietario);

        btnfecha = findViewById(R.id.btnSeleccionarFecha);
        calendar = Calendar.getInstance();
    }

    private void getBundleExtra(){
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            nserieintent = extras.getString("n_serie");
        }
    }
    /**
     * Inicializamos y configuramos un objeto de tipo DatePickerDialog
     * para mostrarlo en pantalla y el usuario seleccione una fecha.
     */
    private void mostrarDatePickerDialog() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
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

    /**
     * La funcion se ecnarga de inicialiar los spinner con el identificador del array de
     * string pasado por parametro.
     *
     * @param id
     * @param spinner
     */
    private void initSpinner(int id, Spinner spinner){
        CharSequence[] opciones = getResources().getTextArray(id);
        ArrayAdapter adapter  = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    /**
     * Listener que recolecta al informacion proporcionada por el usaurio
     * para realizar un peticion post para actualizar el equipo.
     *
     */
    private View.OnClickListener clickUpdate = (View) ->{
        String url = "https://inventariopv.estudiasistemas.com/inventory/api.php?tk=280220240702";
        String n_serie = txtserie.getText().toString();
        String tipo = spinnerTipo.getSelectedItem().toString();
        String estatus = spinnerEstatus.getSelectedItem().toString();
        String marca = txtmarca.getText().toString();
        String propietario = txtpropietario.getText().toString();
        String area = spinnerArea.getSelectedItem().toString();

        String fecha = getFechaPicker();


        Equipo equipo = new Equipo(n_serie,tipo, estatus, marca,propietario,area,fecha);
        equipo.setId(nserieintent);

        System.out.println(equipo.toJson());

        VolleyPOST post = new VolleyPOST(url,this, equipo.toJson(),this::callback);
        post.start();
    };

    @Override
    public void callback(JSONObject jsonObject) {
        try {

            String status = jsonObject.getString("status");
            if(status.equals("200")){
                Toast.makeText(this, "Equipo Actualizado", Toast.LENGTH_SHORT).show();
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