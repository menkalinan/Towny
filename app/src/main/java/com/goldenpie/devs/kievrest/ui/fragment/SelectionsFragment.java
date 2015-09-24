package com.goldenpie.devs.kievrest.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.event.NetworkErrorEvent;
import com.goldenpie.devs.kievrest.event.SelectionLoadedEvent;
import com.goldenpie.devs.kievrest.event.SelectionsLoadedEvent;
import com.goldenpie.devs.kievrest.ui.BaseListFragment;
import com.goldenpie.devs.kievrest.ui.adapter.SelectionsAdapter;
import com.goldenpie.devs.kievrest.utils.ModelTypeEnum;

import butterknife.Bind;
import butterknife.OnClick;

public class SelectionsFragment extends BaseListFragment {

    @Bind(R.id.frag_news_list)
    protected RecyclerView selectionsList;

    private SelectionsAdapter adapter;

    private int itemCount = 0;

    public SelectionsFragment() {
    }

    public static SelectionsFragment newInstance() {
        return new SelectionsFragment();
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_news;
    }

    @OnClick(R.id.no_internet_layout_repaet_button)
    protected void reload() {
        service.loadSelections();
        super.reload();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (helper.getDataMap().containsKey(ModelTypeEnum.SELECTIONS)) {
            adapter = new SelectionsAdapter(helper.getSelectionsList(), getActivity());
            selectionsList.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        } else {
            service.loadSelections();
        }
        selectionsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), selectionsList, null);
    }

    @SuppressWarnings("unused")
    public void onEvent(SelectionsLoadedEvent event) {
        helper.getDataMap().put(ModelTypeEnum.SELECTIONS, event.getResults());
        for (int i = 0; i < event.getResults().size(); i++) {
            service.loadSelection(String.valueOf(event.getResults().get(i).getId()));
        }
    }

    @SuppressWarnings("unused")
    public void onEvent(SelectionLoadedEvent event) {

        for (int i = 0; i < helper.getSelectionsList().size(); i++) {
            if (helper.getSelectionsList().get(i).getId() == event.getId()) {
                helper.getSelectionsList().set(i, event);
                itemCount++;
                break;
            }
        }

        if (helper.getSelectionsList().size() == itemCount) {
            adapter = new SelectionsAdapter(helper.getSelectionsList(), getActivity());
            selectionsList.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        }
    }

    public void onEvent(NetworkErrorEvent errorEvent) {
        if (!helper.getDataMap().containsKey(ModelTypeEnum.SELECTIONS))
            super.onEvent(errorEvent);
    }

}
