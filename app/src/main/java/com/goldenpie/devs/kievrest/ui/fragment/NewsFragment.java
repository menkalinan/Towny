package com.goldenpie.devs.kievrest.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.event.NetworkErrorEvent;
import com.goldenpie.devs.kievrest.event.NewsLoadedEvent;
import com.goldenpie.devs.kievrest.models.NewsModel;
import com.goldenpie.devs.kievrest.ui.BaseFragment;
import com.goldenpie.devs.kievrest.ui.adapter.NewsAdapter;
import com.goldenpie.devs.kievrest.utils.ModelTypeEnum;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class NewsFragment extends BaseFragment {

    @Bind(R.id.frag_news_list)
    protected RecyclerView newsList;
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
            newsList.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        } else {
            service.loadNews();
        }
        newsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), newsList, null);
    }

    public void onEvent(NewsLoadedEvent event) {
        if (helper.getDataMap().containsKey(ModelTypeEnum.NEWS)) {
            ArrayList<NewsModel> tempList = helper.getNewsList();
            tempList.addAll(event.getResults());
            helper.getDataMap().put(ModelTypeEnum.NEWS, tempList);
        } else {
            helper.getDataMap().put(ModelTypeEnum.NEWS, event.getResults());
        }

        adapter = new NewsAdapter(helper.getNewsList(), getActivity());
        newsList.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
    }

    public void onEvent(NetworkErrorEvent errorEvent) {
        if (!helper.getDataMap().containsKey(ModelTypeEnum.SELECTIONS))
            super.onEvent(errorEvent);
    }

}
