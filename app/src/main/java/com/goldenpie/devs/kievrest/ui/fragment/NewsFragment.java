package com.goldenpie.devs.kievrest.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.goldenpie.devs.kievrest.KievRestApplication;
import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.event.NewsLoadedEvent;
import com.goldenpie.devs.kievrest.models.NewsModel;
import com.goldenpie.devs.kievrest.ui.adapter.NewsAdapter;
import com.goldenpie.devs.kievrest.utils.DataHelper;
import com.goldenpie.devs.kievrest.utils.ModelTypeEnum;
import com.goldenpie.devs.kievrest.utils.service.KievRestService;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class NewsFragment extends Fragment {

    @Bind(R.id.frag_news_list)
    protected RecyclerView newsList;
    @Inject
    DataHelper helper;
    @Inject
    KievRestService service;
    @Inject
    EventBus BUS;
    private NewsAdapter adapter;

    public NewsFragment() {
    }

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KievRestApplication.appComponent().inject(this);
        BUS.register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_news, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        if (helper.getDataMap().containsKey(ModelTypeEnum.NEWS)) {
            adapter = new NewsAdapter(helper.getNewsList(), getActivity());
            newsList.setAdapter(adapter);
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
    }
}
