package com.goldenpie.devs.kievrest.ui.fragment.events;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.event.events.FestivalsLoadedEvent;
import com.goldenpie.devs.kievrest.models.EventModel;
import com.goldenpie.devs.kievrest.ui.BaseListFragment;
import com.goldenpie.devs.kievrest.ui.adapter.EventAdapter;
import com.goldenpie.devs.kievrest.utils.ModelTypeEnum;

import java.util.ArrayList;

public class FestivalsFragment extends BaseListFragment {
    private EventAdapter adapter;

    public FestivalsFragment() {
    }

    public static FestivalsFragment newInstance() {
        return new FestivalsFragment();
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_news;
    }

    @Override
    protected ModelTypeEnum getFragmentType() {
        return ModelTypeEnum.FESTIVALS;
    }


    @Override
    protected void onReload() {
        reload();
    }

    protected void reload() {
        service.loadFestivals();
        super.reload();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (helper.getDataMap().containsKey(getFragmentType()) && !helper.getDataMap().get(getFragmentType()).isEmpty()) {
            adapter = new EventAdapter(helper.getFestivalsList(), getActivity());
            list.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        } else {
            service.loadFestivals();
        }
    }

    @Override
    protected void onLoadMoreCalled(int page) {
        if (adapter.isHasNextPage()) {
            swipeRefreshLayout.setRefreshing(true);
            service.loadMoreFestivals((adapter.getItemCount() / 20) + 1);
        }
    }

    @SuppressWarnings("unused")
    public void onEvent(FestivalsLoadedEvent event) {
        swipeRefreshLayout.setRefreshing(false);

        if (helper.getDataMap().containsKey(getFragmentType()) && !helper.getDataMap().get(getFragmentType()).isEmpty()) {
            ArrayList<EventModel> tempList = helper.getFestivalsList();
            tempList.addAll(event.getResults());
            helper.getDataMap().put(ModelTypeEnum.FESTIVALS, tempList);
        } else {
            helper.getDataMap().put(ModelTypeEnum.FESTIVALS, event.getResults());
        }

        if (adapter == null) {
            adapter = new EventAdapter(helper.getFestivalsList(), getActivity());
            list.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        }
        hideError();
        adapter.notifyDataSetChanged();

        adapter.setHasNextPage(!TextUtils.isEmpty(event.getNextUrl()));
    }
}
