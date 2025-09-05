package com.example.movimaps.sql.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "Direccion")
public class Direccion {
    // Attributes
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_direccion")
    private int idDireccion;

    @NonNull
    private String calle;

    private String numero;

    @ColumnInfo(name = "codigo_postal")
    private String codigoPostal;

    @NonNull
    private double lat;

    @NonNull
    private double lng;

    // Llave foranea a 'Ciudad'
    @ColumnInfo(name = "Ciudad_id_ciudad")
    private int idCiudad;

}
