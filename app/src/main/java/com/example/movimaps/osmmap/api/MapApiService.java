package com.example.movimaps.osmmap.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MapApiService {

    // En tu interfaz MapApiService.java
    @GET("search")
    Call<List<GeocodingResponse>> search(@Query("q") String query, @Query("format") String format);

    @GET("reverse")
    Call<ReverseGeocodeResponse> reverseGeocode(
            @Query("lat") double latitude,
            @Query("lon") double longitude,
            @Query("format") String format
    );
}
