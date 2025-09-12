package com.example.movimaps.sql.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "bus_stops",
        foreignKeys = @ForeignKey(entity = DriverRoute.class,
                parentColumns = "id",
                childColumns = "routeId",
                onDelete = ForeignKey.CASCADE))
public class BusStop {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int routeId; // FK a DriverRoute
    private String stopName;
    private String stopDescription;
    private double latitude;
    private double longitude;
    private int stopOrder; // Orden en la ruta
    private String estimatedTime; // Tiempo estimado de llegada
    private boolean isActive;
    private long createdAt;

    // NUEVO: Constructor para paradas de chofer
    public BusStop() {
        this.createdAt = System.currentTimeMillis();
        this.isActive = true;
    }

    public BusStop(int routeId, String stopName, String stopDescription,
                   double latitude, double longitude, int stopOrder, String estimatedTime) {
        this.routeId = routeId;
        this.stopName = stopName;
        this.stopDescription = stopDescription;
        this.latitude = latitude;
        this.longitude = longitude;
        this.stopOrder = stopOrder;
        this.estimatedTime = estimatedTime;
        this.createdAt = System.currentTimeMillis();
        this.isActive = true;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getRouteId() { return routeId; }
    public void setRouteId(int routeId) { this.routeId = routeId; }

    public String getStopName() { return stopName; }
    public void setStopName(String stopName) { this.stopName = stopName; }

    public String getStopDescription() { return stopDescription; }
    public void setStopDescription(String stopDescription) { this.stopDescription = stopDescription; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public int getStopOrder() { return stopOrder; }
    public void setStopOrder(int stopOrder) { this.stopOrder = stopOrder; }

    public String getEstimatedTime() { return estimatedTime; }
    public void setEstimatedTime(String estimatedTime) { this.estimatedTime = estimatedTime; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
}
