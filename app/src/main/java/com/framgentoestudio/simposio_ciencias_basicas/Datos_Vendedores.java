package com.framgentoestudio.simposio_ciencias_basicas;

import java.util.ArrayList;

public class Datos_Vendedores {
    private String UID;
    private String Nombre;
    private String Numero;
    private String Correo;
    private int NControl;
    private int NIP;

    public Datos_Vendedores() {
    }

    public Datos_Vendedores(String uid, String nombre, String numero, String correo, int ncontrol, int nip) {
        UID = uid;
        Nombre = nombre;
        Numero = numero;
        Correo = correo;
        NControl = ncontrol;
        NIP = nip;
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

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String numero) {
        Numero = numero;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public int getNControl() {
        return NControl;
    }

    public void setNControl(int NControl) {
        this.NControl = NControl;
    }

    public int getNIP() {
        return NIP;
    }

    public void setNIP(int NIP) {
        this.NIP = NIP;
    }
}
