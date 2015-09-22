package com.goldenpie.devs.kievrest;

import android.content.Context;

import com.goldenpie.devs.kievrest.config.AppComponent;
import com.goldenpie.devs.kievrest.config.AppModule;
import com.goldenpie.devs.kievrest.config.DaggerAppComponent;

import lombok.Getter;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class KievRestApplication extends android.app.Application {

    @Getter
    private static Context context;
    private AppComponent appComponent;

    public static AppComponent appComponent() {
        return ((KievRestApplication) getContext().getApplicationContext()).appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        appComponent.inject(this);
        Fabric.with(this, new Crashlytics());

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/HelveticaNeue.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
