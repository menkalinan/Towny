package com.goldenpie.devs.kievrest.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class PlaceModel {
    @SerializedName("id")
    int id;
    @SerializedName("title")
    String title;
    @SerializedName("address")
    String address;
    @SerializedName("site_url")
    String url;
    @SerializedName("is_closed")
    String isClosed;
    @SerializedName("timetable")
    String timeTable;
    @SerializedName("phone")
    String phone;
    @SerializedName("is_stub")
    String isStub;
    @SerializedName("images")
    List<PhotosModel> photos;
    @SerializedName("body_text")
    String body;
    @SerializedName("foreign_url")
    String extraUrl;
    @SerializedName("coords")
    CoordinatesModel coordinates;
    @SerializedName("subway")
    String subway;
    @SerializedName("favorite_count")
    int favoriteCount;
    @SerializedName("comments_count")
    int commentsCount;

    public boolean isClosed(){
        return getIsClosed().equals("true");
    }
}
