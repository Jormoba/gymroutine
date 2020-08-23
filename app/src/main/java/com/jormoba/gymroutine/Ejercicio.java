package com.jormoba.gymroutine;

public class Ejercicio {

    private String e_ID;
    private String nombre;
    private int series;
    private int repeticiones;
    private int peso;
    private String comentarios;
    private boolean completado;

    public Ejercicio() {

    }

    public Ejercicio(String e_ID, String nombre, int series, int repeticiones, int peso, String comentarios, boolean completado) {
        this.e_ID = e_ID;
        this.nombre = nombre;
        this.series = series;
        this.repeticiones = repeticiones;
        this.peso = peso;
        this.comentarios = comentarios;
        this.completado = completado;
    }

    public String getE_ID() {
        return e_ID;
    }

    public String getNombre() {
        return nombre;
    }

    public int getSeries() {
        return series;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public int getPeso() {
        return peso;
    }

    public String getComentarios() {
        return comentarios;
    }

    public boolean isCompletado() {
        return completado;
    }

    public void setE_ID(String e_ID) {
        this.e_ID = e_ID;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }
}
