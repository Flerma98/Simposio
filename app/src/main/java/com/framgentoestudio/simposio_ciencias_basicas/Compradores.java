package com.framgentoestudio.simposio_ciencias_basicas;

public class Compradores {
    private String UID;
    private String Nombre;
    private String Numero_Control;
    private String Talla;
    private String Semestre;
    private String Carrera;
    private String Taller;
    private String Importes;
    private String Fechas;
    private String Total;
    private String Vendedora;
    private String Folio;
    private String UIDVendedores;

    public Compradores() {
    }

    public Compradores(String uid, String nombre, String numero_Control, String talla, String semestre, String carrera, String taller, String importes, String fechas, String total, String vendedora, String folio, String uidVendedores) {
        UID = uid;
        Nombre = nombre;
        Numero_Control = numero_Control;
        Talla= talla;
        Semestre = semestre;
        Carrera = carrera;
        Taller = taller;
        Importes = importes;
        Fechas = fechas;
        Total = total;
        Vendedora = vendedora;
        Folio = folio;
        UIDVendedores = uidVendedores;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getNumero_Control() {
        return Numero_Control;
    }

    public void setNumero_Control(String numero_Control) {
        Numero_Control = numero_Control;
    }

    public String getTalla() {
        return Talla;
    }

    public void setTalla(String talla) {
        Talla = talla;
    }

    public String getSemestre() {
        return Semestre;
    }

    public void setSemestre(String semestre) {
        Semestre = semestre;
    }

    public String getCarrera() {
        return Carrera;
    }

    public void setCarrera(String carrera) {
        Carrera = carrera;
    }

    public String getTaller() {
        return Taller;
    }

    public void setTaller(String taller) {
        Taller = taller;
    }

    public String getImportes() {
        return Importes;
    }

    public void setImportes(String importes) {
        Importes = importes;
    }

    public String getFechas() {
        return Fechas;
    }

    public void setFechas(String fechas) {
        Fechas = fechas;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getVendedora() {
        return Vendedora;
    }

    public void setVendedora(String vendedora) {
        Vendedora = vendedora;
    }

    public String getFolio() {
        return Folio;
    }

    public void setFolio(String folio) {
        Folio = folio;
    }

    public String getUIDVendedores() {
        return UIDVendedores;
    }

    public void setUIDVendedores(String UIDVendedores) {
        this.UIDVendedores = UIDVendedores;
    }
}
