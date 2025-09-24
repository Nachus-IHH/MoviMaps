package com.example.movimaps.osmmap.api;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class GeocodingResponse {
    @SerializedName("place_id")
    private String placeId;

    @SerializedName("licence")
    private String licence;

    @SerializedName("osm_type")
    private String osmType;

    @SerializedName("osm_id")
    private String osmId;

    @SerializedName("boundingbox")
    private List<String> boundingBox;

    @SerializedName("lat")
    private String latitude;

    @SerializedName("lon")
    private String longitude;

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("class")
    private String classification;

    @SerializedName("type")
    private String type;

    @SerializedName("importance")
    private double importance;

    @SerializedName("address")
    private Address address;

    public static class Address {
        @SerializedName("house_number")
        private String houseNumber;

        @SerializedName("road")
        private String road;

        @SerializedName("suburb")
        private String suburb;

        @SerializedName("city")
        private String city;

        @SerializedName("state")
        private String state;

        @SerializedName("country")
        private String country;

        @SerializedName("postcode")
        private String postcode;

        // Getters and setters
        public String getHouseNumber() { return houseNumber; }
        public void setHouseNumber(String houseNumber) { this.houseNumber = houseNumber; }

        public String getRoad() { return road; }
        public void setRoad(String road) { this.road = road; }

        public String getSuburb() { return suburb; }
        public void setSuburb(String suburb) { this.suburb = suburb; }

        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }

        public String getState() { return state; }
        public void setState(String state) { this.state = state; }

        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }

        public String getPostcode() { return postcode; }
        public void setPostcode(String postcode) { this.postcode = postcode; }
    }

    // Main getters and setters
    public String getPlaceId() { return placeId; }
    public void setPlaceId(String placeId) { this.placeId = placeId; }

    public String getLicence() { return licence; }
    public void setLicence(String licence) { this.licence = licence; }

    public String getOsmType() { return osmType; }
    public void setOsmType(String osmType) { this.osmType = osmType; }

    public String getOsmId() { return osmId; }
    public void setOsmId(String osmId) { this.osmId = osmId; }

    public List<String> getBoundingBox() { return boundingBox; }
    public void setBoundingBox(List<String> boundingBox) { this.boundingBox = boundingBox; }

    public String getLatitude() { return latitude; }
    public void setLatitude(String latitude) { this.latitude = latitude; }

    public String getLongitude() { return longitude; }
    public void setLongitude(String longitude) { this.longitude = longitude; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getClassification() { return classification; }
    public void setClassification(String classification) { this.classification = classification; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getImportance() { return importance; }
    public void setImportance(double importance) { this.importance = importance; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }
}
