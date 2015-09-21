package com.goldenpie.devs.kievrest.config;


import com.goldenpie.devs.kievrest.KievRestApplication;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(KievRestApplication kievRestApplication);
}
