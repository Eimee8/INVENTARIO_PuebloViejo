package com.example.inventario_puebloviejo.Mantenimiento;

import org.json.JSONException;
import org.json.JSONObject;

public class Mantenimiento {
    private String n_serie;
    private String tipo ;
    private String estatus;
    private String descripcion;
    private String fecha_llegada;
    private String fecha_entrega;

    public Mantenimiento() {
    }

    public Mantenimiento(String n_serie, String tipo, String estatus, String descripcion, String fecha_llegada, String fecha_entrega) {
        this.n_serie = n_serie;
        this.tipo = tipo;
        this.estatus = estatus;
        this.descripcion = descripcion;
        this.fecha_llegada = fecha_llegada;
        this.fecha_entrega = fecha_entrega;
    }

    public String getN_serie() {
        return n_serie;
    }

    public void setN_serie(String n_serie) {
        this.n_serie = n_serie;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha_llegada() {
        return fecha_llegada;
    }

    public void setFecha_llegada(String fecha_llegada) {
        this.fecha_llegada = fecha_llegada;
    }

    public String getFecha_entrega() {
        return fecha_entrega;
    }

    public void setFecha_entrega(String fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }

    public JSONObject toJson(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("n_serie", getN_serie());
            jsonObject.put("tipo", getTipo());
            jsonObject.put("estatus", getEstatus());
            jsonObject.put("descripcion",getDescripcion());
            jsonObject.put("fecha_llegada", getFecha_llegada());
            jsonObject.put("fecha_entrega", getFecha_entrega());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return jsonObject;
    }
}
