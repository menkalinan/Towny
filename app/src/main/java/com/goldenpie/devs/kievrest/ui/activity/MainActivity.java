package com.goldenpie.devs.kievrest.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.TintImageView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.goldenpie.devs.kievrest.KievRestApplication;
import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.config.Constants;
import com.goldenpie.devs.kievrest.event.WeatherLoadedEvent;
import com.goldenpie.devs.kievrest.ui.fragment.BarsFragment;
import com.goldenpie.devs.kievrest.ui.fragment.ClubsFragment;
import com.goldenpie.devs.kievrest.ui.fragment.MuseumsFragment;
import com.goldenpie.devs.kievrest.ui.fragment.NewsFragment;
import com.goldenpie.devs.kievrest.ui.fragment.RestaurantsFragment;
import com.goldenpie.devs.kievrest.ui.fragment.SelectionsFragment;
import com.goldenpie.devs.kievrest.utils.CategoryTypeEnum;
import com.goldenpie.devs.kievrest.utils.DataHelper;
import com.goldenpie.devs.kievrest.utils.service.KievRestService;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    @Bind({R.id.nav_drawer_main_layout
            , R.id.nav_drawer_places_layout})
    protected List<View> drawerItems;
    @Bind(R.id.materialViewPager)
    public MaterialViewPager mViewPager;
    @Bind(R.id.drawer_layout)
    protected DrawerLayout mDrawer;
    @Bind(R.id.header_logo_background)
    protected FrameLayout headerLogoBackground;
    @Bind(R.id.header_logo_image)
    protected ImageView headerImage;
    @Bind(R.id.nav_drawer_current_weather)
    protected TextView currentWeather;
    @Bind(R.id.nav_drawer_header_image)
    protected ImageView drawerHeaderImage;

    private String currentCat = CategoryTypeEnum.MAIN.name();

    @Inject
    protected KievRestService service;
    @Inject
    protected EventBus BUS;
    @Inject
    protected DataHelper helper;

    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        KievRestApplication.appComponent().inject(this);
        ButterKnife.bind(this);
        BUS.register(this);
        service.loadCurrentWeather();

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
        setMainViewPager();

        drawerHeaderImage.setImageResource(helper.getWeatherImage());
    }

    private void setMainViewPager() {
        updateDrawerItem(drawerItems.get(0));
        setTitle(getString(R.string.main));
        final int count = 2;
        mViewPager.getViewPager().setAdapter(
                new FragmentStatePagerAdapter(getSupportFragmentManager()) {
                    @Override
                    public Fragment getItem(int position) {
                        switch (position) {
                            case 0:
                                return NewsFragment.newInstance();
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
                                return getString(R.string.news);
                            case 1:
                                return getString(R.string.interesting);
                        }
                        return "";
                    }
                });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(final int page) {
                YoYo.with(Techniques.ZoomOut).duration(200).playOn(headerLogoBackground);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        switch (page) {
                            case 0:
                                headerLogoBackground.setBackgroundResource(R.drawable.news_circle_drawable);
                                headerImage.setImageResource(R.drawable.news_icon);
                                break;
                            case 1:
                                headerLogoBackground.setBackgroundResource(R.drawable.selection_circle_drawable);
                                headerImage.setImageResource(R.drawable.ic_action_whatshot);
                                break;
                        }
                        YoYo.with(Techniques.ZoomIn).duration(200).playOn(headerLogoBackground);
                    }
                }, 200);

                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.dark_indigo,
                                "http://static.vueling.com/cms/media/1216777/kiev.jpg");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.dark_purple,
                                "http://relax.com.ua/wp-content/media/kiew/2012/09/kiev-at-night.jpg");
                }

                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());
    }

    private void setPlacesViewPager() {
        updateDrawerItem(drawerItems.get(1));
        setTitle(getString(R.string.places));
        final int count = 4;
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
                                return SelectionsFragment.newInstance();
                            case 5:
                                return SelectionsFragment.newInstance();
                            case 6:
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
                        }
                        return "";
                    }
                });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(final int page) {
                YoYo.with(Techniques.ZoomOut).duration(200).playOn(headerLogoBackground);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        switch (page) {
                            case 0:
                                headerLogoBackground.setBackgroundResource(R.drawable.restuarants_circle_drawable);
                                headerImage.setImageResource(R.drawable.ic_action_local_dining);
                                break;
                            case 1:
                                headerLogoBackground.setBackgroundResource(R.drawable.bars_circle_drawable);
                                headerImage.setImageResource(R.drawable.ic_action_local_bar);
                                break;
                            case 2:
                                headerLogoBackground.setBackgroundResource(R.drawable.clubs_circle_drawable);
                                headerImage.setImageResource(R.drawable.ic_action_local_laundry_service);
                                break;
                            case 3:
                                headerLogoBackground.setBackgroundResource(R.drawable.selection_circle_drawable);
                                headerImage.setImageResource(R.drawable.ic_museum);
                                break;
                            case 4:
                                headerLogoBackground.setBackgroundResource(R.drawable.news_circle_drawable);
                                headerImage.setImageResource(R.drawable.ic_action_attraction);
                                break;
                            case 5:
                                headerLogoBackground.setBackgroundResource(R.drawable.selection_circle_drawable);
                                headerImage.setImageResource(R.drawable.ic_action_directions_bike);
                                break;
                            case 6:
                                headerLogoBackground.setBackgroundResource(R.drawable.shops_circle_drawable);
                                headerImage.setImageResource(R.drawable.ic_action_store_mall_directory);
                                break;
                        }
                        YoYo.with(Techniques.ZoomIn).duration(200).playOn(headerLogoBackground);
                    }
                }, 200);

                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.dark_green,
                                "http://www.omilos.com.gr/img/header5.jpg");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.dark_teal,
                                "http://relax.com.ua/wp-content/media/kiew/2012/09/kiev-at-night.jpg");
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.dark_orange,
                                "http://static.vueling.com/cms/media/1216777/kiev.jpg");
                    case 3:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.dark_light_blue,
                                "http://relax.com.ua/wp-content/media/kiew/2012/09/kiev-at-night.jpg");
                    case 4:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.dark_indigo,
                                "http://static.vueling.com/cms/media/1216777/kiev.jpg");
                    case 5:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.dark_purple,
                                "http://relax.com.ua/wp-content/media/kiew/2012/09/kiev-at-night.jpg");
                    case 6:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.dark_lime,
                                "http://static.vueling.com/cms/media/1216777/kiev.jpg");
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
            } else if (v instanceof TintImageView) {
                ((TintImageView) v).setColorFilter(getResources().getColor(R.color.primary_dark));
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
                } else if (v instanceof TintImageView) {
                    ((TintImageView) v).setColorFilter(getResources().getColor(R.color.secondary_text));
                }
            }
        }
    }

    @OnClick(R.id.nav_drawer_places_layout)
    protected void onPlacesClick() {
        if (!currentCat.equals(CategoryTypeEnum.PLACES.name())) {
            currentCat = CategoryTypeEnum.PLACES.name();
            mViewPager.postDelayed(new Runnable() {
                @Override
                public void run() {
                    MaterialViewPagerHelper.getAnimator(MainActivity.this).restoreScroll(0, null);
                }
            }, Constants.DRAWER_ANIMATION_DURATION - 50L);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setPlacesViewPager();
                    mViewPager.notifyHeaderChanged();
                }
            }, Constants.DRAWER_ANIMATION_DURATION);
        }
        mDrawer.closeDrawers();
    }

    @OnClick(R.id.nav_drawer_main_layout)
    protected void onManeClick() {
        if (!currentCat.equals(CategoryTypeEnum.MAIN.name())) {
            currentCat = CategoryTypeEnum.MAIN.name();
            mViewPager.postDelayed(new Runnable() {
                @Override
                public void run() {
                    MaterialViewPagerHelper.getAnimator(MainActivity.this).restoreScroll(0, null);
                }
            }, Constants.DRAWER_ANIMATION_DURATION - 50L);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setMainViewPager();
                    mViewPager.notifyHeaderChanged();
                }
            }, Constants.DRAWER_ANIMATION_DURATION);
        }
        mDrawer.closeDrawers();
    }
}
