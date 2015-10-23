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
import com.goldenpie.devs.kievrest.ui.fragment.events.ConcertsFragment;
import com.goldenpie.devs.kievrest.ui.fragment.events.EntertainmentFragment;
import com.goldenpie.devs.kievrest.ui.fragment.events.ExhibitionsFragment;
import com.goldenpie.devs.kievrest.ui.fragment.events.FestivalsFragment;
import com.goldenpie.devs.kievrest.ui.fragment.events.TheatersFragment;
import com.goldenpie.devs.kievrest.ui.fragment.events.YarmarkiFragment;

public class EventsFragment extends BaseCategoryFragment {


    public static Fragment newInstance() {
        return new EventsFragment();
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_viewpager_layout;
    }

    @Override
    protected String getTitle() {
        return getString(R.string.events);
    }

    @Override
    protected void setViewPagerContent() {
        final int count = 6;
        mViewPager.getViewPager().setAdapter(
                new FragmentStatePagerAdapter(getChildFragmentManager()) {
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
}
