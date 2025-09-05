package com.example.movimaps.sql.entity;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(
        tableName = "Registro_Trayecto_Usuario",
        foreignKeys = {
                @ForeignKey(entity = Parada.class, parentColumns = "id_parada", childColumns = "Parada_id_parada_inicio", onDelete = CASCADE),
                @ForeignKey(entity = Parada.class, parentColumns = "id_parada", childColumns = "Parada_id_parada_fin", onDelete = CASCADE),
                @ForeignKey(entity = Ruta.class, parentColumns = "id_ruta", childColumns = "Ruta_id_ruta", onDelete = CASCADE),
                @ForeignKey(entity = User.class, parentColumns = "id_user", childColumns = "User_id_user", onDelete = CASCADE)
        },
        indices = {
                @Index(value = "Parada_id_parada_inicio"),
                @Index(value = "Parada_id_parada_fin"),
                @Index(value = "Ruta_id_ruta"),
                @Index(value = "User_id_user")
        }
)
public class RegistroTrayectoUsuario {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_registro")
    private int idRegistro;

    @ColumnInfo(name = "fecha_hora_inicio")
    private Date fechaHoraInicio;

    @ColumnInfo(name = "fecha_hora_fin")
    private Date fechaHoraFin;

    @ColumnInfo(name = "duracion_estimada")
    private int duracionEstimada;

    @ColumnInfo(name = "duracion_real")
    private int duracionReal;

    @ColumnInfo(name = "coste_estimado")
    private double costeEstimado;

    @ColumnInfo(name = "coste_real")
    private double costeReal;

    // FK
    @ColumnInfo(name = "Parada_id_parada_inicio")
    private int paradaIdInicial;

    @ColumnInfo(name = "Parada_id_parada_fin")
    private int paradaIdFinal;

    @ColumnInfo(name = "Ruta_id_ruta")
    private int rutaId;

    @ColumnInfo(name = "User_id_user")
    private int userId;

}
