package com.goldenpie.devs.kievrest.config;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;

public class Constants {
    public static final String BASE_ENDPOINT = "http://kudago.com";
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
