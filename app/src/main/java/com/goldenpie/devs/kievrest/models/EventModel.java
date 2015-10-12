package com.goldenpie.devs.kievrest.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class EventModel extends BaseDataModel {

    @SerializedName("age_restriction")
    private Integer ageRestriction;
    @SerializedName("is_free")
    private String isFree;
    @SerializedName("dates")
    private ArrayList<Dates> dates;
    @SerializedName("place")
    private PlaceModel place;

    public boolean isFree() {
        return !isFree.equals("false");
    }

    @Data
    public class Dates {
        @SerializedName("start")
        private String startDate;
        @SerializedName("end")
        private String endDate;
    }
}
