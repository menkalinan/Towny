package com.goldenpie.devs.kievrest.ui;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.TownyApplication;
import com.goldenpie.devs.kievrest.config.Constants;
import com.goldenpie.devs.kievrest.event.WeatherLoadedEvent;
import com.goldenpie.devs.kievrest.ui.activity.MainActivity;
import com.goldenpie.devs.kievrest.ui.activity.SettingsActivity;
import com.goldenpie.devs.kievrest.utils.DataHelper;
import com.goldenpie.devs.kievrest.utils.DialogFactory;
import com.goldenpie.devs.kievrest.utils.ViewPagerHelper;
import com.goldenpie.devs.kievrest.utils.service.ApplicationPreferences;
import com.goldenpie.devs.kievrest.utils.service.TownyService;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import icepick.Icepick;
import lombok.Getter;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class BaseActivity extends AppCompatActivity {
    @Inject
    protected TownyService service;
    @Inject
    protected EventBus BUS;
    @Inject
    protected DataHelper helper;
    @Inject
    protected ApplicationPreferences preferences;
    @Inject
    protected ViewPagerHelper viewPagerHelper;
    @Inject
    protected LocationManager locationManager;

    private AlertDialog mLoadingDialog;

    @Nullable
    @Bind(R.id.drawer_layout)
    protected DrawerLayout mDrawer;
    @Nullable
    @Bind(R.id.materialViewPager)
    protected MaterialViewPager mViewPager;
    @Nullable
    @Bind({R.id.nav_drawer_main_layout,
            R.id.nav_drawer_places_layout,
            R.id.nav_drawer_event_layout,
            R.id.nav_drawer_films_layout,
            R.id.nav_drawer_near_places_layout})
    protected List<View> drawerItems;
    @Nullable
    @Bind(R.id.nav_drawer_header_image)
    protected ImageView drawerHeaderImage;
    @Nullable
    @Bind(R.id.nav_drawer_current_weather)
    protected IconTextView currentWeather;

    @Getter
    private boolean started;

    protected abstract int getContentView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        Icepick.restoreInstanceState(this, savedInstanceState);
        TownyApplication.appComponent().inject(this);
        BUS.register(this);
        ButterKnife.bind(this);

        if (drawerHeaderImage != null)
            Glide.with(this).load(helper.getWeatherImage()).fitCenter().into(drawerHeaderImage);
    }

    @Override
    protected void onStart() {
        super.onStart();
        started = true;
    }

    @Override
    protected void onDestroy() {
        BUS.unregister(this);
        super.onDestroy();
    }

    protected void hideLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing() && isStarted()) {
            mLoadingDialog.dismiss();
        }
    }

    protected void showLoadingDialog() {
        mLoadingDialog = DialogFactory.getLoadingDialog(this, R.string.loading)
                .setCancelable(true)
                .show();
    }

    public void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager
                    inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    protected void updateDrawerItem(View view) {
        clearAllDrawerItems();
        for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
            View v = ((ViewGroup) view).getChildAt(i);
            if (v instanceof TextView) {
                ((TextView) v).setTextColor(getResources().getColor(R.color.primary_dark));
            } else if (v instanceof AppCompatImageView) {
                ((AppCompatImageView) v).setColorFilter(getResources().getColor(R.color.primary_dark));
            }
        }
    }

    protected void clearAllDrawerItems() {
        for (int i = 0; i < drawerItems.size(); i++) {
            View item = drawerItems.get(i);
            for (int j = 0; j < ((ViewGroup) item).getChildCount(); j++) {
                View v = ((ViewGroup) item).getChildAt(j);
                if (v instanceof TextView) {
                    ((TextView) v).setTextColor(getResources().getColor(R.color.primary_text));
                } else if (v instanceof AppCompatImageView) {
                    ((AppCompatImageView) v).setColorFilter(getResources().getColor(R.color.secondary_text));
                }
            }
        }
    }

    @SuppressWarnings("unused")
    public void onEvent(WeatherLoadedEvent event) {
        if (!TextUtils.isEmpty(event.getWeatherData().getTemperature())) {
            currentWeather.setText(String.format(getString(R.string.current_weather),
                    preferences.getCurrentCityName(),
                    event.getWeatherInfo().get(0).getIcon(),
                    event.getWeatherData().getCurrentTemperature()));
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
