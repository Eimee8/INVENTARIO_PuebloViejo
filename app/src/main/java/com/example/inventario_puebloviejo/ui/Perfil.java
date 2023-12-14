package com.example.inventario_puebloviejo.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inventario_puebloviejo.MainActivity;
import com.example.inventario_puebloviejo.R;
import com.example.inventario_puebloviejo.databinding.FragmentPerfilBinding;
import com.example.inventario_puebloviejo.db.DataBase;
import com.example.inventario_puebloviejo.db.Date;

import java.util.ArrayList;


public class Perfil extends Fragment {


    private FragmentPerfilBinding binding;
    private  DataBase db;

    Date date;

   TextView Nombre, Puesto, Correo, Telefono;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root= binding.getRoot();

        db = new DataBase(this.getContext());

        ((MainActivity) requireActivity()).setToolbarTitle("Perfil");


        Nombre = root.findViewById(R.id.NombreCompleto);
        Puesto = root.findViewById(R.id.Puesto);
        Correo = root.findViewById(R.id.CorreoElectronico);
        Telefono = root.findViewById(R.id.NumeroCelular);

        getPerfil();

        return root;
    }

    private void getPerfil() {
        String nombre = "Admin";  // Reemplaza esto con la forma real de obtener el nombre

        if (nombre.isEmpty()) {
            Toast.makeText(getContext(), "Error al buscar el usuario", Toast.LENGTH_SHORT).show();
        } else {
            // Llama a la función mostrarDatosUsuario directamente con el nombre del usuario
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