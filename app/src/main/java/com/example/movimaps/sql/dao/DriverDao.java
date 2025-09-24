package com.example.movimaps.sql.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface DriverDao {

    // NUEVO: Operaciones CRUD para choferes
    @Insert
    long insertDriver(Driver driver);

    @Update
    void updateDriver(Driver driver);

    @Delete
    void deleteDriver(Driver driver);

    @Query("SELECT * FROM drivers WHERE isActive = 1 ORDER BY name ASC")
    List<Driver> getAllActiveDrivers();

    @Query("SELECT * FROM drivers WHERE id = :driverId")
    Driver getDriverById(int driverId);

    @Query("SELECT * FROM drivers WHERE driverId = :driverId")
    Driver getDriverByDriverId(String driverId);

    @Query("SELECT COUNT(*) FROM drivers WHERE driverId = :driverId")
    int checkDriverIdExists(String driverId);

    // NUEVO: Operaciones para rutas de choferes
    @Insert
    long insertDriverRoute(DriverRoute driverRoute);

    @Update
    void updateDriverRoute(DriverRoute driverRoute);

    @Delete
    void deleteDriverRoute(DriverRoute driverRoute);

    @Query("SELECT * FROM driver_routes WHERE driverId = :driverId AND isActive = 1")
    List<DriverRoute> getRoutesByDriverId(int driverId);

    @Query("SELECT * FROM driver_routes WHERE isActive = 1 ORDER BY routeName ASC")
    List<DriverRoute> getAllActiveRoutes();

    @Query("SELECT * FROM driver_routes WHERE id = :routeId")
    DriverRoute getRouteById(int routeId);

    // NUEVO: Operaciones para paradas
    @Insert
    long insertBusStop(BusStop busStop);

    @Update
    void updateBusStop(BusStop busStop);

    @Delete
    void deleteBusStop(BusStop busStop);

    @Query("SELECT * FROM bus_stops WHERE routeId = :routeId AND isActive = 1 ORDER BY stopOrder ASC")
    List<BusStop> getStopsByRouteId(int routeId);

    @Query("SELECT * FROM bus_stops WHERE isActive = 1 ORDER BY stopName ASC")
    List<BusStop> getAllActiveStops();

    @Query("SELECT * FROM bus_stops WHERE id = :stopId")
    BusStop getStopById(int stopId);

    // NUEVO: Consultas combinadas
    @Query("SELECT d.*, COUNT(dr.id) as routeCount FROM drivers d " +
            "LEFT JOIN driver_routes dr ON d.id = dr.driverId " +
            "WHERE d.isActive = 1 GROUP BY d.id ORDER BY d.name ASC")
    List<Driver> getDriversWithRouteCount();

    @Query("SELECT dr.*, d.name as driverName, d.unit as driverUnit FROM driver_routes dr " +
            "INNER JOIN drivers d ON dr.driverId = d.id " +
            "WHERE dr.isActive = 1 AND d.isActive = 1 ORDER BY dr.routeName ASC")
    List<DriverRoute> getRoutesWithDriverInfo();
}
