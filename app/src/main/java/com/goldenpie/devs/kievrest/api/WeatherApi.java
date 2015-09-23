package com.goldenpie.devs.kievrest.api;

import com.goldenpie.devs.kievrest.config.Constants;
import com.goldenpie.devs.kievrest.event.NewsLoadedEvent;
import com.goldenpie.devs.kievrest.event.WeatherLoadedEvent;
import com.goldenpie.devs.kievrest.utils.BaseCallback;

import retrofit.http.GET;

public interface WeatherApi {
    @GET(Constants.CURRENT_WEATHER)
    void getCurrentWeather(BaseCallback<WeatherLoadedEvent> callback);
}
