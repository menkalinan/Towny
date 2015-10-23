package com.goldenpie.devs.kievrest.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.event.SelectionLoadedEvent;
import com.goldenpie.devs.kievrest.event.SelectionsLoadedEvent;
import com.goldenpie.devs.kievrest.models.SelectionModel;
import com.goldenpie.devs.kievrest.ui.BaseSubCategoryFragment;
import com.goldenpie.devs.kievrest.ui.adapter.SelectionsAdapter;
import com.goldenpie.devs.kievrest.utils.ModelTypeEnum;

import java.util.ArrayList;

public class SelectionsFragment extends BaseSubCategoryFragment {
    private SelectionsAdapter adapter;

    private int itemCount = 0;
    private boolean hasNextUrl;

    public SelectionsFragment() {
    }

    public static SelectionsFragment newInstance() {
        return new SelectionsFragment();
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_news;
    }

    @Override
    protected ModelTypeEnum getFragmentType() {
        return ModelTypeEnum.SELECTIONS;
    }


    protected void reload() {
        service.loadSelections();
        super.reload();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (helper.getDataMap().containsKey(ModelTypeEnum.SELECTIONS)) {
            if (adapter == null) {
                adapter = new SelectionsAdapter(helper.getSelectionsList(), getActivity());
                list.setAdapter(adapter);
            }
            progressBar.setVisibility(View.GONE);
        } else {
            service.loadSelections();
        }
    }

    @Override
    protected void onLoadMoreCalled(int page) {
        if (adapter.isHasNextPage()) {
            swipeRefreshLayout.setRefreshing(true);
            service.loadMoreSelection((adapter.getItemCount() / 20) + 1);
        }
    }


    @SuppressWarnings("unused")
    public void onEvent(SelectionsLoadedEvent event) {

        hasNextUrl = !TextUtils.isEmpty(event.getNextUrl());
        if (helper.getDataMap().containsKey(ModelTypeEnum.SELECTIONS)) {
            ArrayList<SelectionModel> tempList = helper.getSelectionsList();
            tempList.addAll(event.getResults());
            helper.getDataMap().put(ModelTypeEnum.SELECTIONS, tempList);
        } else {
            helper.getDataMap().put(ModelTypeEnum.SELECTIONS, event.getResults());
        }

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
            swipeRefreshLayout.setRefreshing(false);
            if (adapter == null) {
                adapter = new SelectionsAdapter(helper.getSelectionsList(), getActivity());
                list.setAdapter(adapter);
            }
            progressBar.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            hideError();

            adapter.setHasNextPage(hasNextUrl);
        }
    }

}
