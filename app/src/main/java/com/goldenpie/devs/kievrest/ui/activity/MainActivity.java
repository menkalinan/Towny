package com.goldenpie.devs.kievrest.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
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
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.MaterialViewPagerAnimator;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.config.Constants;
import com.goldenpie.devs.kievrest.event.WeatherLoadedEvent;
import com.goldenpie.devs.kievrest.models.CityModel;
import com.goldenpie.devs.kievrest.ui.BaseActivity;
import com.goldenpie.devs.kievrest.ui.fragment.NewsFragment;
import com.goldenpie.devs.kievrest.ui.fragment.SelectionsFragment;
import com.goldenpie.devs.kievrest.ui.fragment.events.ConcertsFragment;
import com.goldenpie.devs.kievrest.ui.fragment.events.EntertainmentFragment;
import com.goldenpie.devs.kievrest.ui.fragment.events.ExhibitionsFragment;
import com.goldenpie.devs.kievrest.ui.fragment.events.FestivalsFragment;
import com.goldenpie.devs.kievrest.ui.fragment.events.TheatersFragment;
import com.goldenpie.devs.kievrest.ui.fragment.events.YarmarkiFragment;
import com.goldenpie.devs.kievrest.ui.fragment.places.AttractionsFragment;
import com.goldenpie.devs.kievrest.ui.fragment.places.BarsFragment;
import com.goldenpie.devs.kievrest.ui.fragment.places.ClubsFragment;
import com.goldenpie.devs.kievrest.ui.fragment.places.HotelsFragment;
import com.goldenpie.devs.kievrest.ui.fragment.places.MuseumsFragment;
import com.goldenpie.devs.kievrest.ui.fragment.places.RecreationsFragment;
import com.goldenpie.devs.kievrest.ui.fragment.places.RestaurantsFragment;
import com.goldenpie.devs.kievrest.ui.fragment.places.ShopsFragment;
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
    protected Integer currentScreen;
    @State
    protected Integer currentPage;
    @State
    protected Parcelable viewPagerSate;
    @State
    protected Parcelable pagerSate;
    @State
    protected String currentCat;

    @Bind(R.id.materialViewPager)
    protected MaterialViewPager mViewPager;
    @Bind({R.id.nav_drawer_main_layout,
            R.id.nav_drawer_places_layout,
            R.id.nav_drawer_event_layout})
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
        if (currentScreen == null || currentScreen == 0)
            setMainViewPager();
        else if (currentScreen == 1)
            setPlacesViewPager();
        else if (currentScreen == 2)
            setEventViewPager();

        if (currentCat == null)
            currentCat = CategoryTypeEnum.MAIN.name();

        if (viewPagerSate != null) {
            mViewPager.getViewPager().onRestoreInstanceState(pagerSate);
            mViewPager.onRestoreInstanceState(viewPagerSate);
        }
    }

    @SuppressWarnings("unused")
    private void onEvent(ArrayList<CityModel> event) {
        helper.getDataMap().put(ModelTypeEnum.CITES, event);
    }

    private void setMainViewPager() {
        currentScreen = 0;
        updateDrawerItem(drawerItems.get(0));
        setTitle(getString(R.string.main));
        final boolean isNewYork = preferences.getCurrentCity().equals("new-york");
        final int count = !isNewYork ? 2 : 1;
        mViewPager.getViewPager().setAdapter(
                new FragmentStatePagerAdapter(getSupportFragmentManager()) {
                    @Override
                    public Fragment getItem(int position) {
                        switch (position) {
                            case 0:
                                return !isNewYork ? NewsFragment.newInstance()
                                        : SelectionsFragment.newInstance();
                            case 1:
                                return SelectionsFragment.newInstance();
                            default:
                                return null;
                        }
                    }

                    @Override
                    public int getCount() {
                        return count;
                    }

                    @Override
                    public CharSequence getPageTitle(int position) {
                        switch (position) {
                            case 0:
                                return !isNewYork ? getString(R.string.news) : getString(R.string.interesting);
                            case 1:
                                return getString(R.string.interesting);
                        }
                        return "";
                    }
                });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(final int page) {
                currentPage = page;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        YoYo.with(Techniques.ZoomOut).duration(200).playOn(headerLogoBackground);
                    }
                }, 50);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        switch (page) {
                            case 0:
                                headerLogoBackground.setBackgroundResource(R.drawable.blue_circle_drawable);
                                headerImage.setImageResource(!isNewYork ? R.drawable.news_icon : R.drawable.ic_action_whatshot);
                                break;
                            case 1:
                                headerLogoBackground.setBackgroundResource(R.drawable.purple_circle_drawable);
                                headerImage.setImageResource(R.drawable.ic_action_whatshot);
                                break;
                        }
                        YoYo.with(Techniques.ZoomIn).duration(200).playOn(headerLogoBackground);
                    }
                }, 250);

                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.dark_indigo,
                                "http://cdn01.wallconvert.com/_media/wallpapers_1920x1200/1/2/blurry-city-lights-14941.jpg");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.dark_purple,
                                "http://www.zastavki.com/pictures/originals/2013/Archive___Miscellaneous__039598_.jpg");
                }

                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());
    }

    private void setPlacesViewPager() {
        currentScreen = 1;
        updateDrawerItem(drawerItems.get(1));
        setTitle(getString(R.string.places));
        final int count = 8;
        mViewPager.getViewPager().setAdapter(
                new FragmentStatePagerAdapter(getSupportFragmentManager()) {
                    @Override
                    public Fragment getItem(int position) {
                        switch (position) {
                            case 0:
                                return RestaurantsFragment.newInstance();
                            case 1:
                                return BarsFragment.newInstance();
                            case 2:
                                return ClubsFragment.newInstance();
                            case 3:
                                return MuseumsFragment.newInstance();
                            case 4:
                                return AttractionsFragment.newInstance();
                            case 5:
                                return RecreationsFragment.newInstance();
                            case 6:
                                return ShopsFragment.newInstance();
                            case 7:
                                return HotelsFragment.newInstance();
                            default:
                                return null;
                        }
                    }

                    @Override
                    public int getCount() {
                        return count;
                    }

                    @Override
                    public CharSequence getPageTitle(int position) {
                        switch (position) {
                            case 0:
                                return getString(R.string.restaurants_and_cafe);
                            case 1:
                                return getString(R.string.bars);
                            case 2:
                                return getString(R.string.clubs);
                            case 3:
                                return getString(R.string.museums);
                            case 4:
                                return getString(R.string.attractions);
                            case 5:
                                return getString(R.string.active_rest);
                            case 6:
                                return getString(R.string.shops);
                            case 7:
                                return getString(R.string.hostels_and_hotels);
                        }
                        return "";
                    }
                });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(final int page) {
                currentPage = page;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        YoYo.with(Techniques.ZoomOut).duration(200).playOn(headerLogoBackground);
                    }
                }, 50);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        switch (page) {
                            case 0:
                                headerLogoBackground.setBackgroundResource(R.drawable.green_circle_drawable);
                                headerImage.setImageResource(R.drawable.ic_action_local_dining);
                                break;
                            case 1:
                                headerLogoBackground.setBackgroundResource(R.drawable.teal_circle_drawable);
                                headerImage.setImageResource(R.drawable.ic_action_local_bar);
                                break;
                            case 2:
                                headerLogoBackground.setBackgroundResource(R.drawable.light_blue_circle_drawable);
                                headerImage.setImageResource(R.drawable.ic_action_local_laundry_service);
                                break;
                            case 3:
                                headerLogoBackground.setBackgroundResource(R.drawable.orange_circle_drawable);
                                headerImage.setImageResource(R.drawable.ic_museum);
                                break;
                            case 4:
                                headerLogoBackground.setBackgroundResource(R.drawable.blue_circle_drawable);
                                headerImage.setImageResource(R.drawable.ic_action_attraction);
                                break;
                            case 5:
                                headerLogoBackground.setBackgroundResource(R.drawable.purple_circle_drawable);
                                headerImage.setImageResource(R.drawable.ic_action_directions_bike);
                                break;
                            case 6:
                                headerLogoBackground.setBackgroundResource(R.drawable.lime_circle_drawable);
                                headerImage.setImageResource(R.drawable.ic_action_store_mall_directory);
                                break;
                            case 7:
                                headerLogoBackground.setBackgroundResource(R.drawable.red_circle_drawable);
                                headerImage.setImageResource(R.drawable.ic_action_hotel);
                                break;
                        }
                        YoYo.with(Techniques.ZoomIn).duration(200).playOn(headerLogoBackground);
                    }
                }, 250);

                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.dark_green,
                                "http://www.vihomei.com/i/2015/09/ideas-of-restaurant-design-recessed-ceiling-lighting-oak-wooden-ceiling-decor-modest-wooden-table-denim-single-chairs-maple-wooden-flooring-interiors-of-restaurants-interior-gorgeous-interiors-of-res.jpg");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.dark_teal,
                                "https://venueviking.blob.core.windows.net/venueimagescontainer/7d26d06e-36c0-40e7-ba69-f59515b4d2a1.jpg");
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.dark_light_blue,
                                "http://mistoclub.com/wp-content/uploads/2014/03/bg_1.jpg");
                    case 3:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.dark_orange,
                                "https://kidsfuninseoul.files.wordpress.com/2011/06/img_1172.jpg");
                    case 4:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.dark_indigo,
                                "http://www.orangesmile.com/common/img_fotogallery/paris--1456928-0.jpg");
                    case 5:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.dark_purple,
                                "http://www.friendsoftunisia.org/wp-content/uploads/2014/05/central-park-wallpaper-new-york-central-park-hd-wallpaper-desktop-xgurpvbs.jpg");
                    case 6:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.dark_lime,
                                "http://i.toau-media.com/contentFiles/image/sydney/venues/shopping/doug-up-on-bourke.jpg");
                    case 7:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.dark_red,
                                "http://cdn.decordsgn.com/design/zeospot.com/wp-content/uploads/2010/11/luxury-hotel-interior-design.jpg");
                }

                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());
    }

    private void setEventViewPager() {
        currentScreen = 2;
        updateDrawerItem(drawerItems.get(2));
        setTitle("События");
        final int count = 6;
        mViewPager.getViewPager().setAdapter(
                new FragmentStatePagerAdapter(getSupportFragmentManager()) {
                    @Override
                    public Fragment getItem(int position) {
                        switch (position) {
                            case 0:
                                return ConcertsFragment.newInstance();
                            case 1:
                                return ExhibitionsFragment.newInstance();
                            case 2:
                                return TheatersFragment.newInstance();
                            case 3:
                                return FestivalsFragment.newInstance();
                            case 4:
                                return YarmarkiFragment.newInstance();
                            case 5:
                                return EntertainmentFragment.newInstance();
                            default:
                                return null;
                        }
                    }

                    @Override
                    public int getCount() {
                        return count;
                    }

                    @Override
                    public CharSequence getPageTitle(int position) {
                        switch (position) {
                            case 0:
                                return getString(R.string.concerts);
                            case 1:
                                return getString(R.string.exhibitions);
                            case 2:
                                return getString(R.string.theaters);
                            case 3:
                                return getString(R.string.festivals);
                            case 4:
                                return getString(R.string.yarmarki);
                            case 5:
                                return getString(R.string.entertaiment);
                        }
                        return "";
                    }
                });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(final int page) {
                currentPage = page;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        YoYo.with(Techniques.ZoomOut).duration(200).playOn(headerLogoBackground);
                    }
                }, 50);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        switch (page) {
                            case 0:
                                headerLogoBackground.setBackgroundResource(R.drawable.orange_circle_drawable);
                                headerImage.setImageResource(R.drawable.ic_action_mic);
                                break;
                            case 1:
                                headerLogoBackground.setBackgroundResource(R.drawable.lime_circle_drawable);
                                headerImage.setImageResource(R.drawable.ic_action_satellite);
                                break;
                            case 2:
                                headerLogoBackground.setBackgroundResource(R.drawable.green_circle_drawable);
                                headerImage.setImageResource(R.drawable.ic_action_local_activity);
                                break;
                            case 3:
                                headerLogoBackground.setBackgroundResource(R.drawable.red_circle_drawable);
                                headerImage.setImageResource(R.drawable.ic_action_today);
                                break;
                            case 4:
                                headerLogoBackground.setBackgroundResource(R.drawable.blue_circle_drawable);
                                headerImage.setImageResource(R.drawable.ic_action_card_giftcard);
                                break;
                            case 5:
                                headerLogoBackground.setBackgroundResource(R.drawable.teal_circle_drawable);
                                headerImage.setImageResource(R.drawable.ic_action_mood);
                                break;
                        }
                        YoYo.with(Techniques.ZoomIn).duration(200).playOn(headerLogoBackground);
                    }
                }, 250);

                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.dark_orange,
                                "http://cdn01.wallconvert.com/_media/wallpapers_1920x1200/1/2/blurry-city-lights-14941.jpg");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.dark_lime,
                                "http://www.zastavki.com/pictures/originals/2013/Archive___Miscellaneous__039598_.jpg");
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.dark_green,
                                "http://www.zastavki.com/pictures/originals/2013/Archive___Miscellaneous__039598_.jpg");
                    case 3:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.dark_red,
                                "http://www.zastavki.com/pictures/originals/2013/Archive___Miscellaneous__039598_.jpg");
                    case 4:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.dark_blue,
                                "http://www.zastavki.com/pictures/originals/2013/Archive___Miscellaneous__039598_.jpg");
                    case 5:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.dark_teal,
                                "http://www.zastavki.com/pictures/originals/2013/Archive___Miscellaneous__039598_.jpg");
                }

                return null;
            }
        });

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
        if (!currentCat.equals(CategoryTypeEnum.PLACES.name())) {
            currentCat = CategoryTypeEnum.PLACES.name();
            setViewPager(CategoryTypeEnum.PLACES);
        }
        mDrawer.closeDrawers();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.nav_drawer_main_layout)
    protected void onManeClick() {
        if (!currentCat.equals(CategoryTypeEnum.MAIN.name())) {
            currentCat = CategoryTypeEnum.MAIN.name();
            setViewPager(CategoryTypeEnum.MAIN);
        }
        mDrawer.closeDrawers();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.nav_drawer_event_layout)
    protected void onEventsClick() {
        if (!currentCat.equals(CategoryTypeEnum.EVENTS.name())) {
            currentCat = CategoryTypeEnum.EVENTS.name();
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
        mViewPager.postDelayed(new Runnable() {
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
                        break;
                    case PLACES:
                        setPlacesViewPager();
                        break;
                    case EVENTS:
                        setEventViewPager();
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
        Icepick.saveInstanceState(this, outState);
    }
}
