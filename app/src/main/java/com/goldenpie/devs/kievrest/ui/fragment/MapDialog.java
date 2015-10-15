package com.goldenpie.devs.kievrest.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goldenpie.devs.kievrest.R;
import com.google.android.gms.maps.model.LatLng;

import butterknife.Bind;
import butterknife.ButterKnife;
import lombok.Setter;

public class MapDialog extends DialogFragment {
    @Bind(R.id.title)
    protected TextView labelTextView;
    @Setter
    private LatLng latLng;
    @Setter
    private String label;

    public static MapDialog newInstance() {
        return new MapDialog();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(STYLE_NO_TITLE);
        return View.inflate(getContext(), R.layout.dialog_map, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        getChildFragmentManager().beginTransaction().replace(R.id.container,
                MiniMapFragment.newInstance(latLng)).commit();
        labelTextView.setText(label);

    }
}
