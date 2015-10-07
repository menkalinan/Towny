package com.goldenpie.devs.kievrest.config;


import com.goldenpie.devs.kievrest.TownyApplication;
import com.goldenpie.devs.kievrest.ui.BaseListFragment;
import com.goldenpie.devs.kievrest.ui.activity.FirstRunActivity;
import com.goldenpie.devs.kievrest.ui.activity.InitActivity;
import com.goldenpie.devs.kievrest.ui.activity.MainActivity;
import com.goldenpie.devs.kievrest.ui.fragment.NewsFragment;
import com.goldenpie.devs.kievrest.ui.fragment.SelectionsFragment;
import com.goldenpie.devs.kievrest.utils.service.DataShareService;
import com.goldenpie.devs.kievrest.utils.service.TownyService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(TownyApplication townyApplication);

    void inject(MainActivity mainActivity);

    void inject(NewsFragment newsFragment);

    void inject(SelectionsFragment selectionsFragment);

    void inject(BaseListFragment baseListFragment);

    void inject(DataShareService dataShareService);

    void inject(FirstRunActivity firstRunActivity);

    void inject(TownyService townyService);

    void inject(InitActivity initActivity);

}
