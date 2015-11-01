package com.goldenpie.devs.kievrest.utils;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.ui.fragment.FilmsFragment;
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

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class ViewPagerHelper {
    @Getter
    private final Context context;
    @Getter
    private int currentPage;
    @Setter
    private FrameLayout headerLogoBackground;
    @Setter
    private ImageView headerImage;

    @NonNull
    @Getter
    @Setter
    private FragmentManager supportFragmentManager;

    public ViewPagerHelper(Context context) {
        this.context = context;
    }

    public FragmentStatePagerAdapter getMainPagerAdapter(final int count, final boolean isNewYork) {
        return new FragmentStatePagerAdapter(getSupportFragmentManager()) {
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
        };
    }

    public MaterialViewPager.Listener getMainPagerListener(final boolean isNewYork) {
        return new MaterialViewPager.Listener() {
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
        };
    }

    public FragmentStatePagerAdapter getFilmsPagerAdapter(final int count) {
        return new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return FilmsFragment.newInstance();
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
                        return "Фильмы";
                }
                return "";
            }
        };
    }

    public MaterialViewPager.Listener getFilmsPagerListener() {
        return new MaterialViewPager.Listener() {
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
                        YoYo.with(Techniques.ZoomIn).duration(200).playOn(headerLogoBackground);
                    }
                }, 250);

                switch (page) {
                    case 0:
                        headerLogoBackground.setBackgroundResource(R.drawable.red_circle_drawable);
                        headerImage.setImageResource(R.drawable.ic_action_movie);
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.dark_red,
                                "http://www.44-16.com/wp-content/uploads/2013/09/IMG_3422.jpg");
                }

                return null;
            }
        };
    }

    public FragmentStatePagerAdapter getPlacesPagerAdapter(final int count) {
        return new FragmentStatePagerAdapter(getSupportFragmentManager()) {
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
        };
    }

    public MaterialViewPager.Listener getPlacesPagerListener() {
        return new MaterialViewPager.Listener() {
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
        };
    }

    public FragmentStatePagerAdapter getEventsPagerAdapter(final int count) {
        return new FragmentStatePagerAdapter(getSupportFragmentManager()) {
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
        };
    }


    public MaterialViewPager.Listener getEventsPagerListener() {
        return new MaterialViewPager.Listener() {
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
        };
    }

    private CharSequence getString(int concerts) {
        return getContext().getString(concerts);
    }

}
