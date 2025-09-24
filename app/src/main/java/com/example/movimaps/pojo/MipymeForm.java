package com.example.movimaps.pojo;

import com.example.movimaps.sql.entity.Direccion;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
    Este es un POJO (Plain Old Java Object) para usar en el formulario de MiPymes
 */

@NoArgsConstructor
public class MipymeForm {
    // Datos de MiPyme
    private String mipyme;
    private String tipoGiro;

    private String info;

    private String urlLogo;


    // Datos de Contacto
    private String nombreContacto;

    private String telefonoContacto;

    public String getCorreoContacto() {
        return correoContacto;
    }

    public void setCorreoContacto(String correoContacto) {
        this.correoContacto = correoContacto;
    }

    private String correoContacto;

    // Datos de Direccion
    private Direccion direccion;

    public String getNombreContacto() {
        return nombreContacto;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getMipyme() {
        return mipyme;
    }

    public void setMipyme(String mipyme) {
        this.mipyme = mipyme;
    }

    public String getTipoGiro() {
        return tipoGiro;
    }

    public void setTipoGiro(String tipoGiro) {
        this.tipoGiro = tipoGiro;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }


    /*
    private String calle;
    private String numero;
    private String codigoPostal;
    private float lat;
    private float lng;
    private long idCiudad;
    */

}
