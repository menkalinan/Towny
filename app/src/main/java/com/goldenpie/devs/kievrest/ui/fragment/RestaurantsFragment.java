package com.goldenpie.devs.kievrest.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.event.NetworkErrorEvent;
import com.goldenpie.devs.kievrest.event.RestaurantsLoadedEvent;
import com.goldenpie.devs.kievrest.models.PlaceModel;
import com.goldenpie.devs.kievrest.ui.BaseListFragment;
import com.goldenpie.devs.kievrest.ui.adapter.PlacesAdapter;
import com.goldenpie.devs.kievrest.ui.listener.EndlessRecyclerOnScrollListener;
import com.goldenpie.devs.kievrest.utils.ModelTypeEnum;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class RestaurantsFragment extends BaseListFragment {

    @Bind(R.id.frag_news_list)
    protected RecyclerView newsList;

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

    @OnClick(R.id.no_internet_layout_repaet_button)
    protected void reload() {
        service.loadRestaurants();
        super.reload();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (helper.getDataMap().containsKey(ModelTypeEnum.RESTAURANTS)) {
            adapter = new PlacesAdapter(helper.getRestaurantsList(), getActivity());
            newsList.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        } else {
            service.loadRestaurants();
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        newsList.setLayoutManager(linearLayoutManager);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), newsList, null);

        newsList.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                service.loadMoreSelection((adapter.getItemCount() / 20) + 1);
                service.loadMoreRestaurants((preferences.getTotalRestauratsDataSize() / 20) + 1);
            }
        });
    }

    @SuppressWarnings("unused")
    public void onEvent(RestaurantsLoadedEvent event) {
        swipeRefreshLayout.setRefreshing(false);

        int size = event.getResults().size();
        ArrayList<PlaceModel> modelToDelete = new ArrayList<>();
        for (int i = 0; i < event.getResults().size(); i++) {
            if (i < event.getResults().size() - 1) {
                if (event.getResults().get(i).getId() == (event.getResults().get(i + 1).getId())) {
                    modelToDelete.add(event.getResults().get(i + 1));
                }
            }
        }

        event.getResults().removeAll(modelToDelete);

        if (helper.getDataMap().containsKey(ModelTypeEnum.RESTAURANTS)) {
            preferences.setTotalRestauratsDataSize(preferences.getTotalRestauratsDataSize() + size);
            ArrayList<PlaceModel> tempList = helper.getRestaurantsList();
            tempList.addAll(event.getResults());
            helper.getDataMap().put(ModelTypeEnum.RESTAURANTS, tempList);
        } else {
            preferences.setTotalRestauratsDataSize(size);
            helper.getDataMap().put(ModelTypeEnum.RESTAURANTS, event.getResults());
        }

        if (adapter == null) {
            adapter = new PlacesAdapter(helper.getRestaurantsList(), getActivity());
            newsList.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
    }

    @SuppressWarnings("unused")
    public void onEvent(NetworkErrorEvent errorEvent) {
        if (!helper.getDataMap().containsKey(ModelTypeEnum.RESTAURANTS))
            showError();
    }

}
