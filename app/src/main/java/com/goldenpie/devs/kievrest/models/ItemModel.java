package com.goldenpie.devs.kievrest.models;

import android.text.TextUtils;

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
    @SerializedName("address")
    private String address;

    public String getFinalTitle() {
        title = title.replaceAll("\\#\\d. ", "");
        return title.substring(0, 1).toUpperCase() + title.substring(1);
    }

    public String getFinalDescription() {
        description = description.replaceAll("KudaGo:", "");
        return description = getDescription().replaceAll("_", "");
    }

    public String getAddress() {
        return !TextUtils.isEmpty(address) ? address : getAdditionalAddress();
    }

    public String getAdditionalAddress() {
        String address;
        try {
            address = getFinalDescription().substring(getFinalDescription().indexOf("\r\n\r\n"));
        } catch (Exception e) {
            return null;
        }

        String[] addressArray = address.split("\r\n\r\n");
        address = "";
        for (int i = 1; i < addressArray.length; i++) {
            address += addressArray[i].replace("\r\n\r\n", "");
            if (i < addressArray.length - 1) {
                address += ", ";
            }
        }
        return address;
    }
}
