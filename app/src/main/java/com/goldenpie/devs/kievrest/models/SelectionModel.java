package com.goldenpie.devs.kievrest.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class SelectionModel extends BaseDataModel {
    @SerializedName("ctype")
    private String type;
    @SerializedName("items")
    private List<ItemModel> items;
}
