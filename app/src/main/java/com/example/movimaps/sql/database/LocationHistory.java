package com.example.movimaps.sql.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "location_history")
public class LocationHistory {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int userId;
    private String locationName;
    private double latitude;
    private double longitude;
    private long timestamp;
    private int visitCount; // Cuántas veces ha visitado este lugar
    private long totalTimeSpent; // Tiempo total en milisegundos
    private String locationType; // "visited", "searched", "pinned"

    // Constructor
    public LocationHistory() {
        this.timestamp = System.currentTimeMillis();
        this.visitCount = 1;
        this.totalTimeSpent = 0;
    }

    public LocationHistory(int userId, String locationName, double latitude,
                           double longitude, String locationType) {
        this.userId = userId;
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.locationType = locationType;
        this.timestamp = System.currentTimeMillis();
        this.visitCount = 1;
        this.totalTimeSpent = 0;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getLocationName() { return locationName; }
    public void setLocationName(String locationName) { this.locationName = locationName; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public int getVisitCount() { return visitCount; }
    public void setVisitCount(int visitCount) { this.visitCount = visitCount; }

    public long getTotalTimeSpent() { return totalTimeSpent; }
    public void setTotalTimeSpent(long totalTimeSpent) { this.totalTimeSpent = totalTimeSpent; }

    public String getLocationType() { return locationType; }
    public void setLocationType(String locationType) { this.locationType = locationType; }
}
