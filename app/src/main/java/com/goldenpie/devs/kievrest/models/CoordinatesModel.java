package com.goldenpie.devs.kievrest.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class CoordinatesModel {
    @SerializedName("lat")
    private double latitude;
    @SerializedName("long")
    private double longitude;
    @SerializedName("lon")
    private double weatherLongitude;

    public LatLng getCoordinates() {
        return new LatLng(latitude, longitude);
    }

    public LatLng getWeatherCoordinates() {
        return new LatLng(latitude, weatherLongitude);
    }
}
