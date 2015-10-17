package com.goldenpie.devs.kievrest;

import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.goldenpie.devs.kievrest.config.AppComponent;
import com.goldenpie.devs.kievrest.config.AppModule;
import com.goldenpie.devs.kievrest.config.DaggerAppComponent;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.WeathericonsModule;

import io.fabric.sdk.android.Fabric;
import lombok.Getter;
import lombok.Setter;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class TownyApplication extends android.app.Application {
    @Getter
    @Setter
    private static Context context;

    private AppComponent appComponent;

    public static AppComponent appComponent() {
        return ((TownyApplication) getContext().getApplicationContext()).appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/PTSansRegular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        Iconify.with(new WeathericonsModule());
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        appComponent.inject(this);

        if (!BuildConfig.DEBUG)
            Fabric.with(this, new Crashlytics());

        setContext(this);
    }

}
