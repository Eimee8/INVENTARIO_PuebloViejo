package com.example.inventario_puebloviejo.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.inventario_puebloviejo.Cajas;
import com.example.inventario_puebloviejo.Catastro;
import com.example.inventario_puebloviejo.Contraloria;
import com.example.inventario_puebloviejo.Egresos;
import com.example.inventario_puebloviejo.Ingresos;
import com.example.inventario_puebloviejo.R;
import com.example.inventario_puebloviejo.Registro_Civil;
import com.example.inventario_puebloviejo.Secretarias;
import com.example.inventario_puebloviejo.Tesoreria;
import com.example.inventario_puebloviejo.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        ImageView egresosView = root.findViewById(R.id.egresos);
        ImageView ingresosView = root.findViewById(R.id.ingresos);
        ImageView tesoreriaView = root.findViewById(R.id.tesoreria);
        ImageView cajasView = root.findViewById(R.id.cajas);
        ImageView catastroView = root.findViewById(R.id.catastro);
        ImageView contraloriaView = root.findViewById(R.id.contraloria);
        ImageView registroView = root.findViewById(R.id.registrocivil);
        ImageView secretariasView = root.findViewById(R.id.secretarias);


        egresosView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), Egresos.class);
                startActivity(intent);

            }
        });

        ingresosView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), Ingresos.class);
                startActivity(intent);

            }
        });

        tesoreriaView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Tesoreria.class);
                startActivity(intent);
            }
        });

        cajasView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Cajas.class);
                startActivity(intent);
            }
        });

        catastroView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Catastro.class);
                startActivity(intent);
            }
        });

        contraloriaView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Contraloria.class);
                startActivity(intent);
            }
        });

        registroView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Registro_Civil.class);
                startActivity(intent);
            }
        });

        secretariasView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Secretarias.class);
                startActivity(intent);
            }
        });


        return root;
    }



}