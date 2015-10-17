package com.goldenpie.devs.kievrest.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.goldenpie.devs.kievrest.TownyApplication;
import com.goldenpie.devs.kievrest.utils.DataHelper;
import com.goldenpie.devs.kievrest.utils.service.ApplicationPreferences;
import com.goldenpie.devs.kievrest.utils.service.TownyService;

import javax.inject.Inject;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import icepick.Icepick;

public abstract class BaseActivity extends AppCompatActivity {
    @Inject
    protected TownyService service;
    @Inject
    protected EventBus BUS;
    @Inject
    protected DataHelper helper;
    @Inject
    protected ApplicationPreferences preferences;

    protected abstract int getContentView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        Icepick.restoreInstanceState(this, savedInstanceState);
        TownyApplication.appComponent().inject(this);
        BUS.register(this);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        BUS.unregister(this);
        super.onDestroy();
    }
}
