package com.example.movimaps.osmmap.api;

import com.google.gson.annotations.SerializedName;
import java.util.List;

import lombok.Data;

// Only use Getters
@Data
public class RouteResponse {
    @SerializedName("code")
    private String code;

    @SerializedName("routes")
    private List<Route> routes;

    @Data
    public static class Route {
        @SerializedName("geometry")
        private String geometry;

        @SerializedName("legs")
        private List<Leg> legs;

        @SerializedName("distance")
        private double distance;

        @SerializedName("duration")
        private double duration;

        @Data
        public static class Leg {
            @SerializedName("steps")
            private List<Step> steps;

            @SerializedName("distance")
            private double distance;

            @SerializedName("duration")
            private double duration;

            @Data
            public static class Step {
                @SerializedName("geometry")
                private String geometry;

                @SerializedName("maneuver")
                private Maneuver maneuver;

                @SerializedName("name")
                private String name;

                @SerializedName("distance")
                private double distance;

                @SerializedName("duration")
                private double duration;

                @Data
                public static class Maneuver {
                    @SerializedName("bearing_after")

                    private int bearingAfter;

                    @SerializedName("bearing_before")
                    private int bearingBefore;

                    @SerializedName("location")
                    private double[] location;

                    @SerializedName("modifier")
                    private String modifier;

                    @SerializedName("type")
                    private String type;
                }


            }


        }


    }


}

