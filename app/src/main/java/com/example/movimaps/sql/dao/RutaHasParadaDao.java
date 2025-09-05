package com.example.movimaps.sql.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.movimaps.sql.entity.RutaHasParada;

@Dao
public interface RutaHasParadaDao {
    @Insert
    long insertar(RutaHasParada rutaHasParada);

    @Update
    int actualizar(RutaHasParada rutaHasParada);

    @Delete
    int delete(RutaHasParada rutaHasParada);

    @Query("SELECT * FROM Ruta_has_Parada WHERE Ruta_id_ruta = :idRuta AND Parada_id_parada = :idParada")
    RutaHasParada getPorIdCompuesta(int idRuta, int idParada);
}
