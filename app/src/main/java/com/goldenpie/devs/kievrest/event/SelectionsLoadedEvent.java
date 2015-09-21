package com.goldenpie.devs.kievrest.event;

import com.goldenpie.devs.kievrest.models.SelectionModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class SelectionsLoadedEvent extends NewsLoadedEvent {
    @SerializedName("results")
    private List<SelectionModel> results;
}
