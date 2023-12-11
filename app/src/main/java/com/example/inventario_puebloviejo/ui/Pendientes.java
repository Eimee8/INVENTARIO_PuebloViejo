 package com.example.inventario_puebloviejo.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inventario_puebloviejo.MainActivity;
import com.example.inventario_puebloviejo.R;
import com.example.inventario_puebloviejo.databinding.FragmentPendientesBinding;

import com.example.inventario_puebloviejo.db.AdapterPendientes;
import com.example.inventario_puebloviejo.db.DataBase;
import com.example.inventario_puebloviejo.db.Date;

import java.util.ArrayList;

 public class Pendientes extends Fragment {

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

        recyclerView = root.findViewById(R.id.vPendientes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        adapterPendientes = new AdapterPendientes(db.mostrarEquiposPendientes(),getContext());
        recyclerView.setAdapter(adapterPendientes);

        ((MainActivity) requireActivity()).setToolbarTitle("Pendientes");

        return  root;

    }
}