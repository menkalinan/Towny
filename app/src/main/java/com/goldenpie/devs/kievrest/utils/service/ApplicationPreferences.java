package com.goldenpie.devs.kievrest.utils.service;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ApplicationPreferences {
    private static final String RESTAURANTS_PAGE = "total_restaurants_data_size";
    private static final String RECREATIONS_PAGE = "total_recreations_data_size";
    private static final String CITY = "city";
    private static final String APPLICATION_FIRST_LUNCH = "first_launch";
    private static final String APP_LANGUAGE = "app_lang";
    private static final String CITY_NAME = "city_name";
    private SharedPreferences preferences;

    public ApplicationPreferences(Context context) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean isFirstLaunch() {
        return !preferences.contains(CITY);
    }

    public int getTotalRestaurantsDataSize() {
        return preferences.getInt(RESTAURANTS_PAGE, 0);
    }

    public void setTotalRestaurantsDataSize(int size) {
        preferences.edit()
                .putInt(RESTAURANTS_PAGE, size)
                .apply();
    }

    public int getTotalRecreationsDataSize() {
        return preferences.getInt(RECREATIONS_PAGE, 0);
    }

    public void setTotalRecreationsDataSize(int size) {
        preferences.edit()
                .putInt(RECREATIONS_PAGE, size)
                .apply();
    }

    public String getCurrentCity() {
        return preferences.getString(CITY, "");
    }

    public void setCurrentCity(String slug) {
        preferences.edit()
                .putString(CITY, slug)
                .apply();
    }

    public String getLang() {
        return preferences.getString(APP_LANGUAGE, "");
    }

    public void setLang(String lang) {
        preferences.edit()
                .putString(APP_LANGUAGE, lang)
                .apply();
    }

    public String getCurrentCityName() {
        return preferences.getString(CITY_NAME, "");
    }


    public void setCurrentCityName(String name) {
        preferences.edit()
                .putString(CITY_NAME, name)
                .apply();
    }
}
