package com.goldenpie.devs.keivrest.model;

import java.util.ArrayList;

import lombok.Data;

@Data
public class PlacesModel {
    private ArrayList<String> ids;
    private ArrayList<String> labels;
    private ArrayList<String> address;
}
