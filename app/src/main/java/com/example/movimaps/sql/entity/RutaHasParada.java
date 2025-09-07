package com.example.movimaps.sql.entity;
import androidx.room.Entity;
import androidx.room.ColumnInfo;
import androidx.room.ForeignKey;

import com.example.movimaps.sql.entity.Parada;
import com.example.movimaps.sql.entity.Ruta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "Ruta_has_Parada",
        primaryKeys = {"Ruta_id_ruta", "Parada_id_parada"},
        foreignKeys = {
            @ForeignKey(
                    entity = Ruta.class,
                    parentColumns = "id_ruta",
                    childColumns = "Ruta_id_ruta",
                    onDelete = ForeignKey.CASCADE
            ),
            @ForeignKey(
                    entity = Parada.class,
                    parentColumns = "id_parada",
                    childColumns = "Parada_id_parada",
                    onDelete = ForeignKey.CASCADE
            )
        }
)
public class RutaHasParada {
    @ColumnInfo(name = "Ruta_id_ruta")
    private int rutaIdRuta; // FK
    @ColumnInfo(name = "Parada_id_parada")
    private int paradaIdParada;
    // FK
    @ColumnInfo(name = "no_validaciones")
    private int noValidaciones;
    private String tipo; // ENUM como String
    @ColumnInfo(name = "tiempo_espera_aprox")
    private String tiempoEsperaAprox;
    // TIME como String private int orden;
    @ColumnInfo(name = "tiempo_viaje_desde_anterior")
    private String tiempoViajeDesdeAnterior; // TIME como String
    @ColumnInfo(name = "distancia_km")
    private double distanciaKm;
    // DECIMAL(5,2)
    // Constructor

}