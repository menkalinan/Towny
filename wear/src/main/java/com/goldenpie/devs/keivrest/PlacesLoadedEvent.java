package com.goldenpie.devs.keivrest;

import java.util.ArrayList;

import lombok.Data;

@Data
public class PlacesLoadedEvent {
    private ArrayList<String> ids;
    private ArrayList<String> labels;
    private ArrayList<String> address;
}
