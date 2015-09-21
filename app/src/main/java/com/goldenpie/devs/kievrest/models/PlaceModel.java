package com.goldenpie.devs.kievrest.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class PlaceModel {
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("address")
    private String address;
    @SerializedName("site_url")
    private String url;
    @SerializedName("is_closed")
    private String isClosed;
    @SerializedName("timetable")
    private String timeTable;
    @SerializedName("phone")
    private String phone;
    @SerializedName("is_stub")
    private String isStub;
    @SerializedName("images")
    private List<PhotosModel> photos;
    @SerializedName("body_text")
    private String body;
    @SerializedName("foreign_url")
    private String extraUrl;
    @SerializedName("coords")
    private CoordinatesModel coordinates;
    @SerializedName("subway")
    private String subway;
    @SerializedName("favorite_count")
    private int favoriteCount;
    @SerializedName("comments_count")
    private int commentsCount;

    public boolean isClosed(){
        return getIsClosed().equals("true");
    }
}
