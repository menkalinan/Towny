package com.goldenpie.devs.kievrest.models;

import android.text.Html;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class BaseDataModel {
    @SerializedName("id")
    protected int id;
    @SerializedName("publication_date")
    protected long publicationDate;
    @SerializedName("title")
    protected String title;
    @SerializedName("description")
    protected String description;
    @SerializedName("body_text")
    protected String body;
    @SerializedName("images")
    protected List<PhotosModel> photos;
    @SerializedName("site_url")
    protected String url;
    @SerializedName("favorites_count")
    protected int favoritesCount;
    @SerializedName("comments_count")
    protected int commentsCount;

    public String getClearBody() {
        return Html.fromHtml(getBody()).toString();
    }
    public String getClearDescription() {
        description = description.replaceAll("(\\(.*?\\))", "");
        description = description.replaceAll("\\[|\\]", "");
        return description;
    }

    public String getFinalTitle() {
        return title.substring(0, 1).toUpperCase() + title.substring(1);
    }
}
