package com.goldenpie.devs.kievrest.api;

import com.goldenpie.devs.kievrest.config.Constants;
import com.goldenpie.devs.kievrest.event.AttractionsLoadedEvent;
import com.goldenpie.devs.kievrest.event.BarsLoadedEvent;
import com.goldenpie.devs.kievrest.event.NearPlacesLoadedEvent;
import com.goldenpie.devs.kievrest.event.NewsLoadedEvent;
import com.goldenpie.devs.kievrest.event.SelectionLoadedEvent;
import com.goldenpie.devs.kievrest.event.SelectionsLoadedEvent;
import com.goldenpie.devs.kievrest.event.events.ConcertsLoadedEvent;
import com.goldenpie.devs.kievrest.event.events.EntertainmentsLoadedEvent;
import com.goldenpie.devs.kievrest.event.events.ExhibitionsLoadedEvent;
import com.goldenpie.devs.kievrest.event.events.FestivalsLoadedEvent;
import com.goldenpie.devs.kievrest.event.events.TheatersLoadedEvent;
import com.goldenpie.devs.kievrest.event.events.YarmarkiLoadedEvent;
import com.goldenpie.devs.kievrest.event.places.ClubsLoadedEvent;
import com.goldenpie.devs.kievrest.event.places.HotelsLoadedEvent;
import com.goldenpie.devs.kievrest.event.places.MuseumsLoadedEvent;
import com.goldenpie.devs.kievrest.event.places.RecreationsLoadedEvent;
import com.goldenpie.devs.kievrest.event.places.RestaurantsLoadedEvent;
import com.goldenpie.devs.kievrest.event.places.ShopsLoadedEvent;
import com.goldenpie.devs.kievrest.models.CityModel;
import com.goldenpie.devs.kievrest.utils.BaseCallback;

