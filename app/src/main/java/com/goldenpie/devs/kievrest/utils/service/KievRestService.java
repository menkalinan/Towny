package com.goldenpie.devs.kievrest.utils.service;

import com.goldenpie.devs.kievrest.api.KievRestApi;
import com.goldenpie.devs.kievrest.api.WeatherApi;
import com.goldenpie.devs.kievrest.event.NewsLoadedEvent;
import com.goldenpie.devs.kievrest.event.RestaurantsLoadedEvent;
import com.goldenpie.devs.kievrest.event.SelectionLoadedEvent;
import com.goldenpie.devs.kievrest.event.SelectionsLoadedEvent;
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
    public void loadSelections() {
        kievRestApi.getSelections(new BaseCallback<SelectionsLoadedEvent>());
    }
    public void loadSelection(String id) {
        kievRestApi.getSelection(id, new BaseCallback<SelectionLoadedEvent>());
    }

    public void loadMoreNews(int current_page) {
        kievRestApi.getNews(String.valueOf(current_page), new BaseCallback<NewsLoadedEvent>());
    }

    public void loadMoreSelection(int current_page) {
        kievRestApi.getSelections(String.valueOf(current_page), new BaseCallback<SelectionsLoadedEvent>());
    }

    public void loadRestaurants() {
        kievRestApi.getRestaurants(new BaseCallback<RestaurantsLoadedEvent>());
    }
    public void loadMoreRestaurants(int current_page) {
        kievRestApi.getRestaurants(String.valueOf(current_page), new BaseCallback<RestaurantsLoadedEvent>());
    }
}
