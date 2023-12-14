 package com.example.inventario_puebloviejo.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.inventario_puebloviejo.MainActivity;
import com.example.inventario_puebloviejo.R;
import com.example.inventario_puebloviejo.databinding.FragmentPendientesBinding;

import com.example.inventario_puebloviejo.db.AdapterPendientes;
import com.example.inventario_puebloviejo.db.DataBase;
import com.example.inventario_puebloviejo.db.Date;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

 public class Pendientes extends Fragment {
     private static final int REQUEST_CODE_CREATE_PDF = 123;

     Button btnPDF;
     DataBase db;
     ArrayList<Date> date;
     RecyclerView recyclerView;
     AdapterPendientes adapterPendientes;
     private FragmentPendientesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPendientesBinding.inflate(inflater, container, false);
        View root= binding.getRoot();

        db = new DataBase(this.getContext());

        date = new ArrayList<>();
        date = db.mostrarEquiposPendientes();

        btnPDF = root.findViewById(R.id.btnPDFPendientes);

        recyclerView = root.findViewById(R.id.vPendientes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        adapterPendientes = new AdapterPendientes(db.mostrarEquiposPendientes(),getContext());
        recyclerView.setAdapter(adapterPendientes);

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

                 for (Date equipo : date) {
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
}