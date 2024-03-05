package com.example.inventario_puebloviejo.Equipo;

import org.json.JSONException;
import org.json.JSONObject;

public class Equipo {
    private String id = null;
    private String n_serie;
    private String tipo;
    private String estatus;
    private String marca;
    private String propietario;
    private String area;
    private String fecha_ini;

    public Equipo() {
    }

    public Equipo(String n_serie, String tipo, String estatus, String marca, String propietario, String area, String fecha_ini) {
        this.n_serie = n_serie;
        this.tipo = tipo;
        this.estatus = estatus;
        this.marca = marca;
        this.propietario = propietario;
        this.area = area;
        this.fecha_ini = fecha_ini;
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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getFecha_ini() {
        return fecha_ini;
    }

    public void setFecha_ini(String fecha_ini) {
        this.fecha_ini = fecha_ini;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }
    public JSONObject toJson(){
        JSONObject jsonBody = new JSONObject();

        try{
            if(id != null){
                jsonBody.put("id", getId());
            }

            jsonBody.put("n_serie", getN_serie());
            jsonBody.put("tipo", getTipo());
            jsonBody.put("estatus", getEstatus());
            jsonBody.put("marca", getMarca());
            jsonBody.put("propietario", getPropietario());
            jsonBody.put("area", getArea());
            jsonBody.put("fecha_ini", getFecha_ini());
        }catch(JSONException e){
            e.printStackTrace();
        }

        return jsonBody;
    }

}
