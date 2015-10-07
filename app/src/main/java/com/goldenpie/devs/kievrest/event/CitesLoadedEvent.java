package com.goldenpie.devs.kievrest.event;

import com.goldenpie.devs.kievrest.models.CityModel;

import java.util.ArrayList;

import lombok.Data;

@Data
public class CitesLoadedEvent {
    private ArrayList<CityModel> models;
}
