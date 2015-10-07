package com.goldenpie.devs.kievrest.event;

import com.goldenpie.devs.kievrest.models.SelectionModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.Data;

@SuppressWarnings({"unused", "Lombok"})
@Data
public class SelectionsLoadedEvent extends BaseLoadedEvent {
    @SerializedName("results")
    private ArrayList<SelectionModel> results;
}
