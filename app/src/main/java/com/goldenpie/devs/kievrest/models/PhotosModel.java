package com.goldenpie.devs.kievrest.models;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PhotosModel {
    @SerializedName("id")
    private int id;
    @SerializedName("image")
    private String imageUrl;
}
