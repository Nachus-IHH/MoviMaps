package com.example.movimaps.sql.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.movimaps.sql.entity.Parada;

@Dao
public interface ParadaDao {
    @Insert
    long insertar(Parada parada);

    @Update
    int actualizar(Parada parada);

    @Delete
    int delete(Parada parada);

    @Query("SELECT * FROM Parada WHERE id_parada = :id")
    Parada getPorId(int id);
}
