package com.goldenpie.devs.kievrest.models;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ItemModel {
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("favorites_count")
    private int favoriteCount;
    @SerializedName("comments_count")
    private int commentsCount;
    @SerializedName("description")
    private String description;
    @SerializedName("ctype")
    private String type;
    @SerializedName("place")
    private PlaceModel place;
}
