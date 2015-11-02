package com.goldenpie.devs.kievrest.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.config.Constants;
import com.goldenpie.devs.kievrest.event.ErrorEvent;
import com.goldenpie.devs.kievrest.event.NearPlacesLoadedEvent;
import com.goldenpie.devs.kievrest.models.PlaceModel;
import com.goldenpie.devs.kievrest.ui.BaseActivity;
import com.goldenpie.devs.kievrest.utils.CategoryTypeEnum;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import lombok.Getter;

public class MapsActivity extends BaseActivity implements OnMapReadyCallback {

    public static final int MAPS_RESULT = 666;
    public static final String CHOOSEN_CAT = "some_cat";

    @Getter
    private ArrayList<PlaceModel> places = new ArrayList<>();
    private Location location;

    @Bind(R.id.toolbar)
    protected Toolbar toolbar;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected int getContentView() {
        return R.layout.activity_maps;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLoadingDialog();

        service.loadCurrentWeather();

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
        updateDrawerItem(drawerItems.get(4));
        toolbar.setBackgroundColor(0);
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        service.loadPlacesNearMe(location.getLongitude(), location.getLatitude());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
        setTitle("");
    }

    public void onEvent(NearPlacesLoadedEvent event) {
        getPlaces().addAll(event.getResults());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

    @OnClick({R.id.nav_drawer_main_layout,
            R.id.nav_drawer_places_layout,
            R.id.nav_drawer_event_layout,
            R.id.nav_drawer_films_layout})
    protected void onDrawerItemClick(View v) {
        Intent i = new Intent();
        switch (v.getId()) {
            case R.id.nav_drawer_main_layout:
                i.putExtra(CHOOSEN_CAT, CategoryTypeEnum.MAIN);
                break;
            case R.id.nav_drawer_places_layout:
                i.putExtra(CHOOSEN_CAT, CategoryTypeEnum.PLACES);
                break;
            case R.id.nav_drawer_event_layout:
                i.putExtra(CHOOSEN_CAT, CategoryTypeEnum.EVENTS);
                break;
            case R.id.nav_drawer_films_layout:
                i.putExtra(CHOOSEN_CAT, CategoryTypeEnum.FILMS);
                break;
        }
        setResult(RESULT_OK, i);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MapsActivity.this.finish();
                overridePendingTransition(0, android.R.anim.fade_out);
            }
        }, Constants.DRAWER_ANIMATION_DURATION);
        mDrawer.closeDrawers();
    }

    @OnClick(R.id.nav_drawer_near_places_layout)
    protected void onThisPageClick() {
        mDrawer.closeDrawers();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
        overridePendingTransition(0, android.R.anim.fade_out);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.nav_drawer_settings_layout)
    protected void oSettingsClick() {
        final Intent i = new Intent(this, SettingsActivity.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }, Constants.DRAWER_ANIMATION_DURATION);
        mDrawer.closeDrawers();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        hideLoadingDialog();
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        for (int i = 0; i < getPlaces().size(); i++) {
            googleMap.addMarker(new MarkerOptions().position(getPlaces().get(i).getCoordinates().getCoordinates())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_yellow))
                    .title(getPlaces().get(i).getTitle()));
        }
        googleMap.addCircle(new CircleOptions()
                .center(new LatLng(location.getLatitude(), location.getLongitude()))
                .radius(2000)
                .strokeColor(Color.parseColor("#90FFC107"))
                .fillColor(Color.parseColor("#1500BCD4")));
    }

    public void onEvent(ErrorEvent event) {
        //TODO
    }
}
