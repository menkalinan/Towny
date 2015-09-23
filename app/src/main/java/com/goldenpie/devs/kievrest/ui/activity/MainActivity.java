package com.goldenpie.devs.kievrest.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.goldenpie.devs.kievrest.KievRestApplication;
import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.ui.fragment.NewsFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.materialViewPager)
    protected MaterialViewPager mViewPager;
    @Bind(R.id.drawer_layout)
    protected DrawerLayout mDrawer;
    @Bind(R.id.header_logo_background)
    protected FrameLayout headerLogoBackground;
    @Bind(R.id.header_logo_image)
    protected ImageView headerImage;

    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        KievRestApplication.appComponent().inject(this);
        ButterKnife.bind(this);

        toolbar = mViewPager.getToolbar();

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
    }

    private void setMainViewPager() {
        setTitle("Главная");
        final int count = 2;
        mViewPager.getViewPager().setAdapter(
                new FragmentStatePagerAdapter(getSupportFragmentManager()) {
                    @Override
                    public Fragment getItem(int position) {
                        switch (position) {
                            //case 0:
                            //    return RecyclerViewFragment.newInstance();
                            //case 1:
                            //    return RecyclerViewFragment.newInstance();
                            //case 2:
                            //    return WebViewFragment.newInstance();
                            default:
                                return NewsFragment.newInstance();
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
                                return "Новости";
                            case 1:
                                return "Интересное";
                        }
                        return "";
                    }
                });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        headerLogoBackground.setBackground(
                                getApplicationContext().getDrawable(R.drawable.news_circle_drawable));
                        headerImage.setImageDrawable(getApplicationContext().getDrawable(R.drawable.news_icon));
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.dark_indigo,
                                "http://static.vueling.com/cms/media/1216777/kiev.jpg");
                    case 1:
                        headerLogoBackground.setBackground(
                            getApplicationContext().getDrawable(R.drawable.selection_circle_drawable));
                        headerImage.setImageDrawable(getApplicationContext().getDrawable(R.drawable.ic_action_whatshot));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

}
