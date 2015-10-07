package com.goldenpie.devs.kievrest.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.event.NetworkErrorEvent;
import com.goldenpie.devs.kievrest.models.CityModel;
import com.goldenpie.devs.kievrest.ui.BaseActivity;
import com.nineoldandroids.animation.Animator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;
import fr.ganfra.materialspinner.MaterialSpinner;

public class FirstRunActivity extends BaseActivity {

    @Bind(R.id.act_first_run_city_spinner)
    protected MaterialSpinner spinner;
    @Bind(R.id.act_first_run_error_layout)
    protected RelativeLayout errorLayout;
    @Bind(R.id.act_first_run_loading_layout)
    protected RelativeLayout loadingLayout;
    @Bind(R.id.act_first_run_sucsess_layout)
    protected RelativeLayout successLayout;
    @Bind(R.id.act_first_run_no_internet_layout)
    protected RelativeLayout noInternetLayout;
    @Bind(R.id.act_first_run_go)
    protected ImageView goView;
    private LocationManager locationManager;

    @Override
    protected int getContentView() {
        return R.layout.activity_first_run;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service.loadCites(getLang());
    }

    private String getLang() {
        preferences.setLang(Locale.getDefault().getLanguage().equals("ru") ? "ru" : "en");
        return preferences.getLang();
    }

    public void onEvent(ArrayList<CityModel> event) {
        checkCity(event);
    }

    private void checkCity(ArrayList<CityModel> event) {
        boolean cityExist = false;
        if (!TextUtils.isEmpty(getLocation())) {
            String location = getLocation();
            for (int i = 0; i < event.size(); i++) {
                if (event.get(i).getName().equals(location)) {
                    preferences.setCurrentCity(event.get(i).getSlug());
                    preferences.setCurrentCityName(event.get(i).getName());
                    cityExist = true;
                    break;
                }
            }
        }

        if (cityExist) {
            delayedAnimation(successLayout);
        } else {
            delayedAnimation(errorLayout);
            requestChoose(event);
        }
    }

    private void requestChoose(final ArrayList<CityModel> event) {
        final String[] ITEMS = new String[event.size()];
        for (int i = 0; i < event.size(); i++) {
            ITEMS[i] = event.get(i).getName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final boolean[] initial = {false};

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (initial[0]) {
                    for (int j = 0; j < event.size(); j++) {
                        if (event.get(j).getName().equals(ITEMS[i])) {
                            preferences.setCurrentCity(event.get(j).getSlug());
                            preferences.setCurrentCityName(event.get(j).getName());
                            YoYo.with(Techniques.FadeIn).duration(300).playOn(goView);
                            goView.setVisibility(View.VISIBLE);

                            break;
                        }
                    }
                }
                initial[0] = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @SuppressWarnings("unused")
    @OnClick({R.id.act_first_run_no_internet_image, R.id.act_first_run_no_internet_text})
    protected void onRepeatClick() {
        YoYo.with(Techniques.FadeOut).withListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                noInternetLayout.setVisibility(View.GONE);
                YoYo.with(Techniques.FadeIn).duration(300).playOn(loadingLayout);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).duration(300L).playOn(noInternetLayout);

        loadingLayout.setVisibility(View.VISIBLE);
        service.loadCites(getLang());
    }

    private void startApplication(boolean needDelay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(FirstRunActivity.this, MainActivity.class));
                FirstRunActivity.this.finish();
            }
        }, needDelay ? 2500L : 0);
    }

    private void delayedAnimation(final View view) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                YoYo.with(Techniques.FadeOut).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        loadingLayout.setVisibility(View.GONE);
                        view.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.FadeIn).duration(300).playOn(view);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).duration(300L).playOn(loadingLayout);
            }
        }, 1500L);

    }

    @SuppressWarnings("unused")
    public void onEvent(NetworkErrorEvent errorEvent) {
        delayedAnimation(noInternetLayout);
    }


    @SuppressWarnings("unused")
    @OnClick({R.id.act_first_run_go, R.id.act_first_run_second_go})
    protected void onGoClick() {
        startApplication(false);
    }

    @SuppressWarnings("ResourceType")
    private String getLocation() {
        if (locationManager == null)
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (network_enabled) {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Geocoder gcd = new Geocoder(this, Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            if (addresses.size() > 0) {
                return addresses.get(0).getLocality();
            }
        }
        return null;
    }

}
