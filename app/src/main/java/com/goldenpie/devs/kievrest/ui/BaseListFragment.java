package com.goldenpie.devs.kievrest.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.goldenpie.devs.kievrest.KievRestApplication;
import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.event.ErrorEvent;
import com.goldenpie.devs.kievrest.event.NetworkErrorEvent;
import com.goldenpie.devs.kievrest.utils.DataHelper;
import com.goldenpie.devs.kievrest.utils.service.ApplicationPreferences;
import com.goldenpie.devs.kievrest.utils.service.KievRestService;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public abstract class BaseListFragment extends Fragment{

    @Inject
    protected DataHelper helper;
    @Inject
    protected KievRestService service;
    @Inject
    protected ApplicationPreferences preferences;
    @Inject
    protected EventBus BUS;

    @Bind(R.id.no_internet_layout)
    protected RelativeLayout noInternetLayout;
    @Bind(R.id.progressBar)
    protected ProgressBar progressBar;
    @Bind(R.id.swipe_refresh_layout)
    protected SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KievRestApplication.appComponent().inject(this);
        BUS.register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(getContentView(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setProgressViewOffset(false, 0,
                getActivity().getResources().getDimensionPixelSize(R.dimen.scroll_offset));
    }

    protected abstract int getContentView();
    protected void reload(){
        showLoader();
    }


    protected void showError(){
        noInternetLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    protected void showLoader(){
        noInternetLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

}
