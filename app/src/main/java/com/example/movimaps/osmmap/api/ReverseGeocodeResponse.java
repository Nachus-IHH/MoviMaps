package com.example.movimaps.osmmap.api;
/*
import com.google.gson.annotations.SerializedName;

public class ReverseGeocodeResponse {
    @SerializedName("place_id")
    private String placeId;

    @SerializedName("licence")
    private String licence;

    @SerializedName("osm_type")
    private String osmType;

    @SerializedName("osm_id")
    private String osmId;

    @SerializedName("lat")
    private String latitude;

    @SerializedName("lon")
    private String longitude;

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("address")
    private Address address;

    public static class Address {
        @SerializedName("house_number")
        private String houseNumber;

        @SerializedName("road")
        private String road;

        @SerializedName("city")
        private String city;

        @SerializedName("state")
        private String state;

        @SerializedName("country")
        private String country;

        @SerializedName("postcode")
        private String postcode;

        // Getters y setters
        public String getCity() { return city; }
        public String getCountry() { return country; }
        public String getRoad() { return road; }
        // ... otros getters y setters
    }

    // Getters y setters principales
    public String getLatitude() { return latitude; }
    public String getLongitude() { return longitude; }
    public String getDisplayName() { return displayName; }
    public Address getAddress() { return address; }
    // ... otros getters y setters
}
*/


import com.google.gson.annotations.SerializedName;

public class ReverseGeocodeResponse {
    @SerializedName("place_id")
    private String placeId;

    @SerializedName("licence")
    private String licence;

    @SerializedName("osm_type")
    private String osmType;

    @SerializedName("osm_id")
    private String osmId;

    @SerializedName("lat")
    private String latitude;

    @SerializedName("lon")
    private String longitude;

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("address")
    private Address address;

    public static class Address {
        @SerializedName("house_number")
        private String houseNumber;

        @SerializedName("road")
        private String road;

        @SerializedName("city")
        private String city;

        @SerializedName("state")
        private String state;

        @SerializedName("country")
        private String country;

        @SerializedName("postcode")
        private String postcode;

        // Getters y setters
        public String getCity() { return city; }
        public String getCountry() { return country; }
        public String getRoad() { return road; }
        // ... otros getters y setters
    }

    // Getters y setters principales
    public String getLatitude() { return latitude; }
    public String getLongitude() { return longitude; }
    public String getDisplayName() { return displayName; }
    public Address getAddress() { return address; }
    // ... otros getters y setters
}
