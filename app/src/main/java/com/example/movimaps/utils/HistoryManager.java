package com.example.movimaps.utils;

import android.content.Context;
import com.example.movimaps.database.AppDatabase;
import com.example.movimaps.database.RouteHistory;
import com.example.movimaps.database.SearchHistory;
import com.example.movimaps.database.LocationHistory;
import java.util.List;

public class HistoryManager {

    private AppDatabase database;
    private SessionManager sessionManager;
    private Context context;

    public HistoryManager(Context context) {
        this.context = context;
        this.database = AppDatabase.getInstance(context);
        this.sessionManager = new SessionManager(context);
    }

    // MÉTODOS PARA RUTAS
    public void saveRoute(String origin, String destination,
                          double originLat, double originLng,
                          double destinationLat, double destinationLng,
                          String distance, String duration, String routeType) {

        int userId = sessionManager.getUserId();
        if (userId == -1) return;

        RouteHistory routeHistory = new RouteHistory(userId, origin, destination,
                originLat, originLng, destinationLat, destinationLng,
                distance, duration, routeType);

        database.historyDao().insertRouteHistory(routeHistory);
    }

    public List<RouteHistory> getRecentRoutes(int limit) {
        int userId = sessionManager.getUserId();
        if (userId == -1) return null;

        return database.historyDao().getRecentRoutes(userId, limit);
    }

    public List<RouteHistory> getFavoriteRoutes() {
        int userId = sessionManager.getUserId();
        if (userId == -1) return null;

        return database.historyDao().getFavoriteRoutes(userId);
    }

    public void toggleRouteFavorite(int routeId, boolean isFavorite) {
        database.historyDao().updateRouteFavoriteStatus(routeId, isFavorite);
    }

    // MÉTODOS PARA BÚSQUEDAS
    public void saveSearch(String searchQuery, String resultName,
                           double latitude, double longitude, String searchType) {

        int userId = sessionManager.getUserId();
        if (userId == -1) return;

        // Verificar si ya existe esta búsqueda
        SearchHistory existing = database.historyDao().findExistingSearch(userId, searchQuery);

        if (existing != null) {
            // Actualizar contador y timestamp
            existing.setSearchCount(existing.getSearchCount() + 1);
            existing.setTimestamp(System.currentTimeMillis());
            database.historyDao().updateSearchHistory(existing);
        } else {
            // Crear nueva entrada
            SearchHistory searchHistory = new SearchHistory(userId, searchQuery, resultName,
                    latitude, longitude, searchType);
            database.historyDao().insertSearchHistory(searchHistory);
        }
    }

    public List<SearchHistory> getRecentSearches(int limit) {
        int userId = sessionManager.getUserId();
        if (userId == -1) return null;

        return database.historyDao().getRecentSearches(userId, limit);
    }

    public List<SearchHistory> getFrequentSearches(int limit) {
        int userId = sessionManager.getUserId();
        if (userId == -1) return null;

        return database.historyDao().getFrequentSearches(userId, limit);
    }

    // MÉTODOS PARA UBICACIONES
    public void saveLocation(String locationName, double latitude, double longitude, String locationType) {
        int userId = sessionManager.getUserId();
        if (userId == -1) return;

        // Verificar si ya existe una ubicación cercana
        LocationHistory existing = database.historyDao().findNearbyLocation(userId, latitude, longitude);

        if (existing != null) {
            // Actualizar contador de visitas
            existing.setVisitCount(existing.getVisitCount() + 1);
            existing.setTimestamp(System.currentTimeMillis());
            database.historyDao().updateLocationHistory(existing);
        } else {
            // Crear nueva entrada
            LocationHistory locationHistory = new LocationHistory(userId, locationName,
                    latitude, longitude, locationType);
            database.historyDao().insertLocationHistory(locationHistory);
        }
    }

    public List<LocationHistory> getFrequentLocations(int limit) {
        int userId = sessionManager.getUserId();
        if (userId == -1) return null;

        return database.historyDao().getFrequentLocations(userId, limit);
    }

    public List<LocationHistory> getRecentLocations(int limit) {
        int userId = sessionManager.getUserId();
        if (userId == -1) return null;

        return database.historyDao().getRecentLocations(userId, limit);
    }

    // MÉTODOS PARA LIMPIAR HISTORIAL
    public void clearRouteHistory() {
        int userId = sessionManager.getUserId();
        if (userId == -1) return;

        database.historyDao().clearRouteHistory(userId);
    }

    public void clearSearchHistory() {
        int userId = sessionManager.getUserId();
        if (userId == -1) return;

        database.historyDao().clearSearchHistory(userId);
    }

    public void clearLocationHistory() {
        int userId = sessionManager.getUserId();
        if (userId == -1) return;

        database.historyDao().clearLocationHistory(userId);
    }
}

