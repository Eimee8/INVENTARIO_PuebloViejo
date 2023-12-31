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

import com.example.inventario_puebloviejo.A_Tecnologia;
import com.example.inventario_puebloviejo.Alcoholes;
import com.example.inventario_puebloviejo.Cajas;
import com.example.inventario_puebloviejo.Catastro;
import com.example.inventario_puebloviejo.Comandancia;
import com.example.inventario_puebloviejo.Comercio;
import com.example.inventario_puebloviejo.Contraloria;
import com.example.inventario_puebloviejo.D_Social;
import com.example.inventario_puebloviejo.Egresos;
import com.example.inventario_puebloviejo.Informatica;
import com.example.inventario_puebloviejo.Ingresos;
import com.example.inventario_puebloviejo.Inst_Mujer;
import com.example.inventario_puebloviejo.Obra_Publica;
import com.example.inventario_puebloviejo.Panteones;
import com.example.inventario_puebloviejo.R;
import com.example.inventario_puebloviejo.Regidores;
import com.example.inventario_puebloviejo.Registro_Civil;
import com.example.inventario_puebloviejo.Religion;
import com.example.inventario_puebloviejo.Rh;
import com.example.inventario_puebloviejo.Secretarias;
import com.example.inventario_puebloviejo.Tesoreria;
import com.example.inventario_puebloviejo.Trabajo_Social;
import com.example.inventario_puebloviejo.Transparencia;
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
        ImageView regidoresView = root.findViewById(R.id.regidores);
        ImageView tecnologiaView = root.findViewById(R.id.areadetecnologia);
        ImageView obraView = root.findViewById(R.id.obraspublicas);
        ImageView RHView = root.findViewById(R.id.rrhh);
        ImageView religionView = root.findViewById(R.id.religion);
        ImageView institutoMView = root.findViewById(R.id.mujer);
        ImageView comandanciaView = root.findViewById(R.id.comandancia);
        ImageView comercioView = root.findViewById(R.id.comercio);
        ImageView alcoholView = root.findViewById(R.id.alcoholes);
        ImageView panteonesView = root.findViewById(R.id.panteones);
        ImageView transparenciaView = root.findViewById(R.id.transparencia);
        ImageView drllo_socialView = root.findViewById(R.id.desarrollosocial);
        ImageView informaticalView = root.findViewById(R.id.informatica);
        ImageView t_socialView = root.findViewById(R.id.trabajosocial);


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

        regidoresView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Regidores.class);
                startActivity(intent);
            }
        });

        obraView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Obra_Publica.class);
                startActivity(intent);
            }
        });

        tecnologiaView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), A_Tecnologia.class);
                startActivity(intent);
            }
        });

        RHView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Rh.class);
                startActivity(intent);
            }
        });

        religionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Religion.class);
                startActivity(intent);
            }
        });

        institutoMView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Inst_Mujer.class);
                startActivity(intent);
            }
        });

        comandanciaView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Comandancia.class);
                startActivity(intent);
            }
        });

        comercioView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Comercio.class);
                startActivity(intent);
            }
        });

        alcoholView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Alcoholes.class);
                startActivity(intent);
            }
        });

        panteonesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Panteones.class);
                startActivity(intent);
            }
        });

        transparenciaView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Transparencia.class);
                startActivity(intent);
            }
        });

        drllo_socialView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), D_Social.class);
                startActivity(intent);
            }
        });

        informaticalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Informatica.class);
                startActivity(intent);
            }
        });

        t_socialView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Trabajo_Social.class);
                startActivity(intent);
            }
        });


        return root;
    }



}