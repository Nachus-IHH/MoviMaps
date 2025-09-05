package com.example.movimaps.osmmap.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MapApiService {

    @GET("search")
    Call<SearchResponse> searchLocation(
            @Query("q") String query,
            @Query("format") String format,
            @Query("limit") int limit
    );

    @GET("reverse")
    Call<ReverseGeocodeResponse> reverseGeocode(
            @Query("lat") double latitude,
            @Query("lon") double longitude,
            @Query("format") String format
    );
}
