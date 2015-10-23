package com.goldenpie.devs.kievrest.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.config.Constants;
import com.goldenpie.devs.kievrest.event.WeatherLoadedEvent;
import com.goldenpie.devs.kievrest.models.CityModel;
import com.goldenpie.devs.kievrest.ui.BaseActivity;
import com.goldenpie.devs.kievrest.ui.fragment.EventsFragment;
import com.goldenpie.devs.kievrest.ui.fragment.MainFragment;
import com.goldenpie.devs.kievrest.ui.fragment.PlacesFragment;
import com.goldenpie.devs.kievrest.utils.CategoryTypeEnum;
import com.goldenpie.devs.kievrest.utils.ModelTypeEnum;
import com.goldenpie.devs.kievrest.utils.service.DataShareService;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import icepick.Icepick;
import icepick.State;
import lombok.Getter;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends BaseActivity {

    @Bind({R.id.nav_drawer_main_layout,
            R.id.nav_drawer_places_layout,
            R.id.nav_drawer_event_layout})
    protected List<View> drawerItems;
    @Bind(R.id.drawer_layout)
    protected DrawerLayout mDrawer;
    @Bind(R.id.nav_drawer_current_weather)
    protected IconTextView currentWeather;
    @Bind(R.id.nav_drawer_header_image)
    protected ImageView drawerHeaderImage;

    @Getter
    @Bind(R.id.toolbar)
    protected Toolbar toolbar;

    private ActionBarDrawerToggle mDrawerToggle;

    @State
    protected CategoryTypeEnum currentCat = CategoryTypeEnum.MAIN;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        service.loadCurrentWeather();
        service.loadCites(preferences.getLang());

        Intent i = new Intent(this, DataShareService.class);
        startService(i);

        if (toolbar != null) {
            setSupportActionBar(toolbar);

            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(true);
            }
        }

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, 0, 0);
        mDrawer.setDrawerListener(mDrawerToggle);

        setViewPager(currentCat);

        Glide.with(this).load(helper.getWeatherImage()).fitCenter().into(drawerHeaderImage);
    }

    @SuppressWarnings("unused")
    private void onEvent(ArrayList<CityModel> event) {
        helper.getDataMap().put(ModelTypeEnum.CITES, event);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                service.loadCurrentWeather();
                break;
        }
        return mDrawerToggle.onOptionsItemSelected(item) ||
                super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void updateDrawerItem(View view) {
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

    private void clearAllDrawerItems() {
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
    @OnClick(R.id.nav_drawer_places_layout)
    protected void onPlacesClick() {
        if (currentCat != CategoryTypeEnum.PLACES) {
            currentCat = CategoryTypeEnum.PLACES;
            setViewPager(CategoryTypeEnum.PLACES);
        }
        mDrawer.closeDrawers();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.nav_drawer_main_layout)
    protected void onManeClick() {
        if (currentCat != CategoryTypeEnum.MAIN) {
            currentCat = CategoryTypeEnum.MAIN;
            setViewPager(CategoryTypeEnum.MAIN);
        }
        mDrawer.closeDrawers();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.nav_drawer_event_layout)
    protected void onEventsClick() {
        if (currentCat != CategoryTypeEnum.EVENTS) {
            currentCat = CategoryTypeEnum.EVENTS;
            setViewPager(CategoryTypeEnum.EVENTS);
        }
        mDrawer.closeDrawers();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.nav_drawer_settings_layout)
    protected void oSettingsClick() {
        final Intent i = new Intent(MainActivity.this, SettingsActivity.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }, Constants.DRAWER_ANIMATION_DURATION);
        mDrawer.closeDrawers();
    }

    private void setViewPager(final CategoryTypeEnum cat) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (cat) {
                    case MAIN:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                .replace(R.id.container, MainFragment.newInstance())
                                .commit();
                        updateDrawerItem(drawerItems.get(0));
                        break;
                    case PLACES:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                .replace(R.id.container, PlacesFragment.newInstance())
                                .commit();
                        updateDrawerItem(drawerItems.get(1));
                        break;
                    case EVENTS:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                .replace(R.id.container, EventsFragment.newInstance())
                                .commit();
                        updateDrawerItem(drawerItems.get(2));
                        break;
                }
            }
        }, Constants.DRAWER_ANIMATION_DURATION);

        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }
}
