package com.goldenpie.devs.kievrest.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.goldenpie.devs.kievrest.TownyApplication;
import com.goldenpie.devs.kievrest.utils.service.ApplicationPreferences;

import javax.inject.Inject;

public class InitActivity extends Activity {

    @Inject
    protected ApplicationPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TownyApplication.appComponent().inject(this);

        if (preferences.isFirstLaunch())
            startActivity(new Intent(this, FirstRunActivity.class));
        else startActivity(new Intent(this, MainActivity.class));

        finish();
    }
}
