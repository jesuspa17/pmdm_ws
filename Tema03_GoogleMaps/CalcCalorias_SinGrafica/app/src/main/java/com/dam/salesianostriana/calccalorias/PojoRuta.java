package com.dam.salesianostriana.calccalorias;

/**
 * Created by Jesus Pallares on 23/01/2016.
 */
public class PojoRuta {

    private String fecha;
    private double distancia;
    private double calorias;
    private String dia_semana;
    private String duracion;

    public PojoRuta() {
    }

    public PojoRuta(String num_ruta, double distancia, double calorias) {
        this.fecha = num_ruta;
        this.distancia = distancia;
        this.calorias = calorias;
    }

    public PojoRuta(String num_ruta, double distancia, double calorias, String dia_semana) {
        this.fecha = num_ruta;
        this.distancia = distancia;
        this.calorias = calorias;
        this.dia_semana = dia_semana;
    }

    public PojoRuta(String fecha, double distancia, double calorias, String dia_semana, String duracion) {
        this.fecha = fecha;
        this.distancia = distancia;
        this.calorias = calorias;
        this.dia_semana = dia_semana;
        this.duracion = duracion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String num_ruta) {
        this.fecha = num_ruta;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public double getCalorias() {
        return calorias;
    }

    public void setCalorias(double calorias) {
        this.calorias = calorias;
    }

    public String getDia_semana() {
        return dia_semana;
    }

    public void setDia_semana(String dia_semana) {
        this.dia_semana = dia_semana;
    }


    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }
}
