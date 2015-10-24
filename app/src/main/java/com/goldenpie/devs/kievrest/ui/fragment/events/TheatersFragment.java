package com.goldenpie.devs.kievrest.ui.fragment.events;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.event.events.TheatersLoadedEvent;
import com.goldenpie.devs.kievrest.models.EventModel;
import com.goldenpie.devs.kievrest.ui.BaseListFragment;
import com.goldenpie.devs.kievrest.ui.adapter.EventAdapter;
import com.goldenpie.devs.kievrest.utils.ModelTypeEnum;

import java.util.ArrayList;

public class TheatersFragment extends BaseListFragment {
    private EventAdapter adapter;

    public TheatersFragment() {
    }

    public static TheatersFragment newInstance() {
        return new TheatersFragment();
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_news;
    }

    @Override
    protected ModelTypeEnum getFragmentType() {
        return ModelTypeEnum.THEATERS;
    }

    @Override
    protected void onReload() {
        reload();
    }

    protected void reload() {
        service.loadTheaters();
        super.reload();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (helper.getDataMap().containsKey(getFragmentType()) && !helper.getDataMap().get(getFragmentType()).isEmpty()) {
            adapter = new EventAdapter(helper.getTheatersList(), getActivity());
            list.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        } else {
            service.loadTheaters();
        }
    }

    @Override
    protected void onLoadMoreCalled(int page) {
        if (adapter.isHasNextPage()) {
            swipeRefreshLayout.setRefreshing(true);
            service.loadMoreTheaters((adapter.getItemCount() / 20) + 1);
        }
    }

    @SuppressWarnings("unused")
    public void onEvent(TheatersLoadedEvent event) {
        swipeRefreshLayout.setRefreshing(false);

        if (helper.getDataMap().containsKey(getFragmentType()) && !helper.getDataMap().get(getFragmentType()).isEmpty()) {
            ArrayList<EventModel> tempList = helper.getTheatersList();
            tempList.addAll(event.getResults());
            helper.getDataMap().put(ModelTypeEnum.THEATERS, tempList);
        } else {
            helper.getDataMap().put(ModelTypeEnum.THEATERS, event.getResults());
        }

        if (adapter == null) {
            adapter = new EventAdapter(helper.getTheatersList(), getActivity());
            list.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        }
        hideError();
        adapter.notifyDataSetChanged();

        adapter.setHasNextPage(!TextUtils.isEmpty(event.getNextUrl()));
    }
}
