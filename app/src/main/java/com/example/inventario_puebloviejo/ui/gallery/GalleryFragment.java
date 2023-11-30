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

import com.example.inventario_puebloviejo.R;
import com.example.inventario_puebloviejo.Registro_equipo;
import com.example.inventario_puebloviejo.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {

    Button registro;
    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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