 package com.example.inventario_puebloviejo.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.inventario_puebloviejo.Equipo.AdapterEquipo;
import com.example.inventario_puebloviejo.Equipo.Equipo;
import com.example.inventario_puebloviejo.Inicio_Sesion;
import com.example.inventario_puebloviejo.MainActivity;
import com.example.inventario_puebloviejo.Mantenimiento.Mantenimiento;
import com.example.inventario_puebloviejo.R;
import com.example.inventario_puebloviejo.Volley.CallBack;
import com.example.inventario_puebloviejo.Volley.VolleyGET;
import com.example.inventario_puebloviejo.databinding.FragmentPendientesBinding;

import com.example.inventario_puebloviejo.db.AdapterPendientes;
import com.example.inventario_puebloviejo.db.DataBase;
import com.example.inventario_puebloviejo.db.Date;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

 public class Pendientes extends Fragment implements CallBack {
     private static final int REQUEST_CODE_CREATE_PDF = 123;

     Button btnPDF;
     DataBase db;
     ArrayList<Date> date;
     //private ArrayList<Equipo> data;
     private ArrayList<Mantenimiento> data;
     RecyclerView recyclerView;
     AdapterPendientes adapterPendientes;
     private FragmentPendientesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPendientesBinding.inflate(inflater, container, false);
        View root= binding.getRoot();

        db = new DataBase(this.getContext());
    /*
        date = new ArrayList<>();
        date = db.mostrarEquiposPendientes();
*/
        btnPDF = root.findViewById(R.id.btnPDFPendientes);

        recyclerView = root.findViewById(R.id.vPendientes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        queryPendientes();
        /*
        adapterPendientes = new AdapterPendientes(db.mostrarEquiposPendientes(),getContext());
        recyclerView.setAdapter(adapterPendientes);
*/
        ((MainActivity) requireActivity()).setToolbarTitle("Pendientes");

        btnPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generarPDF();
            }
        });

        return  root;

    }

     private void generarPDF() {
         Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
         intent.addCategory(Intent.CATEGORY_OPENABLE);
         intent.setType("application/pdf");
         intent.putExtra(Intent.EXTRA_TITLE, "Equipos Pendientes.pdf");

         startActivityForResult(intent, REQUEST_CODE_CREATE_PDF);
     }

     public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
         if (requestCode == REQUEST_CODE_CREATE_PDF && resultCode == AppCompatActivity.RESULT_OK) {
             if (data != null && data.getData() != null) {
                 Uri uri = data.getData();
                 savePDFToUri(uri);
             }
         }
     }

     private void savePDFToUri(Uri uri) {
         try {
             OutputStream outputStream = requireContext().getContentResolver().openOutputStream(uri);
             if (outputStream != null) {

                 PdfWriter pdfWriter = new PdfWriter(outputStream);
                 PdfDocument pdfDocument = new PdfDocument(pdfWriter);

                 Document document = new Document(pdfDocument);

                 Paragraph title = new Paragraph("Reporte de Equipos Pendientes");
                 title.setTextAlignment(TextAlignment.CENTER);
                 document.add(title);

                 for (Mantenimiento equipo : data) {
                     document.add(new Paragraph("Estatus: " + equipo.getEstatus()));
                     document.add(new Paragraph("Tipo: " + equipo.getTipo()));
                     document.add(new Paragraph("Número de Serie: " + equipo.getN_serie()));
                     document.add(new Paragraph("Descripcion: " + equipo.getDescripcion()));
                     document.add(new Paragraph("Fecha de Llegada: " + equipo.getFecha_llegada()));
                     document.add(new Paragraph("Fecha de Entrega: " + equipo.getFecha_entrega()));
                     document.add(new Paragraph("----------------------------------------"));
                 }

                 document.close();
                 outputStream.close();
             }
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

     private void queryPendientes(){
         String url = "https://inventariopv.estudiasistemas.com/inventory/api.php?tk=280220241053";
         VolleyGET volleyGET = new VolleyGET(url, getContext(), this::callback);
         volleyGET.start();
     }

     /**
      * Convierte el json consultado en un ArrayList<Equipo>
      *
      * @param jsonArray
      * @return ArrayList<Date>
      */
     private ArrayList<Mantenimiento> jsonToDateArray(JSONArray jsonArray){

         ArrayList<Mantenimiento> list = new ArrayList<>();

         for (int i = 0; i < jsonArray.length(); i++) {
             try {

                 JSONObject element = jsonArray.getJSONObject(i);

                 String n_serie = element.getString("n_serie");
                 String tipo = element.getString("tipo");
                 String estatus =element.getString("estatus");
                 String descripcion = element.getString("descripcion");
                 String fecha_llegada = element.getString("fecha_llegada");
                 String fecha_entrega = element.getString("fecha_entrega");

                 list.add(new Mantenimiento(n_serie, tipo, estatus,descripcion, fecha_llegada,fecha_entrega));

             } catch (JSONException e) {
                 Log.e("ToList",e.getMessage());
             }
         }

         return list;
     }

     @Override
     public void callback(JSONObject jsonObject) {
         try {
             String status = jsonObject.getString("status");
             if(status.equals("200")){

                 data = jsonToDateArray(jsonObject.getJSONArray("data"));
                 recyclerView.setAdapter(new AdapterPendientes(data, getContext()));

             }else if(status.equals("404")){
                 String error = jsonObject.getString("Error");
                 Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
             }else{
                 String error = jsonObject.getString("Error");
                 Log.e("Login", error);
                 Toast.makeText(getContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
             }
         } catch (JSONException e) {
             Log.e("login", e.getMessage());
         }
     }
 }