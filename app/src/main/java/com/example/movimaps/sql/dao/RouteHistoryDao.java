package com.example.movimaps.sql.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.movimaps.sql.database.BusStop;
import com.example.movimaps.sql.database.RouteHistory;

public interface RouteHistoryDao {
    @Insert
    long insertar(RouteHistory routeHistory);     // Devuelve el id de insercion

    @Update
    int actualizar(RouteHistory routeHistory);    // Devuelve numero de filas afectadas

    @Delete
    int delete(RouteHistory routeHistory);    // Devuelve numero de filas afectadas

    @Query("SELECT * FROM route_history WHERE id = :id")
    RouteHistory getPorId(int id);
}
