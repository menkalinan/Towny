package com.goldenpie.devs.kievrest.config;

import android.app.Application;
import android.content.Context;
import android.location.LocationManager;

import com.goldenpie.devs.kievrest.api.TownyApi;
import com.goldenpie.devs.kievrest.api.WeatherApi;
import com.goldenpie.devs.kievrest.utils.DataHelper;
import com.goldenpie.devs.kievrest.utils.ViewPagerHelper;
import com.goldenpie.devs.kievrest.utils.service.ApplicationPreferences;
import com.goldenpie.devs.kievrest.utils.service.TownyService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.greenrobot.event.EventBus;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

@Module
@SuppressWarnings({"unused", "deprecation"})
public class AppModule {
    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    public DataHelper provideDataHelper() {
        return new DataHelper();
    }

    @Provides
    @Singleton
    public EventBus providesEventBus() {
        return EventBus.getDefault();
    }

    @Provides
    @Singleton
    public TownyService provideKievRestService(TownyApi townyApi, WeatherApi weatherApi) {
        return new TownyService(townyApi, weatherApi);
    }

    @Provides
    @Singleton
    public ViewPagerHelper provideViewPagerHelper(Context context) {
        return new ViewPagerHelper(context);
    }

    @Provides
    @Singleton
    public ApplicationPreferences provideApplicationPreferences(Context context) {
        return new ApplicationPreferences(context);
    }
    @Provides
    @Singleton
    public LocationManager provideLocationManager(Context context) {
        return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    @Provides
    @Singleton
    public TownyApi provideRestService() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .disableHtmlEscaping()
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setConverter(new GsonConverter(gson))
                .setEndpoint(Constants.DATA_ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        return restAdapter.create(TownyApi.class);
    }

    @Provides
    @Singleton
    public WeatherApi provideWeatherService() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .disableHtmlEscaping()
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setConverter(new GsonConverter(gson))
                .setEndpoint(Constants.WEATHER_ENDPOINT)
                .build();

        return restAdapter.create(WeatherApi.class);
    }
}
