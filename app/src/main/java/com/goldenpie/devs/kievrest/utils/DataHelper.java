package com.goldenpie.devs.kievrest.utils;

import com.goldenpie.devs.kievrest.models.NewsModel;
import com.goldenpie.devs.kievrest.models.PlaceModel;
import com.goldenpie.devs.kievrest.models.SelectionModel;

import java.util.ArrayList;
import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;

public class DataHelper {
    @Getter
    @Setter
    private HashMap<ModelTypeEnum, ArrayList<?>> dataMap = new HashMap<>();

    @SuppressWarnings("unchecked")
    public ArrayList<NewsModel> getNewsList() {
        return (ArrayList<NewsModel>) dataMap.get(ModelTypeEnum.NEWS);
    }
    @SuppressWarnings("unchecked")
    public ArrayList<SelectionModel> getSelectionsList() {
        return (ArrayList<SelectionModel>) dataMap.get(ModelTypeEnum.SELECTIONS);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<PlaceModel> getRestaurantsList() {
        return (ArrayList<PlaceModel>) dataMap.get(ModelTypeEnum.RESTAURANTS);
    }
}
