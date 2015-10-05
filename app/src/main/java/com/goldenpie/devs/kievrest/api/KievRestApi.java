package com.goldenpie.devs.kievrest.api;

import com.goldenpie.devs.kievrest.config.Constants;
import com.goldenpie.devs.kievrest.event.AttractionsLoadedEvent;
import com.goldenpie.devs.kievrest.event.BarsLoadedEvent;
import com.goldenpie.devs.kievrest.event.ClubsLoadedEvent;
import com.goldenpie.devs.kievrest.event.MuseumsLoadedEvent;
import com.goldenpie.devs.kievrest.event.NewsLoadedEvent;
import com.goldenpie.devs.kievrest.event.RecreationsLoadedEvent;
import com.goldenpie.devs.kievrest.event.RestaurantsLoadedEvent;
import com.goldenpie.devs.kievrest.event.SelectionLoadedEvent;
import com.goldenpie.devs.kievrest.event.SelectionsLoadedEvent;
import com.goldenpie.devs.kievrest.event.ShopsLoadedEvent;
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

    @GET(Constants.RESTAURANT_LINK)
    void getRestaurants(@Query("page") String page, BaseCallback<RestaurantsLoadedEvent> callback);

    @GET(Constants.CONCRETE_SELECTION_LINK)
    void getSelection(@Path("id") String id, BaseCallback<SelectionLoadedEvent> callback);

    @GET(Constants.SELECTION_LINK)
    void getSelections(BaseCallback<SelectionsLoadedEvent> callback);

    @GET(Constants.MORE_SELECTIONS_LINK)
    void getSelections(@Query("page") String page, BaseCallback<SelectionsLoadedEvent> callback);

    @GET(Constants.MUSEUMS_LINK)
    void getMuseums(@Query("page") String page, BaseCallback<MuseumsLoadedEvent> callback);

    @GET(Constants.CLUBS_LINK)
    void getClubs(@Query("page") String page, BaseCallback<ClubsLoadedEvent> callback);

    @GET(Constants.BARS_LINK)
    void getBars(@Query("page") String page, BaseCallback<BarsLoadedEvent> callback);

    @GET(Constants.MUSEUMS_LINK)
    void getMuseums(BaseCallback<MuseumsLoadedEvent> callback);

    @GET(Constants.CLUBS_LINK)
    void getClubs(BaseCallback<ClubsLoadedEvent> callback);

    @GET(Constants.BARS_LINK)
    void getBars(BaseCallback<BarsLoadedEvent> callback);

    @GET(Constants.RECREATIONS_LINK)
    void getRecreations(@Query("page") String page, BaseCallback<RecreationsLoadedEvent> callback);

    @GET(Constants.ATTRACTIONS_LINK)
    void getAttractions(@Query("page") String page, BaseCallback<AttractionsLoadedEvent> callback);

    @GET(Constants.SHOPS_LINK)
    void getShops(@Query("page") String page, BaseCallback<ShopsLoadedEvent> callback);

    @GET(Constants.SHOPS_LINK)
    void getShops(BaseCallback<ShopsLoadedEvent> callback);

    @GET(Constants.ATTRACTIONS_LINK)
    void getAttractions(BaseCallback<AttractionsLoadedEvent> callback);

    @GET(Constants.RECREATIONS_LINK)
    void getRecreations(BaseCallback<RecreationsLoadedEvent> callback);
}
