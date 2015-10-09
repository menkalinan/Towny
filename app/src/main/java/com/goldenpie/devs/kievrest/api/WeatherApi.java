package com.goldenpie.devs.kievrest.api;

import com.goldenpie.devs.kievrest.config.Constants;
import com.goldenpie.devs.kievrest.event.WeatherLoadedEvent;
import com.goldenpie.devs.kievrest.utils.BaseCallback;

import retrofit.http.GET;
import retrofit.http.Query;

public interface WeatherApi {
    @GET(Constants.CURRENT_WEATHER)
    void getCurrentWeather(@Query("q") String city,
                           @Query("lang") String lang,
                           @Query("APPID") String apiKey,
                           BaseCallback<WeatherLoadedEvent> callback);
}
