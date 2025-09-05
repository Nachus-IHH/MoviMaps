package com.example.movimaps.sql.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.movimaps.sql.entity.Ruta;

@Dao
public interface RutaDao {
    @Insert
    long insertar(Ruta ruta);

    @Update
    int actualizar(Ruta ruta);

    @Delete
    int delete(Ruta ruta);

    @Query("SELECT * FROM Ruta WHERE id_ruta = :id")
    Ruta getPorId(int id);
}
