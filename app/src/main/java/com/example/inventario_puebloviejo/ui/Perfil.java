package com.example.inventario_puebloviejo.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inventario_puebloviejo.MainActivity;
import com.example.inventario_puebloviejo.R;
import com.example.inventario_puebloviejo.databinding.FragmentPerfilBinding;


public class Perfil extends Fragment {


    private FragmentPerfilBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root= binding.getRoot();

        ((MainActivity) requireActivity()).setToolbarTitle("Perfil");

        return root;
    }
}