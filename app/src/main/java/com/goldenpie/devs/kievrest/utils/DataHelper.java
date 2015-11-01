package com.goldenpie.devs.kievrest.utils;

import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.models.CityModel;
import com.goldenpie.devs.kievrest.models.EventModel;
import com.goldenpie.devs.kievrest.models.FilmModel;
import com.goldenpie.devs.kievrest.models.NewsModel;
import com.goldenpie.devs.kievrest.models.PlaceModel;
import com.goldenpie.devs.kievrest.models.SelectionModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;

public class DataHelper {
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Getter
    @Setter
    private HashMap<ModelTypeEnum, ArrayList<?>> dataMap = new HashMap<>();

    @Getter
    @Setter
    private boolean isSelectionTotallyLoaded;

    @SuppressWarnings("unchecked")
    public ArrayList<NewsModel> getNewsList() {
        return (ArrayList<NewsModel>) dataMap.get(ModelTypeEnum.NEWS);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<SelectionModel> getSelectionsList() {
        return (ArrayList<SelectionModel>) dataMap.get(ModelTypeEnum.SELECTIONS);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<PlaceModel> getRestaurantsList() {
        return (ArrayList<PlaceModel>) dataMap.get(ModelTypeEnum.RESTAURANTS);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<PlaceModel> getClubsList() {
        return (ArrayList<PlaceModel>) dataMap.get(ModelTypeEnum.CLUBS);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<PlaceModel> getMuseumsList() {
        return (ArrayList<PlaceModel>) dataMap.get(ModelTypeEnum.MUSEUMS);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<PlaceModel> getBarsList() {
        return (ArrayList<PlaceModel>) dataMap.get(ModelTypeEnum.BARS);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<PlaceModel> getShopsList() {
        return (ArrayList<PlaceModel>) dataMap.get(ModelTypeEnum.SHOPS);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<PlaceModel> getRecreationsList() {
        return (ArrayList<PlaceModel>) dataMap.get(ModelTypeEnum.RECREATIONS);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<PlaceModel> getAttractionsList() {
        return (ArrayList<PlaceModel>) dataMap.get(ModelTypeEnum.ATTRACTIONS);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<CityModel> getCitesList() {
        return (ArrayList<CityModel>) dataMap.get(ModelTypeEnum.CITES);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<PlaceModel> getHotelsList() {
        return (ArrayList<PlaceModel>) dataMap.get(ModelTypeEnum.HOTELS);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<EventModel> getExhibitionsList() {
        return (ArrayList<EventModel>) dataMap.get(ModelTypeEnum.EXHIBITIONS);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<EventModel> getConcertsList() {
        return (ArrayList<EventModel>) dataMap.get(ModelTypeEnum.CONCERTS);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<EventModel> getFestivalsList() {
        return (ArrayList<EventModel>) dataMap.get(ModelTypeEnum.FESTIVALS);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<EventModel> getTheatersList() {
        return (ArrayList<EventModel>) dataMap.get(ModelTypeEnum.THEATERS);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<EventModel> getYarmarkiList() {
        return (ArrayList<EventModel>) dataMap.get(ModelTypeEnum.YARMARKI);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<EventModel> getEntertainmentsList() {
        return (ArrayList<EventModel>) dataMap.get(ModelTypeEnum.ENTERTAINMENT);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<FilmModel> getFilmsList() {
        return (ArrayList<FilmModel>) dataMap.get(ModelTypeEnum.FILMS);
    }

    public int getWeatherImage() {
        Calendar calendar = new GregorianCalendar();
        switch (calendar.get(Calendar.MONTH)) {
            case Calendar.JANUARY:
                return R.drawable.bkg_01_january;
            case Calendar.FEBRUARY:
                return R.drawable.bkg_02_february;
            case Calendar.MARCH:
                return R.drawable.bkg_03_march;
            case Calendar.APRIL:
                return R.drawable.bkg_04_april;
            case Calendar.MAY:
                return R.drawable.bkg_05_may;
            case Calendar.JUNE:
                return R.drawable.bkg_06_june;
            case Calendar.JULY:
                return R.drawable.bkg_07_july;
            case Calendar.AUGUST:
                return R.drawable.bkg_08_august;
            case Calendar.SEPTEMBER:
                return R.drawable.bkg_09_september;
            case Calendar.OCTOBER:
                return R.drawable.bkg_10_october;
            case Calendar.NOVEMBER:
                return R.drawable.bkg_11_november;
            case Calendar.DECEMBER:
                return R.drawable.bkg_12_december;
            default:
                return 0;
        }
    }

}
