package com.goldenpie.devs.kievrest.models;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PhotosModel {
    @SerializedName("id")
    int id;
    @SerializedName("image")
    String imageUrl;
}