import java.util.ArrayList;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface TownyApi {
    @GET(Constants.NEWS_LINK)
    void getNews(@Query("location") String currentCity,
                 BaseCallback<NewsLoadedEvent> callback);

    @GET(Constants.NEWS_LINK)
    void getNews(@Query("page") String page,
                 @Query("location") String currentCity,
                 BaseCallback<NewsLoadedEvent> callback);

    @GET(Constants.RESTAURANT_LINK)
    void getRestaurants(@Query("location") String currentCity,
                        BaseCallback<RestaurantsLoadedEvent> callback);

    @GET(Constants.RESTAURANT_LINK)
    void getRestaurants(@Query("page") String page,
                        @Query("location") String currentCity,
                        BaseCallback<RestaurantsLoadedEvent> callback);

    @GET(Constants.CONCRETE_SELECTION_LINK)
    void getSelection(@Path("id") String id,
                      @Query("location") String currentCity,
                      BaseCallback<SelectionLoadedEvent> callback);

    @GET(Constants.SELECTION_LINK)
    void getSelections(@Query("location") String currentCity,
                       BaseCallback<SelectionsLoadedEvent> callback);

    @GET(Constants.MORE_SELECTIONS_LINK)
    void getSelections(@Query("page") String page,
                       @Query("location") String currentCity,
                       BaseCallback<SelectionsLoadedEvent> callback);

    @GET(Constants.MUSEUMS_LINK)
    void getMuseums(@Query("page") String page,
                    @Query("location") String currentCity,
                    BaseCallback<MuseumsLoadedEvent> callback);

    @GET(Constants.CLUBS_LINK)
    void getClubs(@Query("page") String page,
                  @Query("location") String currentCity,
                  BaseCallback<ClubsLoadedEvent> callback);

    @GET(Constants.BARS_LINK)
    void getBars(@Query("page") String page,
                 @Query("location") String currentCity,
                 BaseCallback<BarsLoadedEvent> callback);

    @GET(Constants.MUSEUMS_LINK)
    void getMuseums(@Query("location") String currentCity,
                    BaseCallback<MuseumsLoadedEvent> callback);

    @GET(Constants.CLUBS_LINK)
    void getClubs(@Query("location") String currentCity,
                  BaseCallback<ClubsLoadedEvent> callback);

    @GET(Constants.BARS_LINK)
    void getBars(@Query("location") String currentCity,
                 BaseCallback<BarsLoadedEvent> callback);

    @GET(Constants.PLACES_NEAR_LINK)
    void getPlacesNear(@Query("lon") double longitude,
                       @Query("lat") double latitude,
                       BaseCallback<NearPlacesLoadedEvent> callback);

    @GET(Constants.RECREATIONS_LINK)
    void getRecreations(@Query("page") String page,
                        @Query("location") String currentCity,
                        BaseCallback<RecreationsLoadedEvent> callback);

    @GET(Constants.HOTELS_LINK)
    void getHotels(@Query("page") String page,
                   @Query("location") String currentCity,
                   BaseCallback<HotelsLoadedEvent> callback);

    @GET(Constants.ATTRACTIONS_LINK)
    void getAttractions(@Query("page") String page,
                        @Query("location") String currentCity,
                        BaseCallback<AttractionsLoadedEvent> callback);

    @GET(Constants.SHOPS_LINK)
    void getShops(@Query("page") String page,
                  @Query("location") String currentCity,
                  BaseCallback<ShopsLoadedEvent> callback);

    @GET(Constants.SHOPS_LINK)
    void getShops(@Query("location") String currentCity,
                  BaseCallback<ShopsLoadedEvent> callback);

    @GET(Constants.ATTRACTIONS_LINK)
    void getAttractions(@Query("location") String currentCity,
                        BaseCallback<AttractionsLoadedEvent> callback);

    @GET(Constants.RECREATIONS_LINK)
    void getRecreations(@Query("location") String currentCity,
                        BaseCallback<RecreationsLoadedEvent> callback);

    @GET(Constants.HOTELS_LINK)
    void getHotels(@Query("location") String currentCity,
                   BaseCallback<HotelsLoadedEvent> callback);

    @GET("/public-api/v1/locations/")
    void getSupportCites(@Query("lang") String lang,
                         BaseCallback<ArrayList<CityModel>> citesLoadedEventBaseCallback);


    @GET(Constants.EXHIBITIONS_LINK)
    void getExhibitions(@Query("page") String page,
                        @Query("location") String currentCity,
                        BaseCallback<ExhibitionsLoadedEvent> callback);

    @GET(Constants.EXHIBITIONS_LINK)
    void getExhibitions(@Query("location") String currentCity,
                        BaseCallback<ExhibitionsLoadedEvent> callback);

    @GET(Constants.CONCERTS_LINK)
    void getConcerts(@Query("page") String page,
                     @Query("location") String currentCity,
                     BaseCallback<ConcertsLoadedEvent> callback);

    @GET(Constants.CONCERTS_LINK)
    void getConcerts(@Query("location") String currentCity,
                     BaseCallback<ConcertsLoadedEvent> callback);

    @GET(Constants.FESTIVALS_LINK)
    void getFestivals(@Query("page") String page,
                      @Query("location") String currentCity,
                      BaseCallback<FestivalsLoadedEvent> callback);

    @GET(Constants.FESTIVALS_LINK)
    void getFestivals(@Query("location") String currentCity,
                      BaseCallback<FestivalsLoadedEvent> callback);

    @GET(Constants.THEATERS_LINK)
    void getTheaters(@Query("page") String page,
                      @Query("location") String currentCity,
                      BaseCallback<TheatersLoadedEvent> callback);

    @GET(Constants.THEATERS_LINK)
    void getTheaters(@Query("location") String currentCity,
                      BaseCallback<TheatersLoadedEvent> callback);

    @GET(Constants.YARMARKI_LINK)
    void getYarmarki(@Query("page") String page,
                      @Query("location") String currentCity,
                      BaseCallback<YarmarkiLoadedEvent> callback);

    @GET(Constants.YARMARKI_LINK)
    void getYarmarki(@Query("location") String currentCity,
                      BaseCallback<YarmarkiLoadedEvent> callback);

    @GET(Constants.ENTERTAIMENT_LINK)
    void getEntertainments(@Query("page") String page,
                      @Query("location") String currentCity,
                      BaseCallback<EntertainmentsLoadedEvent> callback);

    @GET(Constants.ENTERTAIMENT_LINK)
    void getEntertainments(@Query("location") String currentCity,
                      BaseCallback<EntertainmentsLoadedEvent> callback);
}
