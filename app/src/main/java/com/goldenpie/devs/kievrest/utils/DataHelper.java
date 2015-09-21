package com.goldenpie.devs.kievrest.utils;

import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;

public class DataHelper {
    @Getter
    @Setter
    private HashMap<ModelTypeEnum, ?> dataMap = new HashMap<>();
}
