package com.goldenpie.devs.kievrest.config;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;

public class Constants {
    public static final long DRAWER_ANIMATION_DURATION = 400L;

    public static final String DATA_ENDPOINT = "http://kudago.com";
    public static final String WEATHER_ENDPOINT = "http://api.openweathermap.org";

    public static final String CURRENT_WEATHER = "/data/2.5/weather";

    public static final String PUBLIC_API = "/public-api/v1/";
    public static final String EXTRA_FIELDS = "&fields=id,title,slug,adress,timetable,images,phone,body_text,site_url,foreign_url,coords,subway,is_closed,address&expand=images,place";
    public static final String EXTRA_EVENT_FIELDS = "&order_by=-publication_date&fields=age_restriction,description,is_free,dates,id,title,slug,images,body_text,site_url,foreign_url,publication_date,place&expand=images,place";

    public static final String SELECTION_LINK = PUBLIC_API + "lists/";
    public static final String MORE_SELECTIONS_LINK = PUBLIC_API + "lists/";
    public static final String CONCRETE_SELECTION_LINK = PUBLIC_API + "lists/{id}/?expand=place,images";

    public static final String NEWS_LINK = PUBLIC_API + "news/?fields=description,publication_date,title,id,place,images,body_text,site_url&expand=place,images";

    public static final String RESTAURANT_LINK = PUBLIC_API + "places/?categories=cafe,restaurants,anticafe,banquets,canteens,coffee,fastfood,roof,vegetarian" + EXTRA_FIELDS;
    public static final String MUSEUMS_LINK = PUBLIC_API + "places/?categories=museums" + EXTRA_FIELDS;
    public static final String CLUBS_LINK = PUBLIC_API + "places/?categories=clubs" + EXTRA_FIELDS;
    public static final String BARS_LINK = PUBLIC_API + "places/?categories=bar" + EXTRA_FIELDS;
    public static final String ATTRACTIONS_LINK = PUBLIC_API + "places/?categories=attract" + EXTRA_FIELDS;
    public static final String RECREATIONS_LINK = PUBLIC_API + "places/?categories=climbing-walls,diving,fitness,ice-rink,karts,kempingi,paintball,rollerdromes,slope,sport-centers,stable,swimming-pool,water-park,wind-tunnels" + EXTRA_FIELDS;
    public static final String SHOPS_LINK = PUBLIC_API + "places/?categories=shops,books,clothing,confectioneries,farmer-shops,flea-market,gifts,handmade,health-food,online-shopping,perfume-stores,shopping-mall,tea,toys" + EXTRA_FIELDS;
    public static final String HOTELS_LINK = PUBLIC_API + "places/?categories=hostels,hotels,inn" + EXTRA_FIELDS;

    public static final String EXHIBITIONS_LINK = PUBLIC_API + "events/?categories=exhibition" + EXTRA_EVENT_FIELDS;
    public static final String CONCERTS_LINK = PUBLIC_API + "events/?categories=concert" + EXTRA_EVENT_FIELDS;
    public static final String THEATERS_LINK = PUBLIC_API + "events/?categories=theater" + EXTRA_EVENT_FIELDS;
    public static final String FESTIVALS_LINK = PUBLIC_API + "events/?categories=festival" + EXTRA_EVENT_FIELDS;
    public static final String ENTERTAIMENT_LINK = PUBLIC_API + "events/?categories=circus,comedy-club,flashmob,games,kvn,magic,masquerade,quest,romance,show,speed-dating,stand-up" + EXTRA_EVENT_FIELDS;
    public static final String YARMARKI_LINK = PUBLIC_API + "events/?categories=yarmarki-razvlecheniya-yarmarki" + EXTRA_EVENT_FIELDS;

    public static final String PLACES_NEAR_LINK = PUBLIC_API + "places/?radius=5000&fields=id,title,adress,phone,coords,address";

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
