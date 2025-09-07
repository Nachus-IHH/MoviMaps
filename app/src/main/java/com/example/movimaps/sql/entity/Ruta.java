package com.example.movimaps.sql.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Ruta")
public class Ruta {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_ruta")
    private int idRuta;

    @ColumnInfo(name = "nombre_ruta")
    private String nombreRuta;

    @ColumnInfo(name = "img_transporte")
    private String imgTransporte;

    @ColumnInfo(name = "no_ruta")
    private int noRuta;

    private String color;

    @ColumnInfo(name = "frecuencia_promedio")
    private int frecuenciaPromedio; // TINYINT

    @ColumnInfo(name = "tipo_transporte")   // 🚀 NUEVO campo
    private String tipoTransporte;          // Ej: "bus", "metro", "microbus"

    // Constructor vacío requerido por Room
    public Ruta() {}

    public Ruta(String nombreRuta, String imgTransporte, int noRuta,
                String color, int frecuenciaPromedio, String tipoTransporte) {
        this.nombreRuta = nombreRuta;
        this.imgTransporte = imgTransporte;
        this.noRuta = noRuta;
        this.color = color;
        this.frecuenciaPromedio = frecuenciaPromedio;
        this.tipoTransporte = tipoTransporte;
    }

    // Getters y Setters
    public int getIdRuta() { return idRuta; }
    public void setIdRuta(int idRuta) { this.idRuta = idRuta; }

    public String getNombreRuta() { return nombreRuta; }
    public void setNombreRuta(String nombreRuta) { this.nombreRuta = nombreRuta; }

    public String getImgTransporte() { return imgTransporte; }
    public void setImgTransporte(String imgTransporte) { this.imgTransporte = imgTransporte; }

    public int getNoRuta() { return noRuta; }
    public void setNoRuta(int noRuta) { this.noRuta = noRuta; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public int getFrecuenciaPromedio() { return frecuenciaPromedio; }
    public void setFrecuenciaPromedio(int frecuenciaPromedio) { this.frecuenciaPromedio = frecuenciaPromedio; }

    public String getTipoTransporte() { return tipoTransporte; }
    public void setTipoTransporte(String tipoTransporte) { this.tipoTransporte = tipoTransporte; }

}
