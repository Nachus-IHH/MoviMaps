package com.example.movimaps.sql.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface HistoryDao {

    // ROUTE HISTORY
    @Insert
    long insertRouteHistory(RouteHistory routeHistory);

    @Update
    void updateRouteHistory(RouteHistory routeHistory);

    @Delete
    void deleteRouteHistory(RouteHistory routeHistory);

    @Query("SELECT * FROM route_history WHERE userId = :userId ORDER BY timestamp DESC LIMIT :limit")
    List<RouteHistory> getRecentRoutes(int userId, int limit);

    @Query("SELECT * FROM route_history WHERE userId = :userId AND isFavorite = 1 ORDER BY timestamp DESC")
    List<RouteHistory> getFavoriteRoutes(int userId);

    @Query("SELECT * FROM route_history WHERE userId = :userId ORDER BY timestamp DESC")
    List<RouteHistory> getAllRouteHistory(int userId);

    @Query("DELETE FROM route_history WHERE userId = :userId")
    void clearRouteHistory(int userId);

    @Query("UPDATE route_history SET isFavorite = :isFavorite WHERE id = :routeId")
    void updateRouteFavoriteStatus(int routeId, boolean isFavorite);

    // SEARCH HISTORY
    @Insert
    long insertSearchHistory(SearchHistory searchHistory);

    @Update
    void updateSearchHistory(SearchHistory searchHistory);

    @Delete
    void deleteSearchHistory(SearchHistory searchHistory);

    @Query("SELECT * FROM search_history WHERE userId = :userId ORDER BY timestamp DESC LIMIT :limit")
    List<SearchHistory> getRecentSearches(int userId, int limit);

    @Query("SELECT * FROM search_history WHERE userId = :userId ORDER BY searchCount DESC, timestamp DESC LIMIT :limit")
    List<SearchHistory> getFrequentSearches(int userId, int limit);

    @Query("SELECT * FROM search_history WHERE userId = :userId AND searchQuery LIKE :query ORDER BY searchCount DESC")
    List<SearchHistory> searchInHistory(int userId, String query);

    @Query("DELETE FROM search_history WHERE userId = :userId")
    void clearSearchHistory(int userId);

    @Query("SELECT * FROM search_history WHERE userId = :userId AND searchQuery = :query LIMIT 1")
    SearchHistory findExistingSearch(int userId, String query);

    // LOCATION HISTORY
    @Insert
    long insertLocationHistory(LocationHistory locationHistory);

    @Update
    void updateLocationHistory(LocationHistory locationHistory);

    @Delete
    void deleteLocationHistory(LocationHistory locationHistory);

    @Query("SELECT * FROM location_history WHERE userId = :userId ORDER BY visitCount DESC, timestamp DESC LIMIT :limit")
    List<LocationHistory> getFrequentLocations(int userId, int limit);

    @Query("SELECT * FROM location_history WHERE userId = :userId ORDER BY timestamp DESC LIMIT :limit")
    List<LocationHistory> getRecentLocations(int userId, int limit);

    @Query("DELETE FROM location_history WHERE userId = :userId")
    void clearLocationHistory(int userId);

    @Query("SELECT * FROM location_history WHERE userId = :userId AND latitude BETWEEN :lat-0.001 AND :lat+0.001 AND longitude BETWEEN :lng-0.001 AND :lng+0.001 LIMIT 1")
    LocationHistory findNearbyLocation(int userId, double lat, double lng);
}