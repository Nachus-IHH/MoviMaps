package com.example.movimaps.pojo;

import com.example.movimaps.sql.entity.Direccion;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
    Este es un POJO (Plain Old Java Object) para usar en el formulario de Eventos
 */
@Data
@NoArgsConstructor
public class EventoForm {
    // Attributes
    private String nombre;
    private String fechaHoraInicio;
    private String fechaHoraFin;
    private float coste;
    private String categoria;
    private String descripcion;
    private String contacto;
    private Direccion direccion;

}
