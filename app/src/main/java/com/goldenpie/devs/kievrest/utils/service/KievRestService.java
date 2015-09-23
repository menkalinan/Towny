package com.goldenpie.devs.kievrest.utils.service;

import com.goldenpie.devs.kievrest.api.KievRestApi;
import com.goldenpie.devs.kievrest.api.WeatherApi;
import com.goldenpie.devs.kievrest.event.NewsLoadedEvent;
import com.goldenpie.devs.kievrest.event.WeatherLoadedEvent;
import com.goldenpie.devs.kievrest.utils.BaseCallback;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(suppressConstructorProperties = true)
public class KievRestService {
    private final KievRestApi kievRestApi;
    private final WeatherApi weatherApi;

    public void loadCurrentWeather() {
        weatherApi.getCurrentWeather(new BaseCallback<WeatherLoadedEvent>());
    }

    public void loadNews() {
        kievRestApi.getNews(new BaseCallback<NewsLoadedEvent>());
    }
}
