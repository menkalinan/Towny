package com.goldenpie.devs.kievrest.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.event.RecreationsLoadedEvent;
import com.goldenpie.devs.kievrest.models.PlaceModel;
import com.goldenpie.devs.kievrest.ui.BaseListFragment;
import com.goldenpie.devs.kievrest.ui.adapter.PlacesAdapter;
import com.goldenpie.devs.kievrest.ui.listener.EndlessRecyclerOnScrollListener;
import com.goldenpie.devs.kievrest.utils.ModelTypeEnum;

import java.util.ArrayList;

import butterknife.OnClick;

public class RecreationsFragment extends BaseListFragment {
    private PlacesAdapter adapter;

    public RecreationsFragment() {
    }

    public static RecreationsFragment newInstance() {
        return new RecreationsFragment();
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_news;
    }

    @Override
    protected ModelTypeEnum getFragmentType() {
        return ModelTypeEnum.RECREATIONS;
    }


    @OnClick(R.id.no_internet_layout_repaet_button)
    protected void reload() {
        service.loadRecreations();
        super.reload();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (helper.getDataMap().containsKey(ModelTypeEnum.RECREATIONS)) {
            adapter = new PlacesAdapter(helper.getRecreationsList(), getActivity());
            list.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        } else {
            service.loadRecreations();
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
    public void onEvent(RecreationsLoadedEvent event) {
        swipeRefreshLayout.setRefreshing(false);

        if (helper.getDataMap().containsKey(ModelTypeEnum.RECREATIONS)) {
            ArrayList<PlaceModel> tempList = helper.getRecreationsList();
            tempList.addAll(event.getResults());
            helper.getDataMap().put(ModelTypeEnum.RECREATIONS, tempList);
        } else {
            helper.getDataMap().put(ModelTypeEnum.RECREATIONS, event.getResults());
        }

        if (adapter == null) {
            adapter = new PlacesAdapter(helper.getRecreationsList(), getActivity());
            list.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        }
        hideError();
        adapter.notifyDataSetChanged();
    }
}
