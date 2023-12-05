package com.example.inventario_puebloviejo.db;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.inventario_puebloviejo.R;
import com.example.inventario_puebloviejo.db.Date;

import java.util.ArrayList;

public class AdapterEquipo extends RecyclerView.Adapter<AdapterEquipo.ViewHolder> {
    static ArrayList<Date>lista;

    public AdapterEquipo(ArrayList<Date> lista, Context context) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Visualizacion de los datos con la vista
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.equipo,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {

        //Relacion de las columnas de las tablas con el AdapterP
       // holder.id.setText(String.valueOf(lista.get(position).getId()));
        holder.status.setText(lista.get(position).getEstatus());
        holder.marca.setText(lista.get(position).getMarca());
        holder.serie.setText(lista.get(position).getN_serie());
        holder.nomarea.setText(lista.get(position).getNombre_area());
        holder.fecha.setText(lista.get(position).getFecha_ini());
        holder.propietario.setText(lista.get(position).getPropietario());

        //Datos datos = lista.get(position);
      //  byte[] imagen = datos.getImagen();
        //Bitmap bitmap = BitmapFactory.decodeByteArray(imagen, 0, imagen.length);
       // holder.imagen.setImageBitmap(bitmap);

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder  extends RecyclerView.ViewHolder{
        private TextView status, marca, serie, nomarea, fecha, propietario;
       // private ImageView btnVista, btncompra;

        public ViewHolder(@NonNull View View) {
            super(View);

            //Relacion de las columnas con los componentes de la vista
            //id = View.findViewById(R.id.idproducto);
            status = View.findViewById(R.id.status_equipo);
            marca = View.findViewById(R.id.marcaEquipo);
            serie = View.findViewById(R.id.numSerie);
            nomarea = View.findViewById(R.id.nomArea);
            fecha = View.findViewById(R.id.Fecha);
            propietario = View.findViewById(R.id.propietarioEquipo);
            //imagen = View.findViewById(R.id.product_image);
           // btnVista = View.findViewById(R.id.btnvistaProducto);
           // btncompra = View.findViewById(R.id.btnCarrro);


        }

    }
}
