package com.example.inventario_puebloviejo.ui.gallery;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventario_puebloviejo.R;
import com.example.inventario_puebloviejo.Registro_equipo;
import com.example.inventario_puebloviejo.databinding.FragmentGalleryBinding;
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

public class GalleryFragment extends Fragment {

    private static final int REQUEST_CODE_CREATE_PDF = 123;

    EditText busqueda;
    Button registro, button;
    private FragmentGalleryBinding binding;

    DataBase db;

    private ArrayList<Date> date;
    RecyclerView recyclerView;

    AdapterEquipo adapterEquipo;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = new DataBase(this.getContext());

        date = new ArrayList();

        date = db.mostrarEquipos();

        recyclerView = root.findViewById(R.id.vEquipo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        adapterEquipo = new AdapterEquipo(db.mostrarEquipos(),getContext());
        recyclerView.setAdapter(adapterEquipo);

        busqueda = root.findViewById(R.id.Busqueda);

        registro = (Button) root.findViewById(R.id.btnRegistro);

        busqueda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String filtroBusqueda = s.toString();
                filtrarEquiposPorNombre(filtroBusqueda);
            }

        });

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Registro_equipo.class);
                startActivity(intent);
            }
        });

        button  = root.findViewById(R.id.btnPDFGeneral);

       button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generarPDF();
            }
        });

        return root;
    }

    private void filtrarEquiposPorNombre(String filtro) {
        ArrayList<Date> resultados = db.mostrarEquiposPorNombre(filtro);
        adapterEquipo.actualizarDatos(resultados);
    }



    private void generarPDF() {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_TITLE, "Equipos Generales.pdf");

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

                Paragraph title = new Paragraph("Reporte de Equipos");
                title.setTextAlignment(TextAlignment.CENTER);
                document.add(title);

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