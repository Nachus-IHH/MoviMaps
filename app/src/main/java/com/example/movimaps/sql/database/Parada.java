package com.example.movimaps.sql.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "Parada")
public class Parada {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_parada")
    private int idParada;

    @ColumnInfo(name = "nombre_parada")
    private String nombreParada;

    private double lat; // DECIMAL(10,6)
    private double lng; // DECIMAL(10,6)
    private boolean accesibilidad; // TINYINT(1)

    @ColumnInfo(name = "img_parada")
    private String imgParada;

    @ColumnInfo(name = "niv_seg_mat")
    private int nivSegMat; // TINYINT - Nivel seguridad matutino

    @ColumnInfo(name = "niv_seg_ves")
    private int nivSegVes; // TINYINT - Nivel seguridad vespertino

    @ColumnInfo(name = "niv_seg_noc")
    private int nivSegNoc; // TINYINT - Nivel seguridad nocturno

    // Constructor
    public Parada() {}

    public Parada(String nombreParada, double lat, double lng, boolean accesibilidad,
                  String imgParada, int nivSegMat, int nivSegVes, int nivSegNoc) {
        this.nombreParada = nombreParada;
        this.lat = lat;
        this.lng = lng;
        this.accesibilidad = accesibilidad;
        this.imgParada = imgParada;
        this.nivSegMat = nivSegMat;
        this.nivSegVes = nivSegVes;
        this.nivSegNoc = nivSegNoc;
    }

    // Getters y Setters
    public int getIdParada() { return idParada; }
    public void setIdParada(int idParada) { this.idParada = idParada; }

    public String getNombreParada() { return nombreParada; }
    public void setNombreParada(String nombreParada) { this.nombreParada = nombreParada; }

    public double getLat() { return lat; }
    public void setLat(double lat) { this.lat = lat; }

    public double getLng() { return lng; }
    public void setLng(double lng) { this.lng = lng; }

    public boolean isAccesibilidad() { return accesibilidad; }
    public void setAccesibilidad(boolean accesibilidad) { this.accesibilidad = accesibilidad; }

    public String getImgParada() { return imgParada; }
    public void setImgParada(String imgParada) { this.imgParada = imgParada; }

    public int getNivSegMat() { return nivSegMat; }
    public void setNivSegMat(int nivSegMat) { this.nivSegMat = nivSegMat; }

    public int getNivSegVes() { return nivSegVes; }
    public void setNivSegVes(int nivSegVes) { this.nivSegVes = nivSegVes; }

    public int getNivSegNoc() { return nivSegNoc; }
    public void setNivSegNoc(int nivSegNoc) { this.nivSegNoc = nivSegNoc; }
}
