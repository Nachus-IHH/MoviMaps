package com.example.movimaps.sql.database;

import androidx.room.Entity;
import androidx.room.ColumnInfo;
import androidx.room.ForeignKey;

@Entity(tableName = "Ruta_has_Parada",
        primaryKeys = {"Ruta_id_ruta", "Parada_id_parada"},
        foreignKeys = {
                @ForeignKey(entity = Ruta.class,
                        parentColumns = "id_ruta",
                        childColumns = "Ruta_id_ruta",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Parada.class,
                        parentColumns = "id_parada",
                        childColumns = "Parada_id_parada",
                        onDelete = ForeignKey.CASCADE)
        })
public class RutaHasParada {
    @ColumnInfo(name = "Ruta_id_ruta")
    private int rutaIdRuta; // FK

    @ColumnInfo(name = "Parada_id_parada")
    private int paradaIdParada; // FK

    @ColumnInfo(name = "no_validaciones")
    private int noValidaciones;

    private String tipo; // ENUM como String

    @ColumnInfo(name = "tiempo_espera_aprox")
    private String tiempoEsperaAprox; // TIME como String

    private int orden;

    @ColumnInfo(name = "tiempo_viaje_desde_anterior")
    private String tiempoViajeDesdeAnterior; // TIME como String

    @ColumnInfo(name = "distancia_km")
    private double distanciaKm; // DECIMAL(5,2)

    // Constructor
    public RutaHasParada() {}

    public RutaHasParada(int rutaIdRuta, int paradaIdParada, int noValidaciones, String tipo,
                         String tiempoEsperaAprox, int orden, String tiempoViajeDesdeAnterior,
                         double distanciaKm) {
        this.rutaIdRuta = rutaIdRuta;
        this.paradaIdParada = paradaIdParada;
        this.noValidaciones = noValidaciones;
        this.tipo = tipo;
        this.tiempoEsperaAprox = tiempoEsperaAprox;
        this.orden = orden;
        this.tiempoViajeDesdeAnterior = tiempoViajeDesdeAnterior;
        this.distanciaKm = distanciaKm;
    }

    // Getters y Setters
    public int getRutaIdRuta() { return rutaIdRuta; }
    public void setRutaIdRuta(int rutaIdRuta) { this.rutaIdRuta = rutaIdRuta; }

    public int getParadaIdParada() { return paradaIdParada; }
    public void setParadaIdParada(int paradaIdParada) { this.paradaIdParada = paradaIdParada; }

    public int getNoValidaciones() { return noValidaciones; }
    public void setNoValidaciones(int noValidaciones) { this.noValidaciones = noValidaciones; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getTiempoEsperaAprox() { return tiempoEsperaAprox; }
    public void setTiempoEsperaAprox(String tiempoEsperaAprox) { this.tiempoEsperaAprox = tiempoEsperaAprox; }

    public int getOrden() { return orden; }
    public void setOrden(int orden) { this.orden = orden; }

    public String getTiempoViajeDesdeAnterior() { return tiempoViajeDesdeAnterior; }
    public void setTiempoViajeDesdeAnterior(String tiempoViajeDesdeAnterior) { this.tiempoViajeDesdeAnterior = tiempoViajeDesdeAnterior; }

    public double getDistanciaKm() { return distanciaKm; }
    public void setDistanciaKm(double distanciaKm) { this.distanciaKm = distanciaKm; }
}
