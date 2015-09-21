package com.goldenpie.devs.kievrest.models;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ItemModel {
    @SerializedName("id")
    int id;
    @SerializedName("title")
    String title;
    @SerializedName("favorites_count")
    int favoriteCount;
    @SerializedName("comments_count")
    int commentsCount;
    @SerializedName("description")
    String description;
    @SerializedName("ctype")
    String type;
    @SerializedName("place")
    PlaceModel place;
}
