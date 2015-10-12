package com.goldenpie.devs.kievrest.ui.fragment.places;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.event.places.ShopsLoadedEvent;
import com.goldenpie.devs.kievrest.models.PlaceModel;
import com.goldenpie.devs.kievrest.ui.BaseListFragment;
import com.goldenpie.devs.kievrest.ui.adapter.PlacesAdapter;
import com.goldenpie.devs.kievrest.ui.listener.EndlessRecyclerOnScrollListener;
import com.goldenpie.devs.kievrest.utils.ModelTypeEnum;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import butterknife.OnClick;

public class ShopsFragment extends BaseListFragment {
    private PlacesAdapter adapter;

    public ShopsFragment() {
    }

    public static ShopsFragment newInstance() {
        return new ShopsFragment();
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_news;
    }

    @Override
    protected ModelTypeEnum getFragmentType() {
        return ModelTypeEnum.SHOPS;
    }

    @OnClick(R.id.no_internet_layout_repaet_button)
    protected void reload() {
        service.loadShops();
        super.reload();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (helper.getDataMap().containsKey(ModelTypeEnum.SHOPS)) {
            adapter = new PlacesAdapter(helper.getShopsList(), getActivity());
            list.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        } else {
            service.loadShops();
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        list.setLayoutManager(linearLayoutManager);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), list, null);

        list.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                if (adapter.isHasNextPage()) {
                    swipeRefreshLayout.setRefreshing(true);
                    service.loadMoreShops((preferences.getTotalShopsDataSize() / 20) + 1);
                }
            }
        });

    }

    @SuppressWarnings("unused")
    public void onEvent(ShopsLoadedEvent event) {
        swipeRefreshLayout.setRefreshing(false);

        int size = event.getResults().size();

        Set<PlaceModel> uniqueItems = new HashSet<>();
        uniqueItems.addAll(event.getResults());

        event.getResults().clear();
        event.getResults().addAll(uniqueItems);

        if (helper.getDataMap().containsKey(ModelTypeEnum.SHOPS)) {
            preferences.setTotalShopsDataSize(preferences.getTotalShopsDataSize() + size);
            ArrayList<PlaceModel> tempList = helper.getShopsList();
            tempList.addAll(event.getResults());
            helper.getDataMap().put(ModelTypeEnum.SHOPS, tempList);
        } else {
            preferences.setTotalShopsDataSize(size);
            helper.getDataMap().put(ModelTypeEnum.SHOPS, event.getResults());
        }

        if (adapter == null) {
            adapter = new PlacesAdapter(helper.getShopsList(), getActivity());
            list.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        }
        hideError();
        adapter.notifyDataSetChanged();

        adapter.setHasNextPage(!TextUtils.isEmpty(event.getNextUrl()));
    }
}