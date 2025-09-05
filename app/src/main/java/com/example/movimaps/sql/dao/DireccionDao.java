package com.example.movimaps.sql.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.movimaps.sql.entity.Direccion;

@Dao
public interface DireccionDao {
    @Insert
    long insertar(Direccion direccion);     // Devuelve el id de insercion

    @Update
    int actualizar(Direccion direccion);    // Devuelve numero de filas afectadas

    @Delete
    int delete(Direccion direccion);    // Devuelve numero de filas afectadas

    @Query("SELECT * FROM Direccion WHERE id_direccion = :id")
    Direccion getPorId(int id);

}
