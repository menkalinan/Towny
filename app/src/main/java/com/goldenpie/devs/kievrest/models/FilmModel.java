package com.goldenpie.devs.kievrest.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class FilmModel extends BaseDataModel {

    @SerializedName("is_editors_choice")
    protected String isEditorsChoice;

    @SerializedName("genres")
    protected ArrayList<Genre> genres;

    @SerializedName("original_title")
    protected String originalTitle;

    @SerializedName("locale")
    protected String locale;

    @SerializedName("country")
    protected String country;

    @SerializedName("year")
    protected int year;

    @SerializedName("language")
    protected String language;

    @SerializedName("running_time")
    protected int runningTime;

    @SerializedName("budget_currency")
    protected String budgetCurrency;

    @SerializedName("budget")
    protected String budget;

    @SerializedName("mpaa_rating")
    protected String mpaaRating;

    @SerializedName("age_restriction")
    protected Integer ageRestriction;

    @SerializedName("stars")
    protected String stars;

    @SerializedName("director")
    protected String director;

    @SerializedName("writer")
    protected String writer;

    @SerializedName("awards")
    protected String awards;

    @SerializedName("trailer")
    protected String trailer;

    @SerializedName("poster")
    protected PhotosModel poster;

    @SerializedName("imdb_url")
    protected String imdbUrl;

    @SerializedName("imdb_rating")
    protected String imdbRating;

//    public String getRunningTime() {
//        return String.valueOf(runningTime / 60.0);
//    }

    public String getLocaleTitle() {
        return Locale.getDefault().getLanguage().equals("ru") ? getTitle() : getOriginalTitle();
    }

    @Data
    public class Genre {
        @SerializedName("name")
        protected String name;
        @SerializedName("slug")
        protected String slug;
    }

}
