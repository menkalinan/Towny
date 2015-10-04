package com.goldenpie.devs.kievrest.event;

import com.goldenpie.devs.kievrest.models.PlaceModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.Data;

@SuppressWarnings("Lombok")
@Data
public class NearPlacesLoadedEvent extends BaseLoadedEvent {
    @SerializedName("results")
    private ArrayList<PlaceModel> results;
}
