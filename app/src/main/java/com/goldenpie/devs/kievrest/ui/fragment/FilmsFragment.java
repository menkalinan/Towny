package com.goldenpie.devs.kievrest.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.event.FilmsLoadedEvent;
import com.goldenpie.devs.kievrest.models.FilmModel;
import com.goldenpie.devs.kievrest.ui.BaseListFragment;
import com.goldenpie.devs.kievrest.ui.adapter.FilmsAdapter;
import com.goldenpie.devs.kievrest.utils.ModelTypeEnum;

import java.util.ArrayList;

public class FilmsFragment extends BaseListFragment {
    private FilmsAdapter adapter;

    public FilmsFragment() {
    }

    public static FilmsFragment newInstance() {
        return new FilmsFragment();
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_news;
    }

    @Override
    protected ModelTypeEnum getFragmentType() {
        return ModelTypeEnum.FILMS;
    }

    @Override
    protected void onReload() {
        reload();
    }

    protected void reload() {
        super.reload();
        service.loadFilms();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (helper.getDataMap().containsKey(getFragmentType())
                && !helper.getDataMap().get(getFragmentType()).isEmpty()) {
            adapter = new FilmsAdapter(helper.getFilmsList(), getActivity());
            list.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        } else {
            service.loadFilms();
        }
    }

    @Override
    protected void onLoadMoreCalled(int page) {
        if (adapter.isHasNextPage()) {
            swipeRefreshLayout.setRefreshing(true);
            service.loadMoreFilms((adapter.getItemCount() / 20) + 1);
        }
    }

    @SuppressWarnings("unused")
    public void onEvent(FilmsLoadedEvent event) {
        swipeRefreshLayout.setRefreshing(false);

        if (helper.getDataMap().containsKey(getFragmentType())
                && !helper.getDataMap().get(getFragmentType()).isEmpty()) {
            ArrayList<FilmModel> tempList = helper.getFilmsList();
            tempList.addAll(event.getResults());
            helper.getDataMap().put(getFragmentType(), tempList);
        } else {
            helper.getDataMap().put(getFragmentType(), event.getResults());
        }

        if (adapter == null || isFromReload) {
            isFromReload = false;
            adapter = new FilmsAdapter(helper.getFilmsList(), getActivity());
            list.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        }

        hideError();
        adapter.notifyDataSetChanged();

        adapter.setHasNextPage(!TextUtils.isEmpty(event.getNextUrl()));

    }
}
