package com.framgentoestudio.simposio_ciencias_basicas;

public class Asistencia {
    private String Estado;
    private String Datos;

    public Asistencia() {
    }

    public Asistencia(String estado, String datos) {
        Estado = estado;
        Datos = datos;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getDatos() {
        return Datos;
    }

    public void setDatos(String datos) {
        Datos = datos;
    }
}
