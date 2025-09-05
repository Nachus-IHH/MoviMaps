package com.example.movimaps.osmmap.api;

import com.google.gson.annotations.SerializedName;
import java.util.List;

import lombok.Data;

@Data
public class SearchResponse {
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
}
