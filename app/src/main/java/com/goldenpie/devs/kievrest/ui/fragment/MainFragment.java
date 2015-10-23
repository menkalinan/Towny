package com.goldenpie.devs.kievrest.ui.fragment;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.ui.BaseCategoryFragment;

public class MainFragment extends BaseCategoryFragment {

    public static Fragment newInstance() {
        return new MainFragment();
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_viewpager_layout;
    }

    @Override
    protected String getTitle() {
        return getString(R.string.main);
    }

    @Override
    protected void setViewPagerContent() {
        final boolean isNewYork = preferences.getCurrentCity().equals("new-york");
        final int count = !isNewYork ? 2 : 1;
        mViewPager.getViewPager().setAdapter(
                new FragmentStatePagerAdapter(getChildFragmentManager()) {
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
}
