package com.goldenpie.devs.kievrest.ui.fragment.events;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.event.events.YarmarkiLoadedEvent;
import com.goldenpie.devs.kievrest.models.EventModel;
import com.goldenpie.devs.kievrest.ui.BaseListFragment;
import com.goldenpie.devs.kievrest.ui.adapter.EventAdapter;
import com.goldenpie.devs.kievrest.utils.ModelTypeEnum;

import java.util.ArrayList;

public class YarmarkiFragment extends BaseListFragment {
    private EventAdapter adapter;

    public YarmarkiFragment() {
    }

    public static YarmarkiFragment newInstance() {
        return new YarmarkiFragment();
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_news;
    }

    @Override
    protected ModelTypeEnum getFragmentType() {
        return ModelTypeEnum.YARMARKI;
    }

    protected void reload() {
        service.loadYarmarki();
        super.reload();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (helper.getDataMap().containsKey(ModelTypeEnum.YARMARKI)) {
            adapter = new EventAdapter(helper.getYarmarkiList(), getActivity());
            list.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        } else {
            service.loadYarmarki();
        }
    }

    @Override
    protected void onLoadMoreCalled(int page) {
        if (adapter.isHasNextPage()) {
            swipeRefreshLayout.setRefreshing(true);
            service.loadMoreYarmarki((adapter.getItemCount() / 20) + 1);
        }
    }

    @SuppressWarnings("unused")
    public void onEvent(YarmarkiLoadedEvent event) {
        swipeRefreshLayout.setRefreshing(false);

        if (helper.getDataMap().containsKey(ModelTypeEnum.YARMARKI)) {
            ArrayList<EventModel> tempList = helper.getYarmarkiList();
            tempList.addAll(event.getResults());
            helper.getDataMap().put(ModelTypeEnum.YARMARKI, tempList);
        } else {
            helper.getDataMap().put(ModelTypeEnum.YARMARKI, event.getResults());
        }

        if (adapter == null) {
            adapter = new EventAdapter(helper.getYarmarkiList(), getActivity());
            list.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        }
        hideError();
        adapter.notifyDataSetChanged();

        adapter.setHasNextPage(!TextUtils.isEmpty(event.getNextUrl()));
    }
}
