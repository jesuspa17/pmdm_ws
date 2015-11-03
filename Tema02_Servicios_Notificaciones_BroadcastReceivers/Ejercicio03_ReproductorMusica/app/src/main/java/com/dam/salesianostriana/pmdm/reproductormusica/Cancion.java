package com.dam.salesianostriana.pmdm.reproductormusica;

/**
 * Created by jpallares on 28/10/2015.
 */
public class Cancion {

    private String grupo;
    private String titutlo;
    private String duracion;
    private int ruta_cancion;

    public Cancion(String grupo, String titutlo, String duracion, int ruta_cancion) {
        this.grupo = grupo;
        this.titutlo = titutlo;
        this.duracion = duracion;
        this.ruta_cancion =ruta_cancion;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getTitutlo() {
        return titutlo;
    }

    public void setTitutlo(String titutlo) {
        this.titutlo = titutlo;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public int getRuta_cancion() {
        return ruta_cancion;
    }

    public void setRuta_cancion(int ruta_cancion) {
        this.ruta_cancion = ruta_cancion;
    }
}
