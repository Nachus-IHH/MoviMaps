package com.example.movimaps.pojo;

import com.example.movimaps.sql.entity.Direccion;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
    Este es un POJO (Plain Old Java Object) para usar en el formulario de MiPymes
 */
@Data
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
    private String correoContacto;

    // Datos de Direccion
    private Direccion direccion;
    /*
    private String calle;
    private String numero;
    private String codigoPostal;
    private float lat;
    private float lng;
    private long idCiudad;
    */

}
