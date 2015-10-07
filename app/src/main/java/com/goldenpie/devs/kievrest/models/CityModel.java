package com.goldenpie.devs.kievrest.models;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class CityModel {
    @SerializedName("slug")
    private String slug;
    @SerializedName("name")
    private String name;
}
