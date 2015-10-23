package com.goldenpie.devs.kievrest.ui.fragment.places;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.event.places.RestaurantsLoadedEvent;
import com.goldenpie.devs.kievrest.models.PlaceModel;
import com.goldenpie.devs.kievrest.ui.BaseSubCategoryFragment;
import com.goldenpie.devs.kievrest.ui.adapter.PlacesAdapter;
import com.goldenpie.devs.kievrest.utils.ModelTypeEnum;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RestaurantsFragment extends BaseSubCategoryFragment {
    private PlacesAdapter adapter;

    public RestaurantsFragment() {
    }

    public static RestaurantsFragment newInstance() {
        return new RestaurantsFragment();
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_news;
    }

    @Override
    protected ModelTypeEnum getFragmentType() {
        return ModelTypeEnum.RESTAURANTS;
    }


    protected void reload() {
        service.loadRestaurants();
        super.reload();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (helper.getDataMap().containsKey(ModelTypeEnum.RESTAURANTS)) {
            adapter = new PlacesAdapter(helper.getRestaurantsList(), getActivity());
            list.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        } else {
            service.loadRestaurants();
        }
    }

    @Override
    protected void onLoadMoreCalled(int page) {
        if (adapter.isHasNextPage()) {
            swipeRefreshLayout.setRefreshing(true);
            service.loadMoreRestaurants((preferences.getTotalRestaurantsDataSize() / 20) + 1);
        }
    }

    @SuppressWarnings("unused")
    public void onEvent(RestaurantsLoadedEvent event) {
        swipeRefreshLayout.setRefreshing(false);

        int size = event.getResults().size();

        Set<PlaceModel> uniqueItems = new HashSet<>();
        uniqueItems.addAll(event.getResults());

        event.getResults().clear();
        event.getResults().addAll(uniqueItems);

        if (helper.getDataMap().containsKey(ModelTypeEnum.RESTAURANTS)) {
            preferences.setTotalRestaurantsDataSize(preferences.getTotalRestaurantsDataSize() + size);
            ArrayList<PlaceModel> tempList = helper.getRestaurantsList();
            tempList.addAll(event.getResults());
            helper.getDataMap().put(ModelTypeEnum.RESTAURANTS, tempList);
        } else {
            preferences.setTotalRestaurantsDataSize(size);
            helper.getDataMap().put(ModelTypeEnum.RESTAURANTS, event.getResults());
        }

        if (adapter == null) {
            adapter = new PlacesAdapter(helper.getRestaurantsList(), getActivity());
            list.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        }
        hideError();
        adapter.notifyDataSetChanged();

        adapter.setHasNextPage(!TextUtils.isEmpty(event.getNextUrl()));

    }
}
