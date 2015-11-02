package com.goldenpie.devs.kievrest.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.event.SelectionLoadedEvent;
import com.goldenpie.devs.kievrest.event.SelectionsLoadedEvent;
import com.goldenpie.devs.kievrest.models.SelectionModel;
import com.goldenpie.devs.kievrest.ui.BaseListFragment;
import com.goldenpie.devs.kievrest.ui.adapter.SelectionsAdapter;
import com.goldenpie.devs.kievrest.utils.ModelTypeEnum;

import java.util.ArrayList;
import java.util.HashMap;

import lombok.Getter;

public class SelectionsFragment extends BaseListFragment {
    private SelectionsAdapter adapter;

    private int itemCount = 0;

    @Getter
    private HashMap<Integer, Integer> selectionIds = new HashMap<>();
    private int nextInt = 0;
    private boolean hasNext;

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

    protected void onReload() {
        reload();
    }

    protected void reload() {
        service.loadSelections();
        super.reload();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (helper.getDataMap().containsKey(getFragmentType())
                && !helper.getDataMap().get(getFragmentType()).isEmpty()
                && helper.isSelectionTotallyLoaded()) {
            itemCount = helper.getSelectionsList().size();
            if (adapter == null) {
                adapter = new SelectionsAdapter(helper.getSelectionsList(), getActivity());
                list.setAdapter(adapter);
            }
            progressBar.setVisibility(View.GONE);
        } else {
            if (helper.getDataMap().containsKey(getFragmentType())
                    && !helper.getDataMap().get(getFragmentType()).isEmpty())
                helper.getDataMap().remove(getFragmentType());

            progress.setVisibility(View.VISIBLE);
            progress.setText("0%");
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
    public void onEvent(final SelectionsLoadedEvent event) {
        hasNext = !TextUtils.isEmpty(event.getNextUrl());
        if (helper.getDataMap().containsKey(getFragmentType())
                && !helper.getDataMap().get(getFragmentType()).isEmpty()
                && helper.isSelectionTotallyLoaded()) {
            ArrayList<SelectionModel> tempList = helper.getSelectionsList();
            tempList.addAll(event.getResults());
            helper.getDataMap().put(ModelTypeEnum.SELECTIONS, tempList);
        } else {
            helper.getDataMap().put(ModelTypeEnum.SELECTIONS, event.getResults());
        }

        for (int i = 0; i < event.getResults().size(); i++) {
            getSelectionIds().put(i, event.getResults().get(i).getId());
        }

        getNextSelection(nextInt = 0);
    }

    private void getNextSelection(int i) {
        service.loadSelection(String.valueOf(getSelectionIds().get(i)));
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

        progress.setText(String.format("%d%%", (int) ((itemCount * 100)
                / helper.getSelectionsList().size())));

        if (helper.getSelectionsList().size() == itemCount) {
            getSelectionIds().clear();
            helper.setSelectionTotallyLoaded(true);
            swipeRefreshLayout.setRefreshing(false);

            if (adapter == null) {
                adapter = new SelectionsAdapter(helper.getSelectionsList(), getActivity());
                list.setAdapter(adapter);
            } else {
                for (int i = 0; i < helper.getSelectionsList().size(); i++) {
                    adapter.addModel(helper.getSelectionsList().get(i));
                }
                adapter.addAll(helper.getSelectionsList());
            }
            adapter.setHasNextPage(hasNext);
            adapter.notifyDataSetChanged();

            progressBar.setVisibility(View.GONE);
            progress.setVisibility(View.GONE);

            hideError();
        } else {
            nextInt++;
            getNextSelection(nextInt);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
