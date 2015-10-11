package com.goldenpie.devs.kievrest.ui.fragment.places;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.event.AttractionsLoadedEvent;
import com.goldenpie.devs.kievrest.models.PlaceModel;
import com.goldenpie.devs.kievrest.ui.BaseListFragment;
import com.goldenpie.devs.kievrest.ui.adapter.PlacesAdapter;
import com.goldenpie.devs.kievrest.ui.listener.EndlessRecyclerOnScrollListener;
import com.goldenpie.devs.kievrest.utils.ModelTypeEnum;

import java.util.ArrayList;

import butterknife.OnClick;

public class AttractionsFragment extends BaseListFragment {
    private PlacesAdapter adapter;

    public AttractionsFragment() {
    }

    public static AttractionsFragment newInstance() {
        return new AttractionsFragment();
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_news;
    }

    @Override
    protected ModelTypeEnum getFragmentType() {
        return ModelTypeEnum.ATTRACTIONS;
    }

    @OnClick(R.id.no_internet_layout_repaet_button)
    protected void reload() {
        service.loadAttractions();
        super.reload();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (helper.getDataMap().containsKey(ModelTypeEnum.ATTRACTIONS)) {
            adapter = new PlacesAdapter(helper.getAttractionsList(), getActivity());
            list.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        } else {
            service.loadAttractions();
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        list.setLayoutManager(linearLayoutManager);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), list, null);

        list.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                if (adapter.isHasNextPage()) {
                    swipeRefreshLayout.setRefreshing(true);
                    service.loadMoreAttractions((adapter.getItemCount() / 20) + 1);
                }
            }
        });
    }

    @SuppressWarnings("unused")
    public void onEvent(AttractionsLoadedEvent event) {
        swipeRefreshLayout.setRefreshing(false);

        if (helper.getDataMap().containsKey(ModelTypeEnum.ATTRACTIONS)) {
            ArrayList<PlaceModel> tempList = helper.getAttractionsList();
            tempList.addAll(event.getResults());
            helper.getDataMap().put(ModelTypeEnum.ATTRACTIONS, tempList);
        } else {
            helper.getDataMap().put(ModelTypeEnum.ATTRACTIONS, event.getResults());
        }

        if (adapter == null) {
            adapter = new PlacesAdapter(helper.getAttractionsList(), getActivity());
            list.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        }
        hideError();
        adapter.notifyDataSetChanged();

        adapter.setHasNextPage(!TextUtils.isEmpty(event.getNextUrl()));
    }
}
