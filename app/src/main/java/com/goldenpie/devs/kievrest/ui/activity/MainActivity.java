package com.goldenpie.devs.kievrest.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.MaterialViewPagerAnimator;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.config.Constants;
import com.goldenpie.devs.kievrest.event.WeatherLoadedEvent;
import com.goldenpie.devs.kievrest.models.CityModel;
import com.goldenpie.devs.kievrest.ui.BaseActivity;
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
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends BaseActivity {

    @State
    protected Integer currentPage;
    @State
    protected Parcelable viewPagerSate;
    @State
    protected Parcelable pagerSate;
    @State
    protected CategoryTypeEnum currentCat = CategoryTypeEnum.MAIN;

    @Bind(R.id.materialViewPager)
    protected MaterialViewPager mViewPager;
    @Bind({R.id.nav_drawer_main_layout,
            R.id.nav_drawer_places_layout,
            R.id.nav_drawer_event_layout,
            R.id.nav_drawer_films_layout})
    protected List<View> drawerItems;
    @Bind(R.id.drawer_layout)
    protected DrawerLayout mDrawer;
    @Bind(R.id.header_logo_background)
    protected FrameLayout headerLogoBackground;
    @Bind(R.id.header_logo_image)
    protected ImageView headerImage;
    @Bind(R.id.nav_drawer_current_weather)
    protected IconTextView currentWeather;
    @Bind(R.id.nav_drawer_header_image)
    protected ImageView drawerHeaderImage;

    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service.loadCurrentWeather();
        service.loadCites(preferences.getLang());

        viewPagerHelper.setHeaderImage(headerImage);
        viewPagerHelper.setHeaderLogoBackground(headerLogoBackground);
        viewPagerHelper.setSupportFragmentManager(getSupportFragmentManager());

        MaterialViewPagerAnimator.ENABLE_LOG = false;

        Intent i = new Intent(this, DataShareService.class);
        startService(i);

        Toolbar toolbar = mViewPager.getToolbar();

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

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, 0, 0);
        mDrawer.setDrawerListener(mDrawerToggle);

        restoreState();

        Glide.with(this).load(helper.getWeatherImage()).fitCenter().into(drawerHeaderImage);
    }

    private void restoreState() {
        switch (currentCat) {
            case MAIN:
                setMainViewPager();
                updateDrawerItem(drawerItems.get(0));
                break;
            case PLACES:
                setPlacesViewPager();
                updateDrawerItem(drawerItems.get(1));
                break;
            case EVENTS:
                setEventViewPager();
                updateDrawerItem(drawerItems.get(2));
                break;
            case FILMS:
                setFilmsViewPager();
                updateDrawerItem(drawerItems.get(3));
                break;
        }

        if (viewPagerSate != null) {
            mViewPager.getViewPager().onRestoreInstanceState(pagerSate);
            mViewPager.onRestoreInstanceState(viewPagerSate);
            mViewPager.getViewPager().setCurrentItem(currentPage);
        }
    }

    @SuppressWarnings("unused")
    private void onEvent(ArrayList<CityModel> event) {
        helper.getDataMap().put(ModelTypeEnum.CITES, event);
    }

    private void setMainViewPager() {
        setTitle(getString(R.string.main));
        boolean isNewYork = preferences.getCurrentCity().equals("new-york");
        int count = !isNewYork ? 2 : 1;

        mViewPager.getViewPager().setAdapter(viewPagerHelper.getMainPagerAdapter(count, isNewYork));
        mViewPager.setMaterialViewPagerListener(viewPagerHelper.getMainPagerListener(isNewYork));
        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());
    }

    private void setPlacesViewPager() {
        setTitle(getString(R.string.places));
        int count = 8;

        mViewPager.getViewPager().setAdapter(viewPagerHelper.getPlacesPagerAdapter(count));
        mViewPager.setMaterialViewPagerListener(viewPagerHelper.getPlacesPagerListener());
        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());
    }

    private void setEventViewPager() {
        setTitle(getString(R.string.events));
        final int count = 6;

        mViewPager.getViewPager().setAdapter(viewPagerHelper.getEventsPagerAdapter(count));
        mViewPager.setMaterialViewPagerListener(viewPagerHelper.getEventsPagerListener());
        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());
    }

    private void setFilmsViewPager() {
        setTitle(getString(R.string.films));
        final int count = 1;

        mViewPager.getViewPager().setAdapter(viewPagerHelper.getFilmsPagerAdapter(count));
        mViewPager.setMaterialViewPagerListener(viewPagerHelper.getFilmsPagerListener());
        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());
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
    @OnClick(R.id.nav_drawer_films_layout)
    protected void onFilmsClick() {
        if (currentCat != CategoryTypeEnum.FILMS) {
            currentCat = CategoryTypeEnum.FILMS;
            setViewPager(CategoryTypeEnum.FILMS);
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
                MaterialViewPagerHelper.getAnimator(MainActivity.this).restoreScroll(0, null);
            }
        }, Constants.DRAWER_ANIMATION_DURATION - 50L);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (cat) {
                    case MAIN:
                        setMainViewPager();
                        updateDrawerItem(drawerItems.get(0));
                        break;
                    case PLACES:
                        setPlacesViewPager();
                        updateDrawerItem(drawerItems.get(1));
                        break;
                    case EVENTS:
                        setEventViewPager();
                        updateDrawerItem(drawerItems.get(2));
                        break;
                    case FILMS:
                        setFilmsViewPager();
                        updateDrawerItem(drawerItems.get(3));
                        break;
                }
                mViewPager.notifyHeaderChanged();
            }
        }, Constants.DRAWER_ANIMATION_DURATION);


        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        viewPagerSate = mViewPager.onSaveInstanceState();
        pagerSate = mViewPager.getViewPager().onSaveInstanceState();
        currentPage = viewPagerHelper.getCurrentPage();
        Icepick.saveInstanceState(this, outState);
    }
}
