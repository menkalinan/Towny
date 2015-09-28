package com.goldenpie.devs.kievrest.config;

import android.app.Application;
import android.content.Context;

import com.goldenpie.devs.kievrest.api.KievRestApi;
import com.goldenpie.devs.kievrest.api.WeatherApi;
import com.goldenpie.devs.kievrest.utils.DataHelper;
import com.goldenpie.devs.kievrest.utils.service.ApplicationPreferences;
import com.goldenpie.devs.kievrest.utils.service.KievRestService;
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
    public KievRestService provideKievRestService(KievRestApi kievRestApi, WeatherApi weatherApi) {
        return new KievRestService(kievRestApi , weatherApi);
    }

    @Provides
    @Singleton
    public ApplicationPreferences provideApplicationPreferences(Context context) {
        return new ApplicationPreferences(context);
    }

    @Provides
    @Singleton
    public KievRestApi provideRestService() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .disableHtmlEscaping()
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setConverter(new GsonConverter(gson))
                .setEndpoint(Constants.BASE_ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        return restAdapter.create(KievRestApi.class);
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
