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
@Entity(tableName = "Parada")
public class Parada {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_parada")
    private int idParada;

    @NonNull
    private double lat;

    @NonNull
    private double lng;

    private boolean accesibilidad;

    @ColumnInfo(name = "url_img_parada")
    private String urlImgParada;

    @ColumnInfo(name = "niv_seg_mat")
    private int nivSegMat;

    @ColumnInfo(name = "niv_seg_ves")
    private int nivSegVes;

    @ColumnInfo(name = "niv_seg_noc")
    private int nivSegNoc;
}
