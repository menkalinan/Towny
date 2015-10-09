package com.goldenpie.devs.kievrest.config;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;

public class Constants {
    public static final String BASE_ENDPOINT = "http://kudago.com";
    public static final String WEATHER_ENDPOINT = "http://api.openweathermap.org";
    public static final String SELECTION_LINK = "/public-api/v1/lists/";
    public static final String CONCRETE_SELECTION_LINK = "/public-api/v1/lists/{id}/?expand=place,images";
    public static final String NEWS_LINK = "/public-api/v1/news/?fields=description,publication_date,title,id,place,images,body_text,site_url&expand=place,images";
    public static final String CURRENT_WEATHER = "/data/2.5/weather";
    public static final String MORE_NEWS_LINK = "/public-api/v1/news/?fields=description,publication_date,title,id,place,images,body_text,site_url&expand=place,images";
    public static final String MORE_SELECTIONS_LINK = "/public-api/v1/lists/";
    public static final String RESTAURANT_LINK = "/public-api/v1/places/?categories=cafe,restaurants,anticafe,banquets,canteens,coffee,fastfood,roof,vegetarian&fields=id,title,slug,adress,timetable,images,phone,body_text,site_url,foreign_url,coords,subway,is_closed,address&expand=images";
    public static final long DRAWER_ANIMATION_DURATION = 400L;
    public static final String MUSEUMS_LINK = "/public-api/v1/places/?categories=museums&fields=id,title,slug,adress,timetable,images,phone,body_text,site_url,foreign_url,coords,subway,is_closed,address&expand=images";
    public static final String CLUBS_LINK = "/public-api/v1/places/?categories=clubs&fields=id,title,slug,adress,timetable,images,phone,body_text,site_url,foreign_url,coords,subway,is_closed,address&expand=images";
    public static final String BARS_LINK = "/public-api/v1/places/?categories=bar&fields=id,title,slug,adress,timetable,images,phone,body_text,site_url,foreign_url,coords,subway,is_closed,address&expand=images";
    public static final String ATTRACTIONS_LINK = "/public-api/v1/places/?categories=attract&fields=id,title,slug,adress,timetable,images,phone,body_text,site_url,foreign_url,coords,subway,is_closed,address&expand=images";
    public static final String RECREATIONS_LINK = "/public-api/v1/places/?categories=climbing-walls,diving,fitness,ice-rink,karts,kempingi,paintball,rollerdromes,slope,sport-centers,stable,swimming-pool,water-park,wind-tunnels&fields=id,title,slug,adress,timetable,images,phone,body_text,site_url,foreign_url,coords,subway,is_closed,address&expand=images";
    public static final String SHOPS_LINK = "/public-api/v1/places/?categories=shops,books,clothing,confectioneries,farmer-shops,flea-market,gifts,handmade,health-food,online-shopping,perfume-stores,shopping-mall,tea,toys&fields=id,title,slug,adress,timetable,images,phone,body_text,site_url,foreign_url,coords,subway,is_closed,address&expand=images";

    public static final String PLACES_NEAR_LINK = "/public-api/v1/places/?radius=5000&fields=id,title,adress,phone,coords,address";
    public static final String OPENWEATHERMAP_API_KEY = "ddd1ba0b2cf758da5561f7e418a26926";

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
