package com.example.movimaps.sql.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.movimaps.sql.database.ChoferTransporte;
import com.example.movimaps.sql.entity.Direccion;

@Dao
public interface ChoferTransporteDao {
    @Insert
    long insertar(ChoferTransporte choferTransporte);     // Devuelve el id de insercion

    @Update
    int actualizar(ChoferTransporte choferTransporte);    // Devuelve numero de filas afectadas

    @Delete
    int delete(ChoferTransporte choferTransporte);    // Devuelve numero de filas afectadas

    @Query("SELECT * FROM Chofer_Transporte WHERE id_chofer = :id")
    ChoferTransporte getPorId(int id);

}
