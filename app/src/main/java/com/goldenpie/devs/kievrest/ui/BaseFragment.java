package com.goldenpie.devs.kievrest.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.goldenpie.devs.kievrest.KievRestApplication;
import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.event.NetworkErrorEvent;
import com.goldenpie.devs.kievrest.utils.DataHelper;
import com.goldenpie.devs.kievrest.utils.service.KievRestService;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public abstract class BaseFragment extends Fragment{

    @Inject
    protected DataHelper helper;
    @Inject
    protected KievRestService service;
    @Inject
    protected EventBus BUS;

    @Bind(R.id.no_internet_layout)
    protected RelativeLayout noInternetLayout;
    @Bind(R.id.progressBar)
    protected ProgressBar progressBar;

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

    protected abstract int getContentView();
    protected void reload(){
        showLoader();
    };

    public void onEvent(NetworkErrorEvent errorEvent){
        if(noInternetLayout.getVisibility() == View.GONE) {
            noInternetLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    protected void showLoader(){
        if(noInternetLayout.getVisibility() == View.VISIBLE) {
            noInternetLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

}
