package com.goldenpie.devs.kievrest.models;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PhotosModel {
    @SerializedName("id")
    private int id;
    @SerializedName("image")
    private String imageUrl;
    @SerializedName("thumbnails")
    private Thumbnails thumbnails;

    @Data
    public class Thumbnails {
        @SerializedName("640x384")
        private String largeThumbnail;
        @SerializedName("144x96")
        private String smallThumbnail;
    }
}
