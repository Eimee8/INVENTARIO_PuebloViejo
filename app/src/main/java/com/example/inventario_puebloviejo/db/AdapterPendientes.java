package com.example.inventario_puebloviejo.db;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventario_puebloviejo.R;

import java.util.ArrayList;

public class AdapterPendientes extends  RecyclerView.Adapter<AdapterPendientes.ViewHolder> {

    static ArrayList<Date>list;
    public AdapterPendientes(ArrayList<Date> list, Context context) {
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pendientes,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.statusP.setText(list.get(position).getEstatus());
        holder.tipoP.setText(list.get(position).getTipo());
        holder.serieP.setText(list.get(position).getN_serie());
        holder.descripcionP.setText(list.get(position).getDescripcion());
        holder.fechaLlegadaP.setText(list.get(position).getFecha_llegada());
        holder.fechaEntregaP.setText(list.get(position).getFecha_entrega());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder  extends RecyclerView.ViewHolder {
        private TextView statusP, tipoP, serieP, descripcionP, fechaLlegadaP, fechaEntregaP;
        public ViewHolder(@NonNull View View) {
            super(View);

            statusP = View.findViewById(R.id.status_equipoP);
            tipoP = View.findViewById(R.id.TipoEquipoP);
            serieP = View.findViewById(R.id.numSerieP);
            descripcionP = View.findViewById(R.id.descripcionP);
            fechaLlegadaP = View.findViewById(R.id.FechaLlegadaP);
            fechaEntregaP = View.findViewById(R.id.FechaEntregaP);


        }
    }
}
