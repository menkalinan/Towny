package com.goldenpie.devs.kievrest.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.Data;

@Data
public class WeatherModel {
    @SerializedName("coord")
    private CoordinatesModel coordinates;
    @SerializedName("main")
    private WeatherDataModel weatherData;
    @SerializedName("weather")
    private ArrayList<WeatherInfoModel> weatherInfo;
    @SerializedName("visibility")
    private String visibility;

    @Data
    public class WeatherDataModel {
        @SerializedName("temp")
        private String temperature;
        @SerializedName("pressure")
        private String pressure;
        @SerializedName("humidity")
        private String humidity;
        @SerializedName("temp_min")
        private String minTemperature;
        @SerializedName("temp_max")
        private String maxTemperature;

        public String getCurrentTemperature() {
            return String.valueOf(Math.round(Double.parseDouble(getTemperature()) - 273.0));
        }
        public String getCelsiusMaxTemperature() {
            return String.valueOf(Double.parseDouble(getMaxTemperature()) - 273.0);
        }
        public String getCelsiusMinTemperature() {
            return String.valueOf(Double.parseDouble(getMinTemperature()) - 273.0);
        }
    }

    @Data
    public class WeatherInfoModel {
        @SerializedName("description")
        private String description;

        public String getFinalDescription() {
            return description.substring(0, 1).toUpperCase() + description.substring(1);
        }
    }
}
