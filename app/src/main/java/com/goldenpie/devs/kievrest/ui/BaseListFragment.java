package com.goldenpie.devs.kievrest.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.TownyApplication;
import com.goldenpie.devs.kievrest.event.BaseLoadedEvent;
import com.goldenpie.devs.kievrest.event.ErrorEvent;
import com.goldenpie.devs.kievrest.event.NetworkErrorEvent;
import com.goldenpie.devs.kievrest.ui.listener.EndlessRecyclerOnScrollListener;
import com.goldenpie.devs.kievrest.utils.DataHelper;
import com.goldenpie.devs.kievrest.utils.ModelTypeEnum;
import com.goldenpie.devs.kievrest.utils.ScreenUtils;
import com.goldenpie.devs.kievrest.utils.service.ApplicationPreferences;
import com.goldenpie.devs.kievrest.utils.service.TownyService;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import lombok.Getter;
import lombok.Setter;

public abstract class BaseListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @Inject
    protected DataHelper helper;
    @Inject
    protected TownyService service;
    @Inject
    protected ApplicationPreferences preferences;
    @Inject
    protected EventBus BUS;

    @Bind(R.id.frag_news_list)
    protected RecyclerView list;
    @Bind(R.id.no_internet_layout)
    protected RelativeLayout noInternetLayout;
    @Bind(R.id.progressBar)
    protected ProgressBar progressBar;
    @Bind(R.id.swipe_refresh_layout)
    protected SwipeRefreshLayout swipeRefreshLayout;
    @Getter
    private StaggeredGridLayoutManager linearLayoutManager;

    private EndlessRecyclerOnScrollListener listener;
    protected boolean isFromReload = false;

    @Setter
    @Getter
    private int nextPage = -1;

    @Setter
    @Getter
    private boolean hasNextPage = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TownyApplication.appComponent().inject(this);
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
        linearLayoutManager = new StaggeredGridLayoutManager(ScreenUtils.getDisplayColumns(getActivity()), StaggeredGridLayoutManager.VERTICAL);
        list.setLayoutManager(linearLayoutManager);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), list, null);
        swipeRefreshLayout.setProgressViewOffset(false, 0,
                getActivity().getResources().getDimensionPixelSize(R.dimen.scroll_offset));

        listener = new EndlessRecyclerOnScrollListener(getLinearLayoutManager()) {
            @Override
            public void onLoadMore(int current_page) {
                onLoadMoreCalled(nextPage);
            }
        };

        list.addOnScrollListener(listener);

        swipeRefreshLayout.setOnRefreshListener(this);
    }

    protected abstract void onLoadMoreCalled(int page);

    protected abstract int getContentView();

    protected abstract void onReload();

    protected abstract ModelTypeEnum getFragmentType();

    @OnClick(R.id.no_internet_layout_repaet_button)

    protected void reload() {
        helper.getDataMap().get(getFragmentType()).clear();
//        showLoader();
    }

    protected void showError() {
        noInternetLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    protected void showLoader() {
        noInternetLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    protected void hideError() {
        if (noInternetLayout.getVisibility() == View.VISIBLE)
            noInternetLayout.setVisibility(View.GONE);
    }

    @SuppressWarnings("unused")
    public void onEvent(NetworkErrorEvent errorEvent) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!helper.getDataMap().containsKey(getFragmentType()))
                    showError();
            }
        }, 3000L);
    }

    @SuppressWarnings("UnusedParameters")
    public void onEvent(ErrorEvent errorEvent) {
        onEvent(new NetworkErrorEvent());
    }

    @Override
    public void onDetach() {
        if (list.getAdapter() != null) {
            list.setAdapter(null);
        }
        Glide.get(getActivity()).clearMemory();
        ButterKnife.unbind(this);
        BUS.unregister(this);
        System.gc();
        super.onDetach();
    }

    @Override
    public void onRefresh() {
        listener.clearState();
        isFromReload = true;
        onReload();
    }
}
