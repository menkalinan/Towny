package com.goldenpie.devs.kievrest.config;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;

public class Constants {
    public static final String BASE_ENDPOINT = "http://kudago.com";
    public static final String WEATHER_ENDPOINT = "http://api.openweathermap.org";
    public static final String SELECTION_LINK = "/public-api/v1/lists/?location=kev";
    public static final String CONCRETE_SELECTION_LINK = "/public-api/v1/lists/{id}/?expand=place,images";
    public static final String NEWS_LINK = "/public-api/v1/news/?location=kev&fields=description,publication_date,title,id,place,images,body_text,site_url&expand=place,images";
    public static final String CURRENT_WEATHER = "/data/2.5/weather?q=Kiev,ua&lang=ru";
    public static final String MORE_NEWS_LINK = "/public-api/v1/news/?location=kev&fields=description,publication_date,title,id,place,images,body_text,site_url&expand=place,images";
    public static final String MORE_SELECTIONS_LINK = "/public-api/v1/lists/?location=kev";
    public static final String RESTAURANT_LINK = "/public-api/v1/places/?location=kev&categories=cafe,restaurants&fields=id,title,slug,adress,timetable,images,phone,body_text,site_url,foreign_url,coords,subway,is_closed,address&expand=images";
    public static final String MORE_RESTAURANTS_LINK = "/public-api/v1/places/?location=kev&categories=cafe,restaurants&fields=id,title,slug,adress,timetable,images,phone,body_text,site_url,foreign_url,coords,subway,is_closed,address&expand=images";
    public static final long DRAWER_ANIMATION_DURATION = 400L;

    public static DateFormatSymbols dateFormatSymbols = new DateFormatSymbols() {
        @Override
        public String[] getMonths() {
            return new String[]{"января", "февраля", "марта", "апреля", "мая", "июня",
                    "июля", "августа", "сентября", "октября", "ноября", "декабря"};
        }
    };
    public static final SimpleDateFormat dateFormat =
            new SimpleDateFormat("d MMMM, H:m", dateFormatSymbols);
}
