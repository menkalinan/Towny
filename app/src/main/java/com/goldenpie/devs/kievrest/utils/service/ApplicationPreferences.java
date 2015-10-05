package com.goldenpie.devs.kievrest.utils.service;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ApplicationPreferences {
    private static final String RESTAURANTS_PAGE = "total_restaurants_data_size";
    private SharedPreferences preferences;
    private Context context;

    public ApplicationPreferences(Context context) {
        this.context = context;
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public int getTotalRestauratsDataSize() {
        return preferences.getInt(RESTAURANTS_PAGE, 0);
    }

    public void setTotalRestauratsDataSize(int size) {
        preferences.edit()
                .putInt(RESTAURANTS_PAGE, size)
                .apply();
    }
}
