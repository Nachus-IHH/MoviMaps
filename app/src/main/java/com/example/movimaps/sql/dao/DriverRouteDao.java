package com.example.movimaps.sql.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.movimaps.sql.database.DriverRoute;
import com.example.movimaps.sql.database.LocationHistory;
@Dao

public interface DriverRouteDao {
    @Insert
    long insertar(DriverRoute driverRoute);     // Devuelve el id de insercion

    @Update
    int actualizar(DriverRoute driverRoute);    // Devuelve numero de filas afectadas

    @Delete
    int delete(DriverRoute driverRoute);    // Devuelve numero de filas afectadas

    @Query("SELECT * FROM driver_routes WHERE id = :id")
    DriverRoute getPorId(int id);

}
