package com.goldenpie.devs.kievrest.ui.fragment.events;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.event.events.ExhibitionsLoadedEvent;
import com.goldenpie.devs.kievrest.models.EventModel;
import com.goldenpie.devs.kievrest.ui.BaseListFragment;
import com.goldenpie.devs.kievrest.ui.adapter.EventAdapter;
import com.goldenpie.devs.kievrest.ui.listener.EndlessRecyclerOnScrollListener;
import com.goldenpie.devs.kievrest.utils.ModelTypeEnum;

import java.util.ArrayList;

import butterknife.OnClick;

public class ExhibitionsFragment extends BaseListFragment {
    private EventAdapter adapter;

    public ExhibitionsFragment() {
    }

    public static ExhibitionsFragment newInstance() {
        return new ExhibitionsFragment();
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_news;
    }

    @Override
    protected ModelTypeEnum getFragmentType() {
        return ModelTypeEnum.EXHIBITIONS;
    }

    @OnClick(R.id.no_internet_layout_repaet_button)
    protected void reload() {
        service.loadExhibitions();
        super.reload();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (helper.getDataMap().containsKey(ModelTypeEnum.EXHIBITIONS)) {
            adapter = new EventAdapter(helper.getExhibitionsList(), getActivity());
            list.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        } else {
            service.loadExhibitions();
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        list.setLayoutManager(linearLayoutManager);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), list, null);

        list.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                if (adapter.isHasNextPage()) {
                    swipeRefreshLayout.setRefreshing(true);
                    service.loadMoreExhibitions((adapter.getItemCount() / 20) + 1);
                }
            }
        });
    }

    @SuppressWarnings("unused")
    public void onEvent(ExhibitionsLoadedEvent event) {
        swipeRefreshLayout.setRefreshing(false);

        if (helper.getDataMap().containsKey(ModelTypeEnum.EXHIBITIONS)) {
            ArrayList<EventModel> tempList = helper.getExhibitionsList();
            tempList.addAll(event.getResults());
            helper.getDataMap().put(ModelTypeEnum.EXHIBITIONS, tempList);
        } else {
            helper.getDataMap().put(ModelTypeEnum.EXHIBITIONS, event.getResults());
        }

        if (adapter == null) {
            adapter = new EventAdapter(helper.getExhibitionsList(), getActivity());
            list.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        }
        hideError();
        adapter.notifyDataSetChanged();

        adapter.setHasNextPage(!TextUtils.isEmpty(event.getNextUrl()));
    }
}
