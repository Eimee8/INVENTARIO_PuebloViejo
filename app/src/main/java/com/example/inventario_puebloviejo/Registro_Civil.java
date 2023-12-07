package com.example.inventario_puebloviejo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.inventario_puebloviejo.db.AdapterEquipo;
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

public class Registro_Civil extends AppCompatActivity {

    private static final int REQUEST_CODE_CREATE_PDF = 123;

    DataBase db;
    private ArrayList<Date> date;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_civil);

        db = new DataBase(this);
        recyclerView = findViewById(R.id.VistaRegistroCivil);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        date = new ArrayList<>();
        date = db.mostrarRegistroCivil();

        AdapterEquipo adapter = new AdapterEquipo(date, this);
        recyclerView.setAdapter(adapter);

        Button generarPDFbtn = findViewById(R.id.btnPDFRegistroCivil);

        generarPDFbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generarPDF();
            }
        });
    }

    private void generarPDF() {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_TITLE, "Equipos Registro Civil.pdf");

        startActivityForResult(intent, REQUEST_CODE_CREATE_PDF);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CREATE_PDF && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
                Uri uri = data.getData();
                savePDFToUri(uri);
            }
        }
    }

    private void savePDFToUri(Uri uri) {
        try {
            OutputStream outputStream = getContentResolver().openOutputStream(uri);
            if (outputStream != null) {
                // Configurar el escritor PDF
                PdfWriter pdfWriter = new PdfWriter(outputStream);
                PdfDocument pdfDocument = new PdfDocument(pdfWriter);

                // Inicializar el documento iTextPdf
                Document document = new Document(pdfDocument);

                // Agregar el título centrado al documento
                Paragraph title = new Paragraph("Reporte de Equipos");
                title.setTextAlignment(TextAlignment.CENTER);
                document.add(title);

                // Obtener datos de la tabla y agregarlos al documento
                for (Date equipo : date) {
                    document.add(new Paragraph("Estatus: " + equipo.getEstatus()));
                    document.add(new Paragraph("Tipo: " + equipo.getTipo()));
                    document.add(new Paragraph("Marca: " + equipo.getMarca()));
                    document.add(new Paragraph("Número de Serie: " + equipo.getN_serie()));
                    document.add(new Paragraph("Nombre de Área: " + equipo.getNombre_area()));
                    document.add(new Paragraph("Fecha de Inicio: " + equipo.getFecha_ini()));
                    document.add(new Paragraph("Propietario: " + equipo.getPropietario()));
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