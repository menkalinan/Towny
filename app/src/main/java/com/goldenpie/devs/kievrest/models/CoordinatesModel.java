package com.goldenpie.devs.kievrest.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class CoordinatesModel {
    @SerializedName("lat")
    double latitude;
    @SerializedName("long")
    double longitude;

    public LatLng getCoordinates(){
        return new LatLng(latitude, longitude);
    }
}
