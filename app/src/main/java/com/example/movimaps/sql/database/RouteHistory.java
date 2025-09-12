package com.example.movimaps.sql.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "route_history")
public class RouteHistory {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int userId;
    private String origin;
    private String destination;
    private double originLat;
    private double originLng;
    private double destinationLat;
    private double destinationLng;
    private String distance;
    private String duration;
    private long timestamp;
    private String routeType; // "driving", "walking", "cycling"
    private boolean isFavorite;

    // Constructor
    public RouteHistory() {
        this.timestamp = System.currentTimeMillis();
        this.isFavorite = false;
    }

    public RouteHistory(int userId, String origin, String destination,
                        double originLat, double originLng,
                        double destinationLat, double destinationLng,
                        String distance, String duration, String routeType) {
        this.userId = userId;
        this.origin = origin;
        this.destination = destination;
        this.originLat = originLat;
        this.originLng = originLng;
        this.destinationLat = destinationLat;
        this.destinationLng = destinationLng;
        this.distance = distance;
        this.duration = duration;
        this.routeType = routeType;
        this.timestamp = System.currentTimeMillis();
        this.isFavorite = false;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public double getOriginLat() { return originLat; }
    public void setOriginLat(double originLat) { this.originLat = originLat; }

    public double getOriginLng() { return originLng; }
    public void setOriginLng(double originLng) { this.originLng = originLng; }

    public double getDestinationLat() { return destinationLat; }
    public void setDestinationLat(double destinationLat) { this.destinationLat = destinationLat; }

    public double getDestinationLng() { return destinationLng; }
    public void setDestinationLng(double destinationLng) { this.destinationLng = destinationLng; }

    public String getDistance() { return distance; }
    public void setDistance(String distance) { this.distance = distance; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public String getRouteType() { return routeType; }
    public void setRouteType(String routeType) { this.routeType = routeType; }

    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }
}