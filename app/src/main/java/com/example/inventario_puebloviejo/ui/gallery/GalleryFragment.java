package com.example.inventario_puebloviejo.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventario_puebloviejo.R;
import com.example.inventario_puebloviejo.Registro_equipo;
import com.example.inventario_puebloviejo.databinding.FragmentGalleryBinding;
import com.example.inventario_puebloviejo.db.AdapterEquipo;
import com.example.inventario_puebloviejo.db.DataBase;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    Button registro;
    private FragmentGalleryBinding binding;

    DataBase db;
    ArrayList date;
    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        db = new DataBase(this.getContext());

        date = new ArrayList();

        recyclerView = root.findViewById(R.id.vEquipo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        AdapterEquipo adapterEquipo = new AdapterEquipo(db.mostrarEquipos(),getContext());
        recyclerView.setAdapter(adapterEquipo);

        registro = (Button) root.findViewById(R.id.btnRegistro);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Registro_equipo.class);
                startActivity(intent);
            }
        });


        return root;
    }

    

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}