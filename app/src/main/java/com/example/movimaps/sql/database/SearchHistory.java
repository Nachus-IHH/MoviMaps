package com.example.movimaps.sql.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "search_history")
public class SearchHistory {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int userId;
    private String searchQuery;
    private String resultName;
    private double latitude;
    private double longitude;
    private long timestamp;
    private int searchCount; // Cuántas veces se ha buscado
    private String searchType; // "place", "address", "coordinate"

    // Constructor
    public SearchHistory() {
        this.timestamp = System.currentTimeMillis();
        this.searchCount = 1;
    }

    public SearchHistory(int userId, String searchQuery, String resultName,
                         double latitude, double longitude, String searchType) {
        this.userId = userId;
        this.searchQuery = searchQuery;
        this.resultName = resultName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.searchType = searchType;
        this.timestamp = System.currentTimeMillis();
        this.searchCount = 1;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getSearchQuery() { return searchQuery; }
    public void setSearchQuery(String searchQuery) { this.searchQuery = searchQuery; }

    public String getResultName() { return resultName; }
    public void setResultName(String resultName) { this.resultName = resultName; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public int getSearchCount() { return searchCount; }
    public void setSearchCount(int searchCount) { this.searchCount = searchCount; }

    public String getSearchType() { return searchType; }
    public void setSearchType(String searchType) { this.searchType = searchType; }
}
