package com.example.movimaps.sql.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "driver_routes",
        foreignKeys = @ForeignKey(entity = Driver.class,
                parentColumns = "id",
                childColumns = "driverId",
                onDelete = ForeignKey.CASCADE))
public class DriverRoute {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int driverId; // FK a Driver
    private String routeName;
    private String routeDescription;
    private String startLocation;
    private String endLocation;
    private double startLatitude;
    private double startLongitude;
    private double endLatitude;
    private double endLongitude;
    private String schedule; // Horarios de operación
    private double fare; // Tarifa
    private boolean isActive;
    private long createdAt;

    // NUEVO: Constructor para rutas de chofer
    public DriverRoute() {
        this.createdAt = System.currentTimeMillis();
        this.isActive = true;
    }

    public DriverRoute(int driverId, String routeName, String routeDescription,
                       String startLocation, String endLocation,
                       double startLatitude, double startLongitude,
                       double endLatitude, double endLongitude,
                       String schedule, double fare) {
        this.driverId = driverId;
        this.routeName = routeName;
        this.routeDescription = routeDescription;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.endLatitude = endLatitude;
        this.endLongitude = endLongitude;
        this.schedule = schedule;
        this.fare = fare;
        this.createdAt = System.currentTimeMillis();
        this.isActive = true;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getDriverId() { return driverId; }
    public void setDriverId(int driverId) { this.driverId = driverId; }

    public String getRouteName() { return routeName; }
    public void setRouteName(String routeName) { this.routeName = routeName; }

    public String getRouteDescription() { return routeDescription; }
    public void setRouteDescription(String routeDescription) { this.routeDescription = routeDescription; }

    public String getStartLocation() { return startLocation; }
    public void setStartLocation(String startLocation) { this.startLocation = startLocation; }

    public String getEndLocation() { return endLocation; }
    public void setEndLocation(String endLocation) { this.endLocation = endLocation; }

    public double getStartLatitude() { return startLatitude; }
    public void setStartLatitude(double startLatitude) { this.startLatitude = startLatitude; }

    public double getStartLongitude() { return startLongitude; }
    public void setStartLongitude(double startLongitude) { this.startLongitude = startLongitude; }

    public double getEndLatitude() { return endLatitude; }
    public void setEndLatitude(double endLatitude) { this.endLatitude = endLatitude; }

    public double getEndLongitude() { return endLongitude; }
    public void setEndLongitude(double endLongitude) { this.endLongitude = endLongitude; }

    public String getSchedule() { return schedule; }
    public void setSchedule(String schedule) { this.schedule = schedule; }

    public double getFare() { return fare; }
    public void setFare(double fare) { this.fare = fare; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
}
