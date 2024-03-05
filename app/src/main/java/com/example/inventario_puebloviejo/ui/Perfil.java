package com.example.inventario_puebloviejo.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inventario_puebloviejo.MainActivity;
import com.example.inventario_puebloviejo.Modificar;
import com.example.inventario_puebloviejo.R;
import com.example.inventario_puebloviejo.databinding.FragmentPerfilBinding;
import com.example.inventario_puebloviejo.db.DataBase;
import com.example.inventario_puebloviejo.db.Date;

import java.util.ArrayList;


public class Perfil extends Fragment {

    Button btnmodificar;
    TextView Nombre, Puesto, Correo, Telefono;
    DataBase db;
    private FragmentPerfilBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root= binding.getRoot();

        ((MainActivity) requireActivity()).setToolbarTitle("Perfil");

        db = new DataBase(this.getContext());

        Nombre = root.findViewById(R.id.NombreCompleto);
        Puesto = root.findViewById(R.id.Puesto);
        Correo = root.findViewById(R.id.CorreoElectronico);
        Telefono = root.findViewById(R.id.NumeroCelular);
        btnmodificar = root.findViewById(R.id.btnModificar);

        btnmodificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Modificar.class);
                startActivity(i);
            }
        });


    getPerfil();

        return root;
    }

    private void getPerfil() {
        String nombre = "Admin";
        if (nombre.isEmpty()) {
            Toast.makeText(getContext(), "Error al buscar el usuario", Toast.LENGTH_SHORT).show();
        } else {

            ArrayList<Date> listaUsuarios = db.mostrarDatosUsuario(nombre);

            if (!listaUsuarios.isEmpty()) {
                Date usuario = listaUsuarios.get(0);

                binding.NombreCompleto.setText(usuario.getNombre());
                binding.Puesto.setText(usuario.getPuesto());
                binding.CorreoElectronico.setText(usuario.getCorreo());
                binding.NumeroCelular.setText(usuario.getTelefono());
            } else {
                Toast.makeText(getContext(), "No se encontraron datos del usuario", Toast.LENGTH_SHORT).show();
            }
}
}
}