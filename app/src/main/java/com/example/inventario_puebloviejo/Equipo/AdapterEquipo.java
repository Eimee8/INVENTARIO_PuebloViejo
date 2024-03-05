package com.example.inventario_puebloviejo.Equipo;

import android.content.Context;
import android.content.Intent;
import android.telephony.IccOpenLogicalChannelResponse;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventario_puebloviejo.ModificarInformacion;
import com.example.inventario_puebloviejo.R;
import com.example.inventario_puebloviejo.Volley.CallBack;
import com.example.inventario_puebloviejo.Volley.VolleyGET;
import com.example.inventario_puebloviejo.db.Date;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdapterEquipo extends RecyclerView.Adapter<AdapterEquipo.ViewHolder> implements CallBack {
    private  ArrayList<Equipo>lista;
    private  ArrayList<Equipo> lista_espejo;
    private Context context;
    public AdapterEquipo(ArrayList<Equipo> lista, Context context) {
        this.lista = lista;
        this.lista_espejo = lista;
        this.context = context;
    }

    public void searchName(String pala){
        if (pala.equals("") ){
            clear();
            for (Equipo e: lista_espejo) {
                lista.add(e);
                notifyItemInserted(lista.size());
            }
        }else{
            this.lista_espejo = (ArrayList<Equipo>) lista.clone();
            lista.removeIf(e -> !e.getPropietario().contains(pala));
            notifyDataSetChanged();
        }


    }
    public void removeItem(int position){
        lista.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }
    public void clear(){
        int size = lista.size();
        lista.clear();
        notifyItemRangeRemoved(0, size);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.equipo,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {

        holder.status.setText(lista.get(position).getEstatus());
        holder.tipo.setText(lista.get(position).getTipo());
        holder.marca.setText(lista.get(position).getMarca());
        holder.serie.setText(lista.get(position).getN_serie());
        holder.nomarea.setText(lista.get(position).getArea());
        holder.fecha.setText(lista.get(position).getFecha_ini());
        holder.propietario.setText(lista.get(position).getPropietario());

        holder.btnModificarDatos.setOnClickListener((View) ->{
            Intent i = new Intent(View.getContext(), ModificarInformacion.class);
            i.putExtra("n_serie", lista.get(position).getN_serie());
            View.getContext().startActivity(i);
        });

        holder.btnEliminar.setOnClickListener((View) ->{
            String url = "https://inventariopv.estudiasistemas.com/inventory/api.php?tk=280220240708&id=" +lista.get(position).getN_serie();
            VolleyGET get = new VolleyGET(url,View.getContext(),this::callback);
            get.start();

            removeItem(position);
        });

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    @Override
    public void callback(JSONObject jsonObject) {
        try {
            String status = jsonObject.getString("status");
            if(status.equals("200")){
                Toast.makeText(context, "ELIMINADO EXITOSAMENTE", Toast.LENGTH_SHORT).show();
            }else if(status.equals("404")){
                String error = jsonObject.getString("Error");

            }else{
                String error = jsonObject.getString("Error");
                Log.e("Login", error);
            }
        } catch (JSONException e) {
            Log.e("login", e.getMessage());
        }
    }

    /*
    public void actualizarDatos(ArrayList<Date> nuevosDatos) {
        this.lista.clear(); // Limpiar la lista existente
        this.lista.addAll(nuevosDatos); // Agregar los nuevos datos
        notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
    }
*/
    public static class ViewHolder  extends RecyclerView.ViewHolder{
        private TextView status, tipo, marca, serie, nomarea, fecha, propietario;
        private ImageView btnModificarDatos, btnEliminar;


        public ViewHolder(@NonNull View View) {
            super(View);

            //Relacion de las columnas con los componentes de la vista
            //id = View.findViewById(R.id.idproducto);
            status = View.findViewById(R.id.status_equipo);
            tipo = View.findViewById(R.id.TipoEquipo);
            marca = View.findViewById(R.id.marcaEquipo);
            serie = View.findViewById(R.id.numSerie);
            nomarea = View.findViewById(R.id.nomArea);
            fecha = View.findViewById(R.id.Fecha);
            propietario = View.findViewById(R.id.propietarioEquipo);
            btnModificarDatos = View.findViewById(R.id.ModificarDatos);
            btnEliminar =  View.findViewById(R.id.eliminarEquipo);
/*
            btnModificarDatos.setOnClickListener(new View.OnClickListener() {o
                @Override
                public void onClick(android.view.View v) {
                    Context context = View.getContext();
                    Intent i = new Intent(context, ModificarInformacion.class);
                    i.putExtra("n_serie", lista.get(getAdapterPosition()).getN_serie());
                    context.startActivity(i);
                }
            });
*/


        }

    }
}
