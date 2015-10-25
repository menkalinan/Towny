package com.goldenpie.devs.kievrest.event;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class BaseLoadedEvent {
    @SerializedName("count")
    protected int count;
    @SerializedName("next")
    protected String nextUrl;
    @SerializedName("previous")
    protected String previousUrl;

    public int getNextPage() {
        return !getNextUrl().equals("null") ?
                Integer.parseInt(getNextUrl().substring(getNextUrl().lastIndexOf("=") + 1)) : -1;
    }
}
