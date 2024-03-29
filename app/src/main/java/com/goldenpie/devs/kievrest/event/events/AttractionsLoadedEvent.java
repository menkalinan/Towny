package com.goldenpie.devs.kievrest.event.events;

import com.goldenpie.devs.kievrest.event.BaseLoadedEvent;
import com.goldenpie.devs.kievrest.models.PlaceModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.Data;

@SuppressWarnings("Lombok")
@Data
public class AttractionsLoadedEvent extends BaseLoadedEvent {
    @SerializedName("results")
    private ArrayList<PlaceModel> results;
}
