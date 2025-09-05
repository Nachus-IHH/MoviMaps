package com.example.movimaps.osmmap.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RouteApiService {

    // API para calcular rutas usando OSRM (Open Source Routing Machine)
    @GET("route/v1/driving/{coordinates}")
    Call<RouteResponse> getRoute(
            @Query("coordinates") String coordinates,
            @Query("overview") String overview,
            @Query("geometries") String geometries
    );

    /* API alternativa usando GraphHopper
    @GET("route")
    Call<GraphHopperResponse> getGraphHopperRoute(
            @Query("point") String[] points,
            @Query("vehicle") String vehicle,
            @Query("key") String apiKey
    );
     */
}
