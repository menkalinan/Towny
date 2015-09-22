package com.goldenpie.devs.kievrest.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

public class NewsFragment extends Fragment {

    @Inject
    DataHelper helper;
    @Inject
    KievRestService service;

    @Bind(R.id.frag_news_list)
    protected ListView newsList;

    private NewsAdapter adapter;
    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    public NewsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KievRestApplication.appComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        if (helper.getDataMap().containsKey(ModelTypeEnum.NEWS)) {
            adapter = new NewsAdapter(getActivity(), 0, helper.getNewsList());
        } else {
            service.loadNews();
        }
    }

    @SuppressWarnings({"unchecked", "unused"})`
    public void onEvent(NewsLoadedEvent event) {
        if (helper.getDataMap().containsKey(ModelTypeEnum.NEWS)) {
            ArrayList<NewsModel> tempList = helper.getNewsList();
            tempList.addAll(event.getResults());
            helper.getDataMap().put(ModelTypeEnum.NEWS, tempList);
        } else {
            helper.getDataMap().put(ModelTypeEnum.NEWS, event.getResults());
        }

        adapter = new NewsAdapter(getActivity(), 0, helper.getNewsList());
    }
}
