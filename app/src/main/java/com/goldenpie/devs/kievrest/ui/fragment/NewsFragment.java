package com.goldenpie.devs.kievrest.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.event.NetworkErrorEvent;
import com.goldenpie.devs.kievrest.event.NewsLoadedEvent;
import com.goldenpie.devs.kievrest.models.NewsModel;
import com.goldenpie.devs.kievrest.ui.BaseListFragment;
import com.goldenpie.devs.kievrest.ui.adapter.NewsAdapter;
import com.goldenpie.devs.kievrest.ui.listener.EndlessRecyclerOnScrollListener;
import com.goldenpie.devs.kievrest.utils.ModelTypeEnum;

import java.util.ArrayList;

import butterknife.OnClick;

public class NewsFragment extends BaseListFragment {

    private NewsAdapter adapter;

    public NewsFragment() {
    }

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_news;
    }

    @OnClick(R.id.no_internet_layout_repaet_button)
    protected void reload() {
        service.loadNews();
        super.reload();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (helper.getDataMap().containsKey(ModelTypeEnum.NEWS)) {
            adapter = new NewsAdapter(helper.getNewsList(), getActivity());
            list.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        } else {
            service.loadNews();
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        list.setLayoutManager(linearLayoutManager);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), list, null);

        list.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                swipeRefreshLayout.setRefreshing(true);
                service.loadMoreNews((adapter.getItemCount() / 20) + 1);
            }
        });
    }

    @SuppressWarnings("unused")
    public void onEvent(NewsLoadedEvent event) {
        swipeRefreshLayout.setRefreshing(false);
        if (helper.getDataMap().containsKey(ModelTypeEnum.NEWS)) {
            ArrayList<NewsModel> tempList = helper.getNewsList();
            tempList.addAll(event.getResults());
            helper.getDataMap().put(ModelTypeEnum.NEWS, tempList);
        } else {
            helper.getDataMap().put(ModelTypeEnum.NEWS, event.getResults());
        }

        if (adapter == null) {
            adapter = new NewsAdapter(helper.getNewsList(), getActivity());
            list.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
    }

    @SuppressWarnings("unused")
    public void onEvent(NetworkErrorEvent errorEvent) {
        if (!helper.getDataMap().containsKey(ModelTypeEnum.NEWS))
            showError();
    }

}
