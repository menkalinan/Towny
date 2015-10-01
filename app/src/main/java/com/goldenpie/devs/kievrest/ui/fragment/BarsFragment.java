package com.goldenpie.devs.kievrest.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.event.BarsLoadedEvent;
import com.goldenpie.devs.kievrest.event.NetworkErrorEvent;
import com.goldenpie.devs.kievrest.event.RestaurantsLoadedEvent;
import com.goldenpie.devs.kievrest.models.PlaceModel;
import com.goldenpie.devs.kievrest.ui.BaseListFragment;
import com.goldenpie.devs.kievrest.ui.adapter.PlacesAdapter;
import com.goldenpie.devs.kievrest.ui.listener.EndlessRecyclerOnScrollListener;
import com.goldenpie.devs.kievrest.utils.ModelTypeEnum;

import java.util.ArrayList;

import butterknife.OnClick;

public class BarsFragment extends BaseListFragment {
    private PlacesAdapter adapter;

    public BarsFragment() {
    }

    public static BarsFragment newInstance() {
        return new BarsFragment();
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_news;
    }

    @OnClick(R.id.no_internet_layout_repaet_button)
    protected void reload() {
        service.loadBars();
        super.reload();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (helper.getDataMap().containsKey(ModelTypeEnum.BARS)) {
            adapter = new PlacesAdapter(helper.getBarsList(), getActivity());
            list.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        } else {
            service.loadBars();
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        list.setLayoutManager(linearLayoutManager);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), list, null);

        list.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                swipeRefreshLayout.setRefreshing(true);
                service.loadMoreBars((adapter.getItemCount() / 20) + 1);
            }
        });
    }

    @SuppressWarnings("unused")
    public void onEvent(BarsLoadedEvent event) {
        swipeRefreshLayout.setRefreshing(false);

        if (helper.getDataMap().containsKey(ModelTypeEnum.BARS)) {
            ArrayList<PlaceModel> tempList = helper.getBarsList();
            tempList.addAll(event.getResults());
            helper.getDataMap().put(ModelTypeEnum.BARS, tempList);
        } else {
            helper.getDataMap().put(ModelTypeEnum.BARS, event.getResults());
        }

        if (adapter == null) {
            adapter = new PlacesAdapter(helper.getBarsList(), getActivity());
            list.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
    }

    @SuppressWarnings("unused")
    public void onEvent(NetworkErrorEvent errorEvent) {
        if (!helper.getDataMap().containsKey(ModelTypeEnum.BARS))
            showError();
    }

}
