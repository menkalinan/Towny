package com.goldenpie.devs.kievrest.config;


import com.goldenpie.devs.kievrest.TownyApplication;
import com.goldenpie.devs.kievrest.ui.BaseActivity;
import com.goldenpie.devs.kievrest.ui.BaseListFragment;
import com.goldenpie.devs.kievrest.ui.activity.InitActivity;
import com.goldenpie.devs.kievrest.ui.adapter.PlacesAdapter;
import com.goldenpie.devs.kievrest.utils.service.DataShareService;
import com.goldenpie.devs.kievrest.utils.service.TownyService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(TownyApplication townyApplication);

    void inject(BaseListFragment baseListFragment);

    void inject(DataShareService dataShareService);

    void inject(TownyService townyService);

    void inject(InitActivity initActivity);

    void inject(BaseActivity baseActivity);

    void inject(PlacesAdapter placesAdapter);
}
