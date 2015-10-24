package com.goldenpie.devs.kievrest.ui.fragment.events;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.event.events.EntertainmentsLoadedEvent;
import com.goldenpie.devs.kievrest.models.EventModel;
import com.goldenpie.devs.kievrest.ui.BaseListFragment;
import com.goldenpie.devs.kievrest.ui.adapter.EventAdapter;
import com.goldenpie.devs.kievrest.utils.ModelTypeEnum;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class EntertainmentFragment extends BaseListFragment {
    private EventAdapter adapter;

    public EntertainmentFragment() {
    }

    public static EntertainmentFragment newInstance() {
        return new EntertainmentFragment();
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_news;
    }

    @Override
    protected ModelTypeEnum getFragmentType() {
        return ModelTypeEnum.ENTERTAINMENT;
    }

    protected void onReload() {
        reload();
    }

    protected void reload() {
        service.loadEntertainments();
        super.reload();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (helper.getDataMap().containsKey(getFragmentType()) && !helper.getDataMap().get(getFragmentType()).isEmpty()) {
            adapter = new EventAdapter(helper.getEntertainmentsList(), getActivity());
            list.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        } else {
            service.loadEntertainments();
        }
    }

    @Override
    protected void onLoadMoreCalled(int page) {
        if (adapter.isHasNextPage()) {
            swipeRefreshLayout.setRefreshing(true);
            service.loadMoreEntertainments((preferences.getTotalEntertainmentsDataSize() / 20) + 1);
        }
    }

    @SuppressWarnings("unused")
    public void onEvent(EntertainmentsLoadedEvent event) {
        swipeRefreshLayout.setRefreshing(false);

        int size = event.getResults().size();

        Set<EventModel> uniqueItems = new HashSet<>();
        uniqueItems.addAll(event.getResults());

        event.getResults().clear();
        event.getResults().addAll(uniqueItems);

        if (helper.getDataMap().containsKey(getFragmentType()) && !helper.getDataMap().get(getFragmentType()).isEmpty()) {
            preferences.setTotalEntertainmentsDataSize(preferences.getTotalEntertainmentsDataSize() + size);
            ArrayList<EventModel> tempList = helper.getEntertainmentsList();
            tempList.addAll(event.getResults());
            helper.getDataMap().put(ModelTypeEnum.ENTERTAINMENT, tempList);
        } else {
            preferences.setTotalEntertainmentsDataSize(size);
            helper.getDataMap().put(ModelTypeEnum.ENTERTAINMENT, event.getResults());
        }

        if (adapter == null) {
            adapter = new EventAdapter(helper.getEntertainmentsList(), getActivity());
            list.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        }
        hideError();
        adapter.notifyDataSetChanged();

        adapter.setHasNextPage(!TextUtils.isEmpty(event.getNextUrl()));
    }
}
