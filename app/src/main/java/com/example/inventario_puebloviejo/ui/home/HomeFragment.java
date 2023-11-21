package com.example.inventario_puebloviejo.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.inventario_puebloviejo.R;
import com.example.inventario_puebloviejo.databinding.FragmentHomeBinding;
import com.example.inventario_puebloviejo.Egresos;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    ImageView egresos;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

       egresos = (ImageView) root.findViewById(R.id.egresos);


       egresos.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getActivity(), Egresos.class);
               startActivity(intent);
           }
       });



        return root;
    }



}