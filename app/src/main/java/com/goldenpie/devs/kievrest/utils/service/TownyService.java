package com.goldenpie.devs.kievrest.utils.service;

import com.goldenpie.devs.kievrest.TownyApplication;
import com.goldenpie.devs.kievrest.api.TownyApi;
import com.goldenpie.devs.kievrest.api.WeatherApi;
import com.goldenpie.devs.kievrest.event.AttractionsLoadedEvent;
import com.goldenpie.devs.kievrest.event.BarsLoadedEvent;
import com.goldenpie.devs.kievrest.event.ClubsLoadedEvent;
import com.goldenpie.devs.kievrest.event.MuseumsLoadedEvent;
import com.goldenpie.devs.kievrest.event.NearPlacesLoadedEvent;
import com.goldenpie.devs.kievrest.event.NewsLoadedEvent;
import com.goldenpie.devs.kievrest.event.RecreationsLoadedEvent;
import com.goldenpie.devs.kievrest.event.RestaurantsLoadedEvent;
import com.goldenpie.devs.kievrest.event.SelectionLoadedEvent;
import com.goldenpie.devs.kievrest.event.SelectionsLoadedEvent;
import com.goldenpie.devs.kievrest.event.ShopsLoadedEvent;
import com.goldenpie.devs.kievrest.event.WeatherLoadedEvent;
import com.goldenpie.devs.kievrest.models.CityModel;
import com.goldenpie.devs.kievrest.utils.BaseCallback;

import java.util.ArrayList;

import javax.inject.Inject;

public class TownyService {
    private final TownyApi townyApi;
    private final WeatherApi weatherApi;

    @Inject
    protected ApplicationPreferences preferences;

    public TownyService(TownyApi townyApi, WeatherApi weatherApi) {
        this.townyApi = townyApi;
        this.weatherApi = weatherApi;
        TownyApplication.appComponent().inject(this);
    }

    public void loadCurrentWeather() {
        weatherApi.getCurrentWeather(preferences.getCurrentCityName(), preferences.getLang(), new BaseCallback<WeatherLoadedEvent>());
    }

    public void loadCites(String lang) {
        townyApi.getSupportCites(lang, new BaseCallback<ArrayList<CityModel>>());
    }

    public void loadNews() {
        townyApi.getNews(preferences.getCurrentCity(), new BaseCallback<NewsLoadedEvent>());
    }

    public void loadSelections() {
        townyApi.getSelections(preferences.getCurrentCity(), new BaseCallback<SelectionsLoadedEvent>());
    }

    public void loadSelection(String id) {
        townyApi.getSelection(id, preferences.getCurrentCity(), new BaseCallback<SelectionLoadedEvent>());
    }

    public void loadMoreNews(int current_page) {
        townyApi.getNews(String.valueOf(current_page), preferences.getCurrentCity(), new BaseCallback<NewsLoadedEvent>());
    }

    public void loadMoreSelection(int current_page) {
        townyApi.getSelections(String.valueOf(current_page), preferences.getCurrentCity(), new BaseCallback<SelectionsLoadedEvent>());
    }

    public void loadRestaurants() {
        townyApi.getRestaurants(preferences.getCurrentCity(), new BaseCallback<RestaurantsLoadedEvent>());
    }

    public void loadMoreRestaurants(int current_page) {
        townyApi.getRestaurants(String.valueOf(current_page), preferences.getCurrentCity(), new BaseCallback<RestaurantsLoadedEvent>());
    }

    public void loadMuseums() {
        townyApi.getMuseums(preferences.getCurrentCity(), new BaseCallback<MuseumsLoadedEvent>());
    }

    public void loadMoreMuseums(int current_page) {
        townyApi.getMuseums(String.valueOf(current_page), preferences.getCurrentCity(), new BaseCallback<MuseumsLoadedEvent>());
    }

    public void loadClubs() {
        townyApi.getClubs(preferences.getCurrentCity(), new BaseCallback<ClubsLoadedEvent>());
    }

    public void loadMoreClubs(int current_page) {
        townyApi.getClubs(String.valueOf(current_page), preferences.getCurrentCity(), new BaseCallback<ClubsLoadedEvent>());
    }

    public void loadBars() {
        townyApi.getBars(preferences.getCurrentCity(), new BaseCallback<BarsLoadedEvent>());
    }

    public void loadMoreBars(int current_page) {
        townyApi.getBars(String.valueOf(current_page), preferences.getCurrentCity(), new BaseCallback<BarsLoadedEvent>());
    }

    public void loadPlacesNearMe(double longitude, double latitude) {
        townyApi.getPlacesNear(longitude, latitude, new BaseCallback<NearPlacesLoadedEvent>());
    }

    public void loadShops() {
        townyApi.getShops(preferences.getCurrentCity(), new BaseCallback<ShopsLoadedEvent>());
    }

    public void loadMoreShops(int current_page) {
        townyApi.getShops(String.valueOf(current_page), preferences.getCurrentCity(), new BaseCallback<ShopsLoadedEvent>());
    }

    public void loadAttractions() {
        townyApi.getAttractions(preferences.getCurrentCity(), new BaseCallback<AttractionsLoadedEvent>());
    }

    public void loadMoreAttractions(int current_page) {
        townyApi.getAttractions(String.valueOf(current_page), preferences.getCurrentCity(), new BaseCallback<AttractionsLoadedEvent>());
    }

    public void loadRecreations() {
        townyApi.getRecreations(preferences.getCurrentCity(), new BaseCallback<RecreationsLoadedEvent>());
    }

    public void loadMoreRecreations(int current_page) {
        townyApi.getRecreations(String.valueOf(current_page), preferences.getCurrentCity(), new BaseCallback<RecreationsLoadedEvent>());
    }
}
