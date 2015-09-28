package com.goldenpie.devs.kievrest.api;

import com.goldenpie.devs.kievrest.config.Constants;
import com.goldenpie.devs.kievrest.event.NewsLoadedEvent;
import com.goldenpie.devs.kievrest.event.RestaurantsLoadedEvent;
import com.goldenpie.devs.kievrest.event.SelectionLoadedEvent;
import com.goldenpie.devs.kievrest.event.SelectionsLoadedEvent;
import com.goldenpie.devs.kievrest.utils.BaseCallback;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface KievRestApi {
    @GET(Constants.NEWS_LINK)
    void getNews(BaseCallback<NewsLoadedEvent> callback);

    @GET(Constants.MORE_NEWS_LINK)
    void getNews(@Query("page") String page, BaseCallback<NewsLoadedEvent> callback);

    @GET(Constants.RESTAURANT_LINK)
    void getRestaurants(BaseCallback<RestaurantsLoadedEvent> callback);

    @GET(Constants.MORE_RESTAURANTS_LINK)
    void getRestaurants(@Query("page") String page, BaseCallback<RestaurantsLoadedEvent> callback);

    @GET(Constants.CONCRETE_SELECTION_LINK)
    void getSelection(@Path("id") String id, BaseCallback<SelectionLoadedEvent> callback);

    @GET(Constants.SELECTION_LINK)
    void getSelections(BaseCallback<SelectionsLoadedEvent> callback);

    @GET(Constants.MORE_SELECTIONS_LINK)
    void getSelections(@Query("page") String page, BaseCallback<SelectionsLoadedEvent> callback);
}
