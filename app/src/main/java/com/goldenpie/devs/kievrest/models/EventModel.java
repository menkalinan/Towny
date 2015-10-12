package com.goldenpie.devs.kievrest.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.Data;

@Data
public class EventModel extends BaseDataModel {

    @SerializedName("age_restriction")
    private String ageRestriction;
    @SerializedName("is_free")
    private String isFree;
    @SerializedName("dates")
    private ArrayList<Dates> dates;

    @Data
    public class Dates {
        @SerializedName("start")
        private long startDate;
        @SerializedName("end")
        private long endDate;
    }

    public boolean isFree() {
        return !isFree.equals("false");
    }
}
