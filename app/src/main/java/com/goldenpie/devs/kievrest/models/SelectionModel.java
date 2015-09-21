package com.goldenpie.devs.kievrest.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class SelectionModel extends NewsModel{
    @SerializedName("ctype")
    String type;
    @SerializedName("items")
    List<ItemModel> items;
}
