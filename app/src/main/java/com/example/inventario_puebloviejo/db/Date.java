package com.example.inventario_puebloviejo.db;

public class Date {
    private int id;

    private String nombre;
    private String puesto;
    private String correo;
    private String password;
    private String telefono;

    private String nombre_area;
    private Boolean estatus;
    private String n_serie;
    private  String marca;
    private String propietario;
    private String fecha_ini;

    private String fecha_llegada;
    private String descripcion;
    private String entrega;

    private String tipo;
    private String area;

    public Date(){
        this.id = id;
        this.nombre = nombre;
        this.puesto = puesto;
        this.correo = correo;
        this.password = password;
        this.telefono = telefono;

        this.nombre_area = nombre_area;
        this.estatus = estatus;
        this.n_serie = n_serie;
        this.marca = marca;
        this.propietario = propietario;
        this.fecha_ini = fecha_ini;

        this.fecha_llegada = fecha_llegada;
        this.descripcion = descripcion;
        this.entrega = entrega;

        this.tipo = tipo;
        this.area = area;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getPuesto(){
        return puesto;
    }

    public void setPuesto(String puesto){
        this.puesto = puesto;
    }

    public String getCorreo(){
        return correo;
    }
    public void setCorreo(String correo){
        this.correo = correo;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public String getTelefono(){
        return telefono;
    }
    public void setTelefono(String telefono){
        this.telefono = telefono;
    }

    public String getNombre_area(){
        return nombre_area;
    }
    public void setNombre_area(String nombre_area){
        this.nombre_area = nombre_area;
    }

    public Boolean getEstatus(){
        return estatus;
    }
    public void setEstatus(Boolean estatus){
        this.estatus = estatus;
    }

    public String getN_serie(){
        return n_serie;
    }
    public void setN_serie(String n_serie){
        this.n_serie = n_serie;
    }

    public String getMarca(){
        return marca;
    }

    public void setMarca(String marca){
        this.marca = marca;
    }

    private String getPropietario(){
        return propietario;
    }
    private void setPropietario(String propietario){
        this.propietario = propietario;
    }

    private String getFecha_ini(){
        return fecha_ini;
    }
    private void setFecha_ini(String fecha_ini){
        this.fecha_ini = fecha_ini;
    }

    private String getFecha_llegada(){
        return fecha_llegada;
    }

    private void setFecha_llegada(String fecha_llegada){
        this.fecha_llegada = fecha_llegada;
    }

    private String getDescripcion(){
        return descripcion;
    }
    private void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    private String getEntrega(){
        return entrega;
    }

    private void setEntrega(){
        this.entrega = entrega;
    }

    private String getTipo(){
        return tipo;
    }
    private void setTipo(String tipo){
        this.tipo = tipo;
    }

    private String getArea(){
        return area;
    }
    private void setArea(String area){
        this.area = area;
    }

}
