package com.goldenpie.devs.kievrest.event;

import com.goldenpie.devs.kievrest.models.NewsModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class NewsLoadedEvent extends BaseLoadedEvent{
    @SerializedName("results")
    private List<NewsModel> results;
}