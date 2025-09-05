package com.example.movimaps.sql.entity;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(
        tableName = "Ruta_has_Parada",
        primaryKeys = { "Ruta_id_ruta", "Parada_id_parada" },
        foreignKeys = {
                @ForeignKey(
                        entity = Ruta.class,
                        parentColumns = "id_ruta",
                        childColumns = "Ruta_id_ruta",
                        onDelete = CASCADE
                ),
                @ForeignKey(
                        entity = Parada.class,
                        parentColumns = "id_parada",
                        childColumns = "Parada_id_parada",
                        onDelete = CASCADE
                )
        },
        indices = {
                @Index(value = "Ruta_id_ruta"),
                @Index(value = "Parada_id_parada")
        }
)
public class RutaHasParada  {
    @ColumnInfo(name = "Ruta_id_ruta")
    private int idRuta;

    @ColumnInfo(name = "Parada_id_parada")
    private int idParada;

    @ColumnInfo(name = "no_validaciones")
    private int noValidaciones;

    @ColumnInfo(name = "tiempo_espera_aprox")
    private String tiempoEsperaAprox;   // Time

    @NonNull
    private int orden;

    @ColumnInfo(name = "tiempo_viaje_desde_anterior")
    private String tiempoViajeDesdeAnterior;    // Time

    @ColumnInfo(name = "distancia_km")
    private float distanciaKm;

    private String estado;  // 'ACTIVA', 'INACTIVA'
}
