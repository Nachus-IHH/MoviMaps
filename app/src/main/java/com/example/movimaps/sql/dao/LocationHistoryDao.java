package com.example.movimaps.sql.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.movimaps.sql.database.ChoferTransporte;
import com.example.movimaps.sql.database.LocationHistory;

public interface LocationHistoryDao {
    @Insert
    long insertar(LocationHistory locationHistory);     // Devuelve el id de insercion

    @Update
    int actualizar(LocationHistory locationHistory);    // Devuelve numero de filas afectadas

    @Delete
    int delete(LocationHistory locationHistory);    // Devuelve numero de filas afectadas

    @Query("SELECT * FROM location_history WHERE id = :id")
    LocationHistory getPorId(int id);

}
