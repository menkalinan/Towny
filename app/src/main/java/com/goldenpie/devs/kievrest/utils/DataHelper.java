package com.goldenpie.devs.kievrest.utils;

import com.goldenpie.devs.kievrest.models.NewsModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class DataHelper {
    @Getter
    @Setter
    private HashMap<ModelTypeEnum, ArrayList<?>> dataMap = new HashMap<>();

    @SuppressWarnings("unchecked")
    public ArrayList<NewsModel> getNewsList(){
        return (ArrayList<NewsModel>) dataMap.get(ModelTypeEnum.NEWS);
    }
}
