package com.goldenpie.devs.kievrest.config;


import com.goldenpie.devs.kievrest.KievRestApplication;
import com.goldenpie.devs.kievrest.ui.activity.MainActivity;
import com.goldenpie.devs.kievrest.ui.fragment.NewsFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(KievRestApplication kievRestApplication);

    void inject(MainActivity mainActivity);

    void inject(NewsFragment newsFragment);
}
