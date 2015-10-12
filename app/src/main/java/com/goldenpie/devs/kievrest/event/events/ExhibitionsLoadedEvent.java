package com.goldenpie.devs.kievrest.event.events;

import com.goldenpie.devs.kievrest.event.BaseLoadedEvent;
import com.goldenpie.devs.kievrest.models.EventModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ExhibitionsLoadedEvent extends BaseLoadedEvent {
    @SerializedName("results")
    private ArrayList<EventModel> results;
}
