package com.goldenpie.devs.kievrest.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goldenpie.devs.kievrest.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import lombok.Setter;

public class MiniMapFragment extends SupportMapFragment {

    @Setter
    private LatLng latLng;

    public static MiniMapFragment newInstance(LatLng position) {
        MiniMapFragment frag = new MiniMapFragment();
        frag.latLng = position;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View v = super.onCreateView(layoutInflater, viewGroup, bundle);
        initMap();
        return v;
    }

    private void initMap() {
        UiSettings settings = getMap().getUiSettings();
        settings.setAllGesturesEnabled(false);

        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
        getMap().addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_yellow)));
    }
} 