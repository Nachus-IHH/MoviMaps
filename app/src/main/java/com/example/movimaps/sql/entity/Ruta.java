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
@Entity(tableName = "Ruta")
public class Ruta {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_ruta")
    private int idRuta;

    @NonNull
    @ColumnInfo(name = "nombre_ruta")
    private String nombreRuta;

    @ColumnInfo(name = "url_img_transporte")
    private String urlImgTransporte;

    @ColumnInfo(name = "no_ruta")
    private int noRuta;

    private String color;

    @ColumnInfo(name = "frecuencia_promedio")
    private int frecuenciaPromedio;     // frecuencia en minutos

}
