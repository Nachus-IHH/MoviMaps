package com.example.movimaps.sql.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.movimaps.sql.database.BusStop;
import com.example.movimaps.sql.database.DriverRoute;

public interface BusStopDao {
    @Insert
    long insertar(BusStop busStop);     // Devuelve el id de insercion

    @Update
    int actualizar(BusStop busStop);    // Devuelve numero de filas afectadas

    @Delete
    int delete(BusStop busStop);    // Devuelve numero de filas afectadas

    @Query("SELECT * FROM bus_stops WHERE id = :id")
    BusStop getPorId(int id);

}
