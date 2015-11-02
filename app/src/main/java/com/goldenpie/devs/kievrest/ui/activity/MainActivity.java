package com.goldenpie.devs.kievrest.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.github.florent37.materialviewpager.MaterialViewPagerAnimator;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.config.Constants;
import com.goldenpie.devs.kievrest.models.CityModel;
import com.goldenpie.devs.kievrest.ui.BaseActivity;
import com.goldenpie.devs.kievrest.utils.CategoryTypeEnum;
import com.goldenpie.devs.kievrest.utils.ModelTypeEnum;
import com.goldenpie.devs.kievrest.utils.service.DataShareService;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import icepick.Icepick;
import icepick.State;

@SuppressWarnings("ConstantConditions")
public class MainActivity extends BaseActivity {

    @State
    protected Integer currentPage;
    @State
    protected Parcelable viewPagerSate;
    @State
    protected Parcelable pagerSate;
    @State
    protected CategoryTypeEnum currentCat = CategoryTypeEnum.MAIN;

    @Bind(R.id.header_logo_background)
    protected FrameLayout headerLogoBackground;
    @Bind(R.id.header_logo_image)
    protected ImageView headerImage;

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

        mViewPager.getPagerTitleStrip().setIndicatorColor(isNewYork ? 0 : getResources().getColor(R.color.white));
    }

    private void setPlacesViewPager() {
        setTitle(getString(R.string.places));
        int count = 8;

        mViewPager.getViewPager().setAdapter(viewPagerHelper.getPlacesPagerAdapter(count));
        mViewPager.setMaterialViewPagerListener(viewPagerHelper.getPlacesPagerListener());
        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        mViewPager.getPagerTitleStrip().setIndicatorColor(getResources().getColor(R.color.white));
    }

    private void setEventViewPager() {
        setTitle(getString(R.string.events));
        final int count = 6;

        mViewPager.getViewPager().setAdapter(viewPagerHelper.getEventsPagerAdapter(count));
        mViewPager.setMaterialViewPagerListener(viewPagerHelper.getEventsPagerListener());
        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        mViewPager.getPagerTitleStrip().setIndicatorColor(getResources().getColor(R.color.white));
    }

    private void setFilmsViewPager() {
        setTitle(getString(R.string.films));
        final int count = 1;

        mViewPager.getViewPager().setAdapter(viewPagerHelper.getFilmsPagerAdapter(count));
        mViewPager.setMaterialViewPagerListener(viewPagerHelper.getFilmsPagerListener());
        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        mViewPager.getPagerTitleStrip().setIndicatorColor(0);
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

    @SuppressWarnings("unused")
    @OnClick(R.id.nav_drawer_places_layout)
    protected void onPlacesClick() {
        if (currentCat != CategoryTypeEnum.PLACES) {
            currentCat = CategoryTypeEnum.PLACES;
            setViewPager(CategoryTypeEnum.PLACES, true);
        }
        mDrawer.closeDrawers();
    }


    @SuppressWarnings("unused")
    @OnClick(R.id.nav_drawer_main_layout)
    protected void onManeClick() {
        if (currentCat != CategoryTypeEnum.MAIN) {
            currentCat = CategoryTypeEnum.MAIN;
            setViewPager(CategoryTypeEnum.MAIN, true);
        }
        mDrawer.closeDrawers();
    }


    @SuppressWarnings("unused")
    @OnClick(R.id.nav_drawer_event_layout)
    protected void onEventsClick() {
        if (currentCat != CategoryTypeEnum.EVENTS) {
            currentCat = CategoryTypeEnum.EVENTS;
            setViewPager(CategoryTypeEnum.EVENTS, true);
        }
        mDrawer.closeDrawers();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.nav_drawer_films_layout)
    protected void onFilmsClick() {
        if (currentCat != CategoryTypeEnum.FILMS) {
            currentCat = CategoryTypeEnum.FILMS;
            setViewPager(CategoryTypeEnum.FILMS, true);
        }
        mDrawer.closeDrawers();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.nav_drawer_near_places_layout)
    protected void onMapsClick() {
        final Intent i = new Intent(MainActivity.this, MapsActivity.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivityForResult(i, MapsActivity.MAPS_RESULT);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }, Constants.DRAWER_ANIMATION_DURATION);
        mDrawer.closeDrawers();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.nav_drawer_settings_layout)
    protected void oSettingsClick() {
        final Intent i = new Intent(this, SettingsActivity.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, 0);
            }
        }, Constants.DRAWER_ANIMATION_DURATION);
        mDrawer.closeDrawers();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == MapsActivity.MAPS_RESULT){
            if(resultCode == RESULT_CANCELED){
                this.finish();
            } else if (resultCode == RESULT_OK){
                setViewPager((CategoryTypeEnum) data.getSerializableExtra(MapsActivity.CHOOSEN_CAT), false);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setViewPager(final CategoryTypeEnum cat, boolean needWait) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MaterialViewPagerHelper.getAnimator(MainActivity.this).restoreScroll(0, null);
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
        }, needWait ? Constants.DRAWER_ANIMATION_DURATION : 0);

//        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
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
